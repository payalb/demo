package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.example.demo.dto.Employee;

public class EmployeePreparedStatementSetter implements ItemPreparedStatementSetter<Employee> {

	@Override
	public void setValues(Employee item, PreparedStatement ps) throws SQLException {
		ps.setInt(1, item.getId());
		ps.setString(2, item.getName());
		ps.setFloat(3, item.getSalary());
		ps.setString(4, item.getEmailId());
	}

}
