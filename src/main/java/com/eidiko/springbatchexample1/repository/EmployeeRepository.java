package com.eidiko.springbatchexample1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eidiko.springbatchexample1.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
