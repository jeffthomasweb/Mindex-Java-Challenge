package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {
	private Employee employee;
	private List<Employee> numberOfReports;
	
	public ReportingStructure() {
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Employee> getNumberOfReports() {
		return numberOfReports;
	}

	public void setNumberOfReports(List<Employee> numberOfReports) {
		this.numberOfReports = numberOfReports;
	}
	
	}

