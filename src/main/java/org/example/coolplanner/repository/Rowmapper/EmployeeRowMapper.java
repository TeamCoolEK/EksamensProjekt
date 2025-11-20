package org.example.coolplanner.repository.Rowmapper;

import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.EmployeeRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

        Employee employee = new Employee();

        employee.setEmployeeId(rs.getInt("employeeId"));
        employee.setFirstName(rs.getString("firstName"));
        employee.setLastName(rs.getString("lastName"));
        employee.setEmail(rs.getString("email"));
        employee.setPassword(rs.getString("employeePassword"));

        // Enum henter String fra Database og konverterer til EmployeeRole
        employee.setRole(EmployeeRole.valueOf(rs.getString("employeeRole")));

        return employee;
    }
}