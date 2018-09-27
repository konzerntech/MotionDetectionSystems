package com.mds.serv;

import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MDSMailer extends Thread {

	private String recipient="mdsmailer@gmail.com";
	private String from="mdsmailer@gmail.com";
//	private String subject="MDS PIC";
//	private String msg;
	private String fileAttachment = "C:\\test1.jpg";
//	public void send( String subject, String msg, String recipient) throws Exception
//	{
//		this.recipient=recipient;
//		this.msg=msg;
//		this.subject=subject;
//		start();
//	}
	
	public MDSMailer(String file)
	{
		this.fileAttachment = file;
	}
	
	@Override
	public void run(){
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.quitwait", "false");
		 
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getInstance(props,
		new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mdsmailer@gmail.com", "mdsproject");
				}
			});
		
		try {
		    // Define message
		    MimeMessage message = 
		      new MimeMessage(session);
		    message.setFrom(
		      new InternetAddress(from));
		    message.addRecipient(
		      Message.RecipientType.TO, 
		      new InternetAddress(recipient));
		    message.setSubject(
		      "MDS Picture Attachment");

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

			// Send message
			Transport.send(message);
		} catch(Exception e){}
		finally{}
	}	
	
	public static void main(String[] args) {
		
		MDSMailer r = new MDSMailer("C:\\test1.jpg");
		Thread t = new Thread(r);
		r.start();
	}
}