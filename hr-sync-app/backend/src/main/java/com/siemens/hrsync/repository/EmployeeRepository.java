package com.siemens.hrsync.repository;
import com.siemens.hrsync.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
