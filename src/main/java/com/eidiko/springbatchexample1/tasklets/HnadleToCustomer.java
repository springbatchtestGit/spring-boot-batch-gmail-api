package com.eidiko.springbatchexample1.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class HnadleToCustomer implements Tasklet {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(HnadleToCustomer.class);

	@Override
	public RepeatStatus execute(StepContribution contribution,
				ChunkContext chunkContext) throws Exception {

		LOG.info("Habdled Items to customer for the package Id:"
				+ contribution.getStepExecution().getJobExecution()
					.getExecutionContext().getInt("ID"));

		return RepeatStatus.FINISHED;
	}

}
