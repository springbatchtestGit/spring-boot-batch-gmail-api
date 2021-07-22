package com.eidiko.springbatchexample1.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

@Service
public class MailService {

	private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

	private static final String APPLICATION_NAME = "Spring Mail Service";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);

	@Value("${credential.file}")
	private String filePath;

	@Value("${gmail.user}")
	private String gUser;

	@Value("${gmail.access.token}")
	private String token;

	public MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage email = new MimeMessage(session);
		email.setFrom(new InternetAddress(from));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		email.setSubject(subject);
		email.setText(bodyText);
		return email;
	}

	public Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}

	public Message sendMessage(Gmail service, String userId, Message message) throws MessagingException, IOException {
		message = service.users().messages().send(userId, message).execute();
		LOG.info("Message id: " + message.getId());
		LOG.info(message.toPrettyString());
		return message;
	}

	public Gmail getGmailService() throws GeneralSecurityException, IOException {

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credentialFromJson = GoogleCredential.fromStream(new FileInputStream(filePath))
				.createScoped(Collections.singletonList(GmailScopes.GMAIL_SEND));

		GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(credentialFromJson.getServiceAccountId())
				.setServiceAccountPrivateKey(credentialFromJson.getServiceAccountPrivateKey())
				.setServiceAccountScopes(SCOPES).setServiceAccountUser(gUser).build();

		// credential.refreshToken();
		credential.setAccessToken(token);
		LOG.info("Access Token: " + credential.getAccessToken());

		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
				.build();

		return service;

	}

}
