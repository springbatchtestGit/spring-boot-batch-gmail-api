package com.eidiko.springbatchexample1.tasklets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eidiko.springbatchexample1.model.Employee;
import com.eidiko.springbatchexample1.service.EmployeeService;

@Component
public class ReadEmployees implements Tasklet {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReadEmployees.class);

	@Autowired
	EmployeeService empService;

	@Override
	public RepeatStatus execute(StepContribution contribution, 
			ChunkContext chunkContext) throws Exception {

		LOG.info("Read Employees from Database...");
		List<Employee> emps = empService.getAllEmployees();

		Map<Integer, Employee> employeeMap = new HashMap<>();
		for (Employee emp : emps) {
			employeeMap.put(emp.getId(), emp);
			LOG.info(emp.toString());
		}

		LOG.info("Store Employees in Job execution context...");
		contribution.getStepExecution().getJobExecution()
		       .getExecutionContext().put("employeeDetails", employeeMap);

		return RepeatStatus.FINISHED;
	}

}
