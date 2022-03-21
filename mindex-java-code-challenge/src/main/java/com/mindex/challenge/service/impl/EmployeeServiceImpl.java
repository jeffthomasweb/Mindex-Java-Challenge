package com.mindex.challenge.service.impl;


import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;
    
    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }
    
    //Find Employee Reports by EmployeeId for nesting 2 levels deep. A recursive function probably would be better since this function can not provide list of reports if there is nesting many levels deep.
    @Override
    public List<Employee> readReports(String id) {
        LOG.debug("List of reports that report to employee with id [{}]", id);
        ReportingStructure listOfReports = new ReportingStructure();
        //Better solution may have been to make ReportingStructure iterable. Since I didn't I used an ArrayList.
        List<Employee> listEmployees = new ArrayList<Employee>();
        List<Employee> secondListEmployees = new ArrayList<Employee>();
        List<List<Employee>> combinedList = new ArrayList<List<Employee>>();
        List<Employee> listAfterCombinedList = new ArrayList<Employee>();
        //Query database and save as Employee data type.
        Employee employee = employeeRepository.findByEmployeeId(id);
        
        //Check if direct reports is null.
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        
        
        //If direct reports is not null:
        if (employee.getDirectReports() != null) {
        	//Add to list if direct reports is not null.
        	listEmployees.addAll(employee.getDirectReports());
        	//For each employee in the first level of reports, add their direct reports to a second list. 
        	for (Employee individual : listEmployees) {
        		if (individual.getDirectReports() != null) {
        			secondListEmployees.addAll(individual.getDirectReports());
        	}
        	}
        	//Combine the two lists.
        	combinedList.add(listEmployees);
        	combinedList.add(secondListEmployees);
        	//Add the final list of employee ids to one list. Use the read () function to get the employee data structure.
        	for (List<Employee> individualInList : combinedList) {
        		for (Employee subIndividualInList: individualInList) {
        			//
        			listAfterCombinedList.add(read(subIndividualInList.getEmployeeId()));
        	
        	}
        	}
        	//Set the data as Reporting Structure data type.
        	listOfReports.setNumberOfReports(listAfterCombinedList);
        	}
        
        return listOfReports.getNumberOfReports();
    }
    
    //Implement interface for Task 2, reading compensation information by employeeId
    @Override
    public Compensation readCompensation(String employeeId) {
    	LOG.debug("Compensation info for employee with id [{}]", employeeId);
    	Compensation compensationEmployeeId = compensationRepository.findByEmployeeId(employeeId);
    	if (compensationEmployeeId == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
    	
    	return compensationEmployeeId;
    	
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
    
    //Implement interface for Task 2, creating compensation data type.
    @Override
    public Compensation createCompensation(@RequestBody Compensation employeeCompensation) {
    	LOG.debug("Create compensation info for [{}", employeeCompensation);
    	
    	
    	compensationRepository.save(employeeCompensation);
    	
    	return employeeCompensation;
    }
}
