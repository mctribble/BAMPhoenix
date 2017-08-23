package com.bam.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.bam.bean.CustomException;
import com.bam.logging.LoggerClass;

public class MailService {

	public static void sendMail(String email) {
		Logger logger = Logger.getLogger(LoggerClass.class);

		
		

        final String USERNAME = "revabam@gmail.com";
        final String PASSWORD = "testing123";
        String receiver= email; //user.getemail
        
        PasswordGenerator pass = new PasswordGenerator();
        

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
            message.setText("Your temporary password is " + pass);   


            Transport.send(message);
            

        } catch (MessagingException e) {
				try {
					throw new CustomException(e);
				} catch (CustomException e1) {
					logger.error(e1);
				}
			} 
        }


    }


