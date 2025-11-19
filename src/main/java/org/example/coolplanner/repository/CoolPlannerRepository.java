package org.example.coolplanner.repository;


import org.example.coolplanner.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CoolPlannerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoolPlannerRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createEmployee(Employee employee){
        String sql = "INSERT INTO employee (firstName, lastName, email, EmployeePassword, EmployeeRole) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPassword(), employee.getRole().name());
    }

}
