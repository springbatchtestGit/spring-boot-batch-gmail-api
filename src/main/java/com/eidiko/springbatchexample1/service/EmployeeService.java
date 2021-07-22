package com.eidiko.springbatchexample1.service;

import com.eidiko.springbatchexample1.model.Employee;
import com.eidiko.springbatchexample1.repository.EmployeeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository empRepo;

	public List<Employee> getAllEmployees() {
		return empRepo.findAll();
	}

}