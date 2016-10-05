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
    public void notify(String mail, String pass, String login) {
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
        StringBuffer message = new StringBuffer("");
        message.append("Hi, <br><br>"
                + "You have been registered with Cancure.<br> "
                + "Note your login details<br>"
                + "Login : " + login + "<br>"
                + "Password : " + pass + "<br><br>"
                + "Visit <a href=http://www.cancure.in.net>www.cancure.in.net</a><br><br>" 
                + "Thanks, <br>Cancure");
        try {
            email.setHtmlMsg(message.toString());
            email.addTo(mail);            
            email.setSubject("Cancure Notification");
            email.setHtmlMsg(message.toString());
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
