package com.eidiko.springbatchexample1.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.eidiko.springbatchexample1.model.User;
import com.eidiko.springbatchexample1.tasklets.PrepareMailSubject;
import com.eidiko.springbatchexample1.tasklets.ReadEmployees;
import com.eidiko.springbatchexample1.tasklets.SendMailsToEmployees;

@Configuration
@EnableBatchProcessing
public class SpringBatchMailConfig {

	@Autowired
	ReadEmployees employees;

	@Autowired
	PrepareMailSubject mailSubject;

	@Autowired
	SendMailsToEmployees sendMail;

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<User> itemReader, ItemProcessor<User, User> itemProcessor, ItemWriter<User> itemWriter) {

		Step readEmpStep = stepBuilderFactory.get("Read Employees from Database")
				.tasklet(employees).build();
		Step prepareMessageStep = stepBuilderFactory.get("Prepare Mail Message")
				.tasklet(mailSubject).build();
		Step sendMailStep = stepBuilderFactory.get("Delivering Mails to Employees")
				.tasklet(sendMail).build();

		return jobBuilderFactory.get("SEND_MAILS_TO_EMPLOYEES")
				.incrementer(new RunIdIncrementer()).start(readEmpStep)
				.next(prepareMessageStep).next(sendMailStep).build();
	}

	@Bean
	public FlatFileItemReader<User> itemReader() {
		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
		flatFileItemReader.setName("CSV-READER");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	@Bean
	public LineMapper<User> lineMapper() {
		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id", "name", "dept", "salary");
		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}

}
