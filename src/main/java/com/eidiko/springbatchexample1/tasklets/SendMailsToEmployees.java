package com.eidiko.springbatchexample1.tasklets;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eidiko.springbatchexample1.service.MailService;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

@Component
public class SendMailsToEmployees implements Tasklet {

	private static final Logger LOG = LoggerFactory.getLogger(SendMailsToEmployees.class);

	@Value("${credential.file}")
	private String filePath;

	@Value("${gmail.user}")
	private String gUser;

	@Value("${gmail.access.token}")
	private String token;

	@Autowired
	MailService mailService;

	@SuppressWarnings("unchecked")
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		LOG.info("Reading Mail Message from Job Execution Context....");

		Map<Integer, Message> mailMap = (Map<Integer, Message>) contribution.getStepExecution().getJobExecution()
				.getExecutionContext().get("mailMap");

		LOG.info("Sending Mails to Employees....");
		MimeMessage mm = mailService.createEmail(gUser, gUser, "Automated Mail Service Completed Successful",
				"\n\nThis mail service batch is completed successful\n\n");

		Gmail service = mailService.getGmailService();
		LOG.info("Sending Mails to Employees....");

		for (Map.Entry<Integer, Message> m : mailMap.entrySet()) {

			LOG.info("Sending Mail to an Employee with ID: " + m.getKey());
			try {
				mailService.sendMessage(service, gUser, (Message) m.getValue());
			} catch (Exception e) {
				LOG.error("ERROR Sending Mail to employee with id: " + m.getKey() + ":" + e.getMessage());
			}

		}

		LOG.info("Sending Mails to Support Guy....");
		try {
			mailService.sendMessage(service, gUser, mailService.createMessageWithEmail(mm));
		} catch (Exception e) {
			LOG.error("ERROR Sending Mail to suport team");

		}

		return RepeatStatus.FINISHED;
	}

}
