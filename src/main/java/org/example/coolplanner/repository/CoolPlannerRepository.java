package org.example.coolplanner.repository;


import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.Project;
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
        jdbcTemplate.update(sql, employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getRole().name());
    }

    public boolean emailExists(String email){
        String sql = "SELECT COUNT(*) FROM employee WHERE email =?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public Project createProject(Project project){
        String sql = "INSERT INTO project (projectId, projectName, projectDetails, projectStartDate, projectDeadline, projectTimeEstimate, projectActualTime, projectStatus) VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, project.getProjectId(),
                project.getProjectName(),
                project.getProjectDetails(),
                project.getProjectStartDate(),
                project.getProjectDeadLine(),
                project.getProjectTimeEstimate(),
                project.getProjectActualTime(),
                project.getStatus());
        return project;
    }
}
