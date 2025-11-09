package com.example.EmployeeApplication.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeApplication.entity.Employee;
import com.example.EmployeeApplication.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private final EmployeeService service;
	
	public EmployeeController(EmployeeService service) {
		this.service = service;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee){
		return new ResponseEntity<>(service.saveEmployee(employee),HttpStatus.CREATED);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<Employee>> getAll(){
		return ResponseEntity.ok(service.getAllEmployees());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getById(@PathVariable Long id){
		return ResponseEntity.ok(service.getEmployeeById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Employee> update(@PathVariable Long id,@RequestBody Employee employee)
	{
		return ResponseEntity.ok(service.updateEmployee(id, employee));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}

}
