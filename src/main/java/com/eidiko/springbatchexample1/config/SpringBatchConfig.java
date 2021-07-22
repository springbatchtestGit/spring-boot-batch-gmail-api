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
import com.eidiko.springbatchexample1.tasklets.Packaging;
import com.eidiko.springbatchexample1.tasklets.Transporting;
import com.eidiko.springbatchexample1.tasklets.DeliverAtDoor;
import com.eidiko.springbatchexample1.tasklets.HnadleToCustomer;

@Configuration
@EnableBatchProcessing
@SuppressWarnings("all")
public class SpringBatchConfig {

	/*
	 * @Autowired Packaging packaging;
	 * 
	 * @Autowired Transporting transporting;
	 * 
	 * @Autowired DeliverAtDoor doorDelver;
	 * 
	 * @Autowired HnadleToCustomer handleCust;
	 * 
	 * @Bean public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory
	 * stepBuilderFactory, ItemReader<User> itemReader, ItemProcessor<User, User>
	 * itemProcessor, ItemWriter<User> itemWriter) {
	 * 
	 * Step step1 = stepBuilderFactory.get("ETL-STEP-1").<User,
	 * User>chunk(3).reader(itemReader)
	 * .processor(itemProcessor).writer(itemWriter).build();
	 * 
	 * 
	 * Step step2 = stepBuilderFactory.get("ETL-STEP-2").<User,
	 * User>chunk(3).reader(itemReader)
	 * .processor(itemProcessor).writer(itemWriter).build();
	 * 
	 * Step step3 = stepBuilderFactory.get("ETL-STEP-3").<User,
	 * User>chunk(3).reader(itemReader)
	 * .processor(itemProcessor).writer(itemWriter).build();
	 * 
	 * 
	 * Step packageGeneration =
	 * stepBuilderFactory.get("packaging item").tasklet(packaging).build(); Step
	 * delivery =
	 * stepBuilderFactory.get("Leaving at the door").tasklet(transporting).build();
	 * 
	 * Step deliveryStep =
	 * stepBuilderFactory.get("Delivery Step").tasklet(doorDelver).build();
	 * 
	 * Step handleCustomer =
	 * stepBuilderFactory.get("handing the package to the customer").tasklet(
	 * handleCust).build();
	 * 
	 * 
	 * return jobBuilderFactory.get("ETL-LOAD").incrementer(new
	 * RunIdIncrementer()).start(step1).on("*").to(step2)
	 * .from(step1).on("FAILED").to(step3).end().build();
	 * 
	 * return jobBuilderFactory.get("ETL-WITH_PACKAGE").incrementer(new
	 * RunIdIncrementer()).start(step1)
	 * .next(packageGeneration).next(delivery).next(deliveryStep).next(
	 * handleCustomer).build(); }
	 * 
	 * @Bean public FlatFileItemReader<User> itemReader() {
	 * 
	 * FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
	 * flatFileItemReader.setResource(new
	 * FileSystemResource("src/main/resources/users.csv"));
	 * flatFileItemReader.setName("CSV-READER");
	 * flatFileItemReader.setLinesToSkip(1);
	 * flatFileItemReader.setLineMapper(lineMapper()); return flatFileItemReader; }
	 * 
	 * @Bean public LineMapper<User> lineMapper() {
	 * 
	 * DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
	 * DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	 * 
	 * lineTokenizer.setDelimiter(","); lineTokenizer.setStrict(false);
	 * lineTokenizer.setNames("id", "name", "dept", "salary");
	 * 
	 * BeanWrapperFieldSetMapper<User> fieldSetMapper = new
	 * BeanWrapperFieldSetMapper<>(); fieldSetMapper.setTargetType(User.class);
	 * 
	 * defaultLineMapper.setLineTokenizer(lineTokenizer);
	 * defaultLineMapper.setFieldSetMapper(fieldSetMapper);
	 * 
	 * return defaultLineMapper; }
	 */
	 
}
