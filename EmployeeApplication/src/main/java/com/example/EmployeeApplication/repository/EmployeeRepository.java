package com.example.EmployeeApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EmployeeApplication.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
