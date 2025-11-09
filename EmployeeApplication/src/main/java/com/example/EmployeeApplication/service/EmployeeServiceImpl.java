package com.example.EmployeeApplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.EmployeeApplication.entity.Employee;
import com.example.EmployeeApplication.exception.ResourceNotFoundException;
import com.example.EmployeeApplication.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employee Not Found"));
	}

	@Override
	public Employee updateEmployee(Long id, Employee employee) {
		Employee existing = getEmployeeById(id);
		existing.setName(employee.getName());
		existing.setEmail(employee.getEmail());
		existing.setDepartment(employee.getDepartment());
		return employeeRepository.save(existing);
	}

	@Override
	public void deleteEmployee(Long id) {
		Employee emp = getEmployeeById(id);
		employeeRepository.delete(emp);
		
	}
	
	

}
