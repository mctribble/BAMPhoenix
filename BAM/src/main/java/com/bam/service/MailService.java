package com.bam.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

<<<<<<< HEAD
import com.bam.bean.CustomException;
=======
import com.bam.bean.BamUser;
>>>>>>> 2220584cbc57f69ec51688b7e290250e3c8a8f50

public class MailService {

	public static void sendMail(String email, String newPassword) {


<<<<<<< HEAD
        final String USERNAME = "revabam@gmail.com";
        final String PASSWORD = "testing123";
        String receiver= email; //user.getemail
        
        PasswordGenerator pass = new PasswordGenerator();
        
=======
		//replace these with environtment vairables
        final String username = "revabam@gmail.com";
        final String password = "testing123";
        String receiver= email; //user.getemail
        
        Properties pop = new Properties();
>>>>>>> 2220584cbc57f69ec51688b7e290250e3c8a8f50
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
                  new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                  });
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(receiver));
            message.setSubject("Recover Password");
<<<<<<< HEAD
            message.setText("Your temporary password is " + pass);   
=======
  
            message.setText("Your temporary password is " + newPassword  + " " + " Feel free to click the following link login \n" + " "+
                    "Login Page: http://localhost:8085/BAM/#/");            
            
//            + ", and your username is" +
//            " " + email + ". Feel free to click the following link to set your own! \n"
//                + "Password Reset: http://localhost:7001/PeekABooERS/initiallogin.html"
>>>>>>> 2220584cbc57f69ec51688b7e290250e3c8a8f50

            Transport.send(message);
            

        } catch (MessagingException e) {
				try {
					throw new CustomException(e);
				} catch (CustomException e1) {
					e1.printStackTrace();
				}
			} 
        }


    }

<<<<<<< HEAD
=======
}
>>>>>>> 2220584cbc57f69ec51688b7e290250e3c8a8f50
