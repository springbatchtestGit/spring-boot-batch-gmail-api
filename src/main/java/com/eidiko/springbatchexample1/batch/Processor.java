package com.eidiko.springbatchexample1.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eidiko.springbatchexample1.model.User;

@Component
public class Processor extends StepExecutionListenerSupport implements ItemProcessor<User, User> {

	private static final Logger LOG = LoggerFactory.getLogger(DBWriter.class);

	private StepExecution stepExecution;

	@Autowired
	Job job;

	@Autowired
	JobRegistry jobRegistry;

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		return this.stepExecution.getJobExecution().getExecutionContext().containsKey("ID")
				? new ExitStatus("FAILED")
				: new ExitStatus("COMPLETED");
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	private static final Map<String, String> DEPT_NAMES = new HashMap<>();

	public Processor() {
		DEPT_NAMES.put("001", "Technology");
		DEPT_NAMES.put("002", "Operations");
		DEPT_NAMES.put("003", "Accounts");
	}

	@Override
	public User process(User user) throws Exception {
		String deptCode = user.getDept();
		String dept = DEPT_NAMES.get(deptCode);
		user.setDept(dept);
		user.setTime(new Date());

		LOG.info("READ  COUNT: " + this.stepExecution.getReadCount());
		LOG.info("READ SKIP COUNT: " + this.stepExecution.getReadSkipCount());
		LOG.info("WRITE  COUNT: " + this.stepExecution.getWriteCount());
		LOG.info("WRITE SKIP COUNT: " + this.stepExecution.getWriteSkipCount());

		
		LOG.info(String.format("Converted from [%s] to [%s]", deptCode, dept));
		
		return user;
	}
}
