package org.example.coolplanner.repository.Rowmapper;

import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.EmployeeRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// RowMapper der mapper en række fra employee-tabellen
// til et Employee-domæneobjekt.
public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

        // Opretter et nyt Employee-objekt
        Employee employee = new Employee();

        // Mapper primitive felter direkte fra databasen
        employee.setEmployeeId(rs.getInt("employeeId"));        // Primærnøgle
        employee.setFirstName(rs.getString("firstName"));       // Fornavn
        employee.setLastName(rs.getString("lastName"));         // Efternavn
        employee.setEmail(rs.getString("email"));               // Email
        employee.setPassword(rs.getString("employeePassword")); // Password

        // Konverterer rolle fra String i databasen til EmployeeRole enum
        // Dette sikrer typesikkerhed og undgår "magic strings" i koden
        employee.setRole(EmployeeRole.valueOf(rs.getString("employeeRole")));

        // Returnerer det færdig-mappede Employee-objekt
        return employee;
    }
}