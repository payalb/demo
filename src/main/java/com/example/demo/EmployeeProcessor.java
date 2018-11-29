package com.example.demo;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demo.dto.Employee;

@Component
public class EmployeeProcessor implements ItemProcessor<Employee, Employee>{
	
	int id=1;

	@Override
	public Employee process(Employee employee) throws Exception {
		employee.setId(id++);
		return employee;
	}

}
