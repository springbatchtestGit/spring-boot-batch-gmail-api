package com.eidiko.springbatchexample1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.eidiko.springbatchexample1.model.Employee;
import com.eidiko.springbatchexample1.repository.EmployeeRepository;

@SpringBootApplication
public class SpringBatchExample1Application implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory
			.getLogger(SpringBatchExample1Application.class);

	@Autowired
	EmployeeRepository empRepo;

	@Value("${gmail.user}")
	private String gUser;

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchExample1Application.class, args);
	}

	@Override
	public void run(String... args) {
		LOG.info("Test data is being inserted....");
		Employee emp = null;
		;
		for (int idx = 1; idx <= 5; idx++) {
			emp = new Employee();
			emp.setAge(idx * 10);
			emp.setCity("City-" + idx);
			emp.setCompanyName("Company-" + idx);
			emp.setDesignation("Designation-" + idx);
			emp.setEmailId(gUser);
			emp.setName("User-" + idx);
			empRepo.save(emp);
		}
	}
}
