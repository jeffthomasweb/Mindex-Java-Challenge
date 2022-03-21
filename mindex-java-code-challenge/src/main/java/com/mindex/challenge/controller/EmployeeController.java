package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
    
    //Task 1, get the list of reports. Entering the Id in the URL will provide the list of reports. Return object is <List>Employees. Queries database.
    @GetMapping("/employee/reports/{id}")
    public List<Employee> readReports(@PathVariable String id) {
    	LOG.debug("Here are the list of reports for id [{}]", id);
    	
    	return employeeService.readReports(id); 
    }
    
    //Task 2, get the compensation for an employee. Enter their Id in the URL. Return object is Compensation data type. Queries database. Embedded database saves info per session. Does not save entries if Spring is rebooted. 
    @GetMapping("/employee/compensation/{employeeId}")
    public Compensation readCompensation(@PathVariable String employeeId) {
    	LOG.debug("Compensation info for employee with id [{}]", employeeId);
    	
    	return employeeService.readCompensation(employeeId);
    }
    
    //Task 2, create compensation info for an employee. Enter the employee's Id in the request body. Data is saved in database but doesn't persist between sessions or Spring restarts.
    @PostMapping("/employee/compensation")
    public Compensation createCompensation(@RequestBody Compensation employeeCompensation) {
        LOG.debug("Create compensation info for [{}]", employeeCompensation);
        
        
        return employeeService.createCompensation(employeeCompensation);
    }
    
}
