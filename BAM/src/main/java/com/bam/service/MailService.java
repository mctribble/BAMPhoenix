package com.bam.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bam.bean.BamUser;

public class MailService {

	public static void sendMail(String email, String newPassword) {


		//replace these with environtment vairables
        final String username = "revabam@gmail.com";
        final String password = "testing123";
        String receiver= email; //user.getemail
        
        Properties pop = new Properties();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
                  new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                  });
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(receiver));
            message.setSubject("Recover Password");
  
            message.setText("Your temporary password is " + newPassword  + " " + " Feel free to click the following link login \n" + " "+
                    "Login Page: http://localhost:8085/BAM/#/");            
            
//            + ", and your username is" +
//            " " + email + ". Feel free to click the following link to set your own! \n"
//                + "Password Reset: http://localhost:7001/PeekABooERS/initiallogin.html"

            Transport.send(message);
            

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

}
