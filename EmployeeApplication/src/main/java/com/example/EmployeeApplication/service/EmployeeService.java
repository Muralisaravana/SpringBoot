package com.example.EmployeeApplication.service;

import java.util.List;

import com.example.EmployeeApplication.entity.Employee;

public interface EmployeeService {

	Employee saveEmployee(Employee employee);
	List<Employee> getAllEmployees();
	Employee getEmployeeById(Long id);
	Employee updateEmployee(Long id,Employee employee);
	void deleteEmployee(Long id);
}

