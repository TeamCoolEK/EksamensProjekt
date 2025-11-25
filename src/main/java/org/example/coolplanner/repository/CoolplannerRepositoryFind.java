package org.example.coolplanner.repository;

import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.Rowmapper.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoolplannerRepositoryFind {

    private final JdbcTemplate jdbcTemplate;

    public CoolplannerRepositoryFind(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Employee findEmployeeById(int employeeId) {
        String sql = "SELECT * FROM employee WHERE employeeId = ?";
        return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), employeeId);
    }

    public Employee findEmployeeByEmail(String email) {
        String sql = "SELECT * FROM employee WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), email);
    }

    public List<SubTask> findActiveSubTasks(int employeeId) {
        String sql = "SELECT * FROM subTask WHERE employeeId = ?";
        List<SubTask> SubTask = jdbcTemplate.query(sql, new SubTaskRowMapper(), employeeId);
        return SubTask;
    }

    public List<Project> findActiveProjects(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ?";
        List<Project> project = jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
        return project;
    }

    public List<SubProject> findActiveSubProjects(int employeeId) {
        String sql = "SELECT * FROM subProject WHERE projectId = ?";
        List<SubProject> SubProjects = jdbcTemplate.query(sql, new SubProjectRowMapper(), employeeId);
        return SubProjects;
    }

    public List<Task> findActiveTasks(int employeeId) {
        String sql = "SELECT * FROM task WHERE subProjectId = ?";
        List<Task> Task = jdbcTemplate.query(sql, new TaskRowMapper(), employeeId);
        return Task;
    }

    public Task findTaskById(int taskID) {
        String sql = "SELECT * FROM Task WHERE TaskID = ?";
        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), taskID);
    }


    public Project findProjectById(int projectId) {
        String sql = "SELECT * FROM project WHERE projectId = ?";
        return jdbcTemplate.queryForObject(sql, new ProjectRowMapper(), projectId);
    }

    public SubProject findSubProjectById(int subProjectId) {
        String sql = "SELECT * FROM subProject WHERE subProjectId = ?";
        return jdbcTemplate.queryForObject(sql, new SubProjectRowMapper(), subProjectId);
    }

    public List<SubTask> findSubTasksByTaskId(int taskId){
        String sql = "SELECT * FROM subTask WHERE taskId = ?";
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), taskId);
    }

    public List<Project> findClosedProjects(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ? AND projectStatus = 'LUKKET'";
        return jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
    }

    public List<Task> findTasksBySubProjectId(int subProjectId) {
        String sql = "SELECT * FROM task WHERE subProjectId = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), subProjectId);
    }

    public List<SubProject> findSubProjectByProjectId(int projectId){
        String sql = "SELECT * FROM subProject WHERE projectId = ?";
        return jdbcTemplate.query(sql, new SubProjectRowMapper(), projectId);
    }
}
