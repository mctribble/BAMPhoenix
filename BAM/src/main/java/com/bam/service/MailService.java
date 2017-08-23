package com.bam.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import com.bam.bean.CustomException;

public class MailService {

	public static void sendMail(String email, String newPassword) {
		
		

        final String USERNAME = "revabam@gmail.com";
        final String PASSWORD = "testing123";
        String receiver= email; //user.getemail
        
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
            message.setText("Your temporary password is " + newPassword);   


            Transport.send(message);
            

        } catch (MessagingException e) {
				try {
					throw new CustomException(e);
				} catch (CustomException e1) {
					;
				}
			} 
        }


    }


