package org.cancure.cpa.service;

import org.activiti.engine.ActivitiException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.cancure.cpa.persistence.entity.User;
import org.cancure.cpa.persistence.repository.SettingsRepository;
import org.cancure.cpa.util.ApplicationContextProvider;
import org.cancure.cpa.util.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component("passwordNotifier")
public class PasswordNotifierServiceImpl implements PasswordNotifier {

    @Override
    public void notify(User user, String pass, Boolean resetPassword) {
        if (user.getEmail() == null || pass == null){
            Log.getLogger().warn("Email cannot be empty. No one to email or SMS.");
            return;
        }
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        
        SettingsRepository settingsRepo = context.getBean(SettingsRepository.class);
		String enabled = settingsRepo.findOne(33).getValue(); //'Email Enabled
        
        if (!Boolean.valueOf(enabled)){
            return;
        }
        
        String host = settingsRepo.findOne(34).getValue(); //email.server
		String port = settingsRepo.findOne(35).getValue(); //Email Port
		String from = settingsRepo.findOne(36).getValue(); //Email From
		String password = settingsRepo.findOne(37).getValue(); //Email Password

        HtmlEmail email = new HtmlEmail();
        StringBuffer message = new StringBuffer("");
        String mailText = resetPassword ? "Your password has been reset." : "You have been registered with Cancure.";
        message.append("<div style='border : 2px solid #f4961c;'>"
                + "<div style='background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;'>"
                + "Cancure Foundation</div>"
                + "<div style='padding:15px;color: #222d32;font-weight:500;'> "
                + "Hi "+ user.getName() +", <br><br>"
                + "<b>"+ mailText +"</b> <br> <br>"
                + "<table border=1 style='border-collapse: collapse;'>"
                + "<tr>"
                + "<th style='padding:4px 8px;'> Login ID</th>"
                + "<th style='padding:4px 8px;'> Password</th>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding:4px 8px;'>"+ user.getLogin() +"</td>"
                + "<td style='padding:4px 8px;'>"+ pass +"</td>"
                + "</tr>"
                + "</table><br>"
                + "Visit <a href='www.cancure.in.net'>www.cancure.in.net</a> <br> <br>"
                + "<b>Thanks,</b> <br>"
                + "Admin"
                + "</div><br><i>Please do not reply to this email</i><br>"
                + "</div>");
        try {
            email.setHtmlMsg(message.toString());
            email.addTo(user.getEmail());            
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
