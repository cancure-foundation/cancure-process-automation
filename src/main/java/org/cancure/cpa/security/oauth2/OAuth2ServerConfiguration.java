package org.cancure.cpa.security.oauth2;

import javax.sql.DataSource;

import org.cancure.cpa.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class OAuth2ServerConfiguration {

	private static final String RESOURCE_ID = "restservice";

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends
			ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.antMatchers("/**").permitAll();
					/*.antMatchers("/hellomessage").permitAll()
					.antMatchers("/allusers").hasRole("USER")
					.antMatchers("/cancure").hasRole("ADMIN")
					.antMatchers("/pc").hasRole("PROGRAM_COORDINATOR")
					.antMatchers("/**").authenticated();*/
			// @formatter:on
			
			http.csrf().disable();
		}

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {

		//private TokenStore tokenStore = new InMemoryTokenStore();

		@Autowired
		private CustomUserDetailsService userDetailsService;
		
		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Autowired
        DataSource dataSource;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints
             .tokenStore(new JdbcTokenStore(dataSource))
             .authenticationManager(this.authenticationManager)
             .userDetailsService(userDetailsService);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients
				.jdbc(dataSource)
				.withClient("cancureapp")
					.authorizedGrantTypes("password", "refresh_token")
					.authorities("USER")
					.scopes("read", "write")
					.resourceIds(RESOURCE_ID)
					.secret("cancure123456");
		}

		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setAccessTokenValiditySeconds(300);
            tokenServices.setRefreshTokenValiditySeconds(6000);
            tokenServices.setTokenStore(new JdbcTokenStore(dataSource));
            return tokenServices;
		}
		
	}

}