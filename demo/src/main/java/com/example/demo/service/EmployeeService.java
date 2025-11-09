package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
    private final EmployeeRepository employeeRepository;
    
    public EmployeeService(EmployeeRepository employeeRepository) {
    	this.employeeRepository = employeeRepository;
    }
    
    public List<Employee> getAllEmployees(){
    	return employeeRepository.findAll();
    }
    
    public Employee saveEmployee(Employee emp) {
    	return employeeRepository.save(emp);
    }


}
