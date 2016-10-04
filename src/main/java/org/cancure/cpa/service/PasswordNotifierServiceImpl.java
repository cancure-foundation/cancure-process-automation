package org.cancure.cpa.service;

import org.activiti.engine.ActivitiException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.cancure.cpa.util.ApplicationContextProvider;
import org.cancure.cpa.util.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component("passwordNotifier")
public class PasswordNotifierServiceImpl implements PasswordNotifier {

    @Override
    public void notify(String mail, String pass) {
        if (mail == null || pass == null){
            Log.getLogger().warn("Email cannot be empty. No one to email or SMS.");
            return;
        }
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        
        Environment env = context.getBean(Environment.class);
        String enabled = env.getProperty("email.enabled");
        
        if (!Boolean.valueOf(enabled)){
            return;
        }
        
        String host = env.getProperty("email.server");
        String port = env.getProperty("email.port");
        String from = env.getProperty("email.from");
        String password = env.getProperty("email.password");
        HtmlEmail email = new HtmlEmail();
        String message = "Please note your login Password : "+pass;
        try {
            email.setHtmlMsg(message);
            email.addTo(mail);            
            email.setSubject("Cancure Notification");
            email.setHtmlMsg(message);
            email.setHostName(host);
            email.setSmtpPort(Integer.parseInt(port));
            email.setFrom(from);
            if(from!=null && password!=null){
            email.setAuthentication(from, password);
            }
            email.send();
        } catch (EmailException e) {
            Log.getLogger().error(e);
            throw new ActivitiException("Could not send e-mail:" + e.getMessage(), e);
        }
    }

}
