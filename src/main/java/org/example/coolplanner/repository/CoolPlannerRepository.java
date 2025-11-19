package org.example.coolplanner.repository;


import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.SubProject;
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

    public SubProject createSubProject(SubProject subProject){
        String sql = "INSERT INTO subProject (subProjectId, subProjectName, subProjectDetails, subProjectStartDate, subProjectDeadline, subProjectTimeEstimate, subProjectActualTime, subProjectStatus) VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, subProject.getSubProjectId(),
                subProject.getSubProjectName(),
                subProject.getSubProjectDetails(),
                subProject.getSubProjectStartDate(),
                subProject.getSubProjectDeadLine(),
                subProject.getSubProjectTimeEstimate(),
                subProject.getSubProjectActualTime(),
                subProject.getStatus());
        return subProject;
    }
}
