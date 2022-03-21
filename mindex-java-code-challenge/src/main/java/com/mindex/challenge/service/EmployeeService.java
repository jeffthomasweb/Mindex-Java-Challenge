package com.mindex.challenge.service;

import java.util.List;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);
    List<Employee> readReports(String id);
    Compensation readCompensation(String employeeId);
    Compensation createCompensation(Compensation employeeCompensation);
}
