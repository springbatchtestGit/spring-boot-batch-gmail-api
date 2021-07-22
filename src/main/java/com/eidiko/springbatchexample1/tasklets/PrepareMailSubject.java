package com.eidiko.springbatchexample1.tasklets;

import java.util.HashMap;
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

import com.eidiko.springbatchexample1.model.Employee;
import com.eidiko.springbatchexample1.service.MailService;
import com.google.api.services.gmail.model.Message;

@Component
public class PrepareMailSubject implements Tasklet {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(PrepareMailSubject.class);

	@Autowired
	MailService mailService;

	@Value("${gmail.user}")
	private String gUser;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		LOG.info("Reading Employees from Job Execution Context....");
		@SuppressWarnings("unchecked")
		Map<Integer, Employee> emps = (Map<Integer, Employee>) contribution.getStepExecution().getJobExecution()
				.getExecutionContext().get("employeeDetails");
		MimeMessage mm = null;
		Map<Integer, Message> mailMap = new HashMap<>();

		for (Map.Entry<Integer, Employee> m : emps.entrySet()) {
			
			LOG.info(m.getKey() + " " + m.getValue().toString());
			String empName = m.getValue().getName();
			
			String body = "Dear " + empName + ",\n\nBelow are the Employee Details:\nEmployee name: " + empName
					+ "\nEmployee City: " + m.getValue().getCity() + "\nEmployee Designation: "
					+ m.getValue().getDesignation() + "\nEmployee Company: " + m.getValue().getCompanyName()
					+ "\n\nBest Regards,\n" + empName;

			LOG.info("Mail Body:: \n" + body);
			mm = mailService.createEmail(m.getValue()
					.getEmailId(), gUser, "Automated Mail Service-" + empName, body);
			mailMap.put(m.getKey(), mailService.createMessageWithEmail(mm));
		}

		contribution.getStepExecution().getJobExecution()
		          .getExecutionContext().put("mailMap", mailMap);
		
		return RepeatStatus.FINISHED;
	}

}
