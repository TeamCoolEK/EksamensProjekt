package org.example.coolplanner.repository;


import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.Rowmapper.ProjectRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CoolPlannerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoolPlannerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createEmployee(Employee employee) {
        String sql = "INSERT INTO employee (EmployeeId, EmployeeFirtsName, EmployeeLastName, EmployeeEmail, EmployeePassword, EmployeeRole) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPassword(), employee.getRole());
    }

    public List<Project> findActiveProjects(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ?";
        List<Project> projects = jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
        return projects;
    }

    public List<SubProject> findActiveSubProjects(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ?";
        List<SubProject> SubProjects = jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
        return SubProjects;
    }

    public List<UserStory> findActiveUserStories(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ?";
        List<UserStory> UserStory = jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
        return UserStory;
    }


    public List<Task> findActiveTasks(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ?";
        List<Task> Task = jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
        return Task;
    }
}




