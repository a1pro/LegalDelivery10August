package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import android.util.Log;

public class GMailSender extends javax.mail.Authenticator {
	private String mailhost = "smtp.gmail.com";
	private String user;
	private String password;
	private javax.mail.Session session;
	Multipart multipart;
	public GMailSender(String user, String password) {
		this.user = user;
		this.password = password;

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", mailhost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		session = javax.mail.Session.getDefaultInstance(props, this);
	}

	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		// return new javax.mail.PasswordAuthentication(user, password);
		return new javax.mail.PasswordAuthentication(user, password);
	}

	public boolean makeAttachments(String filepath) {
		try {
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText("This is message body");

			// Create a multipar message
			 multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = filepath;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
		

		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
		return true;
	}

	public synchronized void sendMail(String subject, String body,
			String sender, String recipients) throws Exception {
		try {
			MimeMessage message = new MimeMessage(session);
			DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
			message.setSender(new InternetAddress(sender));
			message.setSubject(subject);
			message.setDataHandler(handler);
			
			if(multipart==null)
				Log.i("Attachments:", "No attachments found!");
			else
				message.setContent(multipart);
			if (recipients.indexOf(',') > 0)
				message.setRecipients(javax.mail.Message.RecipientType.TO,
						InternetAddress.parse(recipients));
			else
				message.setRecipient(javax.mail.Message.RecipientType.TO,
						new InternetAddress(recipients));
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
