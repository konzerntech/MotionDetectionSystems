package com.mds.tryout;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class AttachedMail {
  public static void main (String args[]) 
      throws Exception {
    String host = args[0];
    String from = args[1];
    String to = args[2];
    String fileAttachment = args[3];

    // Get system properties
    Properties props = System.getProperties();

    // Setup mail server
    props.put("mail.smtp.host", host);

    // Get session
    Session session = 
      Session.getInstance(props, null);

    // Define message
    MimeMessage message = 
      new MimeMessage(session);
    message.setFrom(
      new InternetAddress(from));
    message.addRecipient(
      Message.RecipientType.TO, 
      new InternetAddress(to));
    message.setSubject(
      "Hello JavaMail Attachment");

    // create the message part 
    MimeBodyPart messageBodyPart = 
      new MimeBodyPart();

    //fill message
    messageBodyPart.setText("Hi");

    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);

    // Part two is attachment
    messageBodyPart = new MimeBodyPart();
    DataSource source = 
      new FileDataSource(fileAttachment);
    messageBodyPart.setDataHandler(
      new DataHandler(source));
    messageBodyPart.setFileName(fileAttachment);
    multipart.addBodyPart(messageBodyPart);

    // Put parts in message
    message.setContent(multipart);

    // Send the message
    Transport.send( message );
  }
}
