package com.bam.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {

	public void sendMail(String to, String subj, String msg) {
		
		// Add "from" attributes and log in to mail account
		String from = "bamrevature@gmail.com";
		String password = "lANvlzZ8xiWVd3qfzBXrZz3m";
        String host = "smtp.gmail.com";
        
        // Add sig file
        msg += "\n\n----------------------------------------" +
        	"\n\nDo not reply to this e-mail.  This e-mail was sent from an unmonitored e-mail account." +
        	"\n\n" + "\u00a9" + "2017 BAM Revature";
        
        // Create session properties
        Properties props = new Properties();  
        props.setProperty("mail.transport.protocol", "smtp");     
        props.setProperty("mail.host", host);  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.port", "465");  
        props.put("mail.debug", "true");  
        props.put("mail.smtp.socketFactory.port", "465");  
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
        props.put("mail.smtp.socketFactory.fallback", "false");  
        Session session = Session.getDefaultInstance(props,  
	        new javax.mail.Authenticator() {
	           protected PasswordAuthentication getPasswordAuthentication() {  
	           return new PasswordAuthentication(from, password);  
           }  
        });

        try {
            // Instantiate a message
            Message message = new MimeMessage(session);

            //Set message attributes
            message.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subj);
            message.setSentDate(new Date());

            // Set message content
            message.setText(msg);

            //Send the message
            Transport.send(message);
        }
        catch (MessagingException mex) {
            // Prints all nested (chained) exceptions as well
            mex.printStackTrace();
        }
		
	}
	
}
