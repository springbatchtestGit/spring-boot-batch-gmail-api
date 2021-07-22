package com.eidiko.springbatchexample1.tasklets;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class Packaging implements Tasklet {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(Packaging.class);

	@Override
	public RepeatStatus execute(StepContribution contribution, 
			ChunkContext chunkContext) throws Exception {
		
		int nextInt = new Random().nextInt(100);
		LOG.info("packaged item No:" + nextInt);

		contribution.getStepExecution().getJobExecution()
			.getExecutionContext().putInt("ID", nextInt);

		return RepeatStatus.FINISHED;
	}

}
