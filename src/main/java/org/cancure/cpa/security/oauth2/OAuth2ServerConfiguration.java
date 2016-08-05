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

/**
 *
 * After you launch the app, you can seek a bearer token like this:
 *
 * <pre>
 * POST to URL - http://localhost:8080/oauth/token?password=cancure&username=cancure&grant_type=password&scope=read%20write&client_secret=cancure123456&client_id=cancureapp
 * Using Basic Auth - Username = cancureapp and Password = cancure123456
 * i.e. Base64 Encode the username:password and set the header "Authorization" with value "Basic <base64 encoded username:password>".
 * E.g. Base64 encoded value of cancureapp:cancure123456 = Y2FuY3VyZWFwcDpjYW5jdXJlMTIzNDU2
 * Set Header as Authorization=Basic Y2FuY3VyZWFwcDpjYW5jdXJlMTIzNDU2
 * 
 * Response should be similar to this:
 * <code>{"access_token":"533de99b-5a0f-4175-8afd-1a64feb952d5","token_type":"bearer","expires_in":43199,"scope":"read"}</code>
 *
 * </pre>
 * 
 * With the token value, you can now interrogate the RESTful interface like this:
 *
 * <pre>
 * curl -H "Authorization: bearer <access_token>" localhost:8080/url
 * </pre>
 * 
 * After the specified time period, the access_token will expire. Use the refresh_token that was returned in the original OAuth authorization to retrieve a new access_token:
 * 
 * <code>
 * curl -X POST -vu clientapp:cancure123456 http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=f554d386-0b0a-461b-bdb2-292831cecd57&client_secret=cancure123456&client_id=cancureapp -H "Accept: application/json"
 * </code>
 * 
 * @author 870154
 *
 */
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
				.jdbc(dataSource);
				/*.withClient("cancureapp")
					.authorizedGrantTypes("password", "refresh_token")
					.authorities("USER")
					.scopes("read", "write")
					.resourceIds(RESOURCE_ID)
					.secret("cancure123456");*/
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