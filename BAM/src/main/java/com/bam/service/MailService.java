package com.bam.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;

public class MailService {

  public static void sendMail(String email, String newPassword) {

    final String EMAILUSERNAME = System.getenv("EMAIL_USERNAME");
    final String EMAILPASSWORD = System.getenv("EMAIL_PASSWORD");

    String receiver = email; // user.getemail

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(EMAILUSERNAME, EMAILPASSWORD);
      }
    });
    try {

      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(EMAILUSERNAME));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));

      message.setSubject("Recover Password");
      message.setText("Hi! Your New temporary password is: \n " + newPassword + "\n" + "" + " " + "\n"
          + "Upon Logging in, please click the dropdown menu where your name is located and select reset password to set"
          + " " + "your password to your convience. \n" + ""
          + "\n Never show or give your password to anyone to avoid your account from being compromised. \n" + ""
          + "\n Regards, \n Revature Team");

      Transport.send(message);

    } catch (MessagingException e) {
      LogManager.getRootLogger().error(e);
    }
  }

}
