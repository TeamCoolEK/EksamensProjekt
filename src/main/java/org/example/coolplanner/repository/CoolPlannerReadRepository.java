package org.example.coolplanner.repository;

import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.Rowmapper.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoolPlannerReadRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoolPlannerReadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Finder og returnerer en Employee baseret på employeeId.
    public Employee findEmployeeById(int employeeId) {
        // SQL-forespørgsel der henter én medarbejder ud fra primærnøglen
        String sql = "SELECT * FROM employee WHERE employeeId = ?";
        // Udfører forespørgslen og mapper resultatet til et Employee-objekt
        return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), employeeId);
    }

    // Finder og returnerer en Employee baseret på email.
    public Employee findEmployeeByEmail(String email) {
        // SQL-forespørgsel der henter én medarbejder ud fra email (unik)
        String sql = "SELECT * FROM employee WHERE email = ?";
        // Udfører forespørgslen og mapper resultatet til et Employee-objekt
        return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), email);
    }

    // Finder og returnerer alle aktive SubTasks for en given Task.
    // Metoden henter SubTasks baseret på taskId og mapper dem til SubTask-objekter.
    public List<SubTask> findActiveSubTasks(int taskId) {
        // SQL-forespørgsel der henter alle subtasks, som hører til den angivne task
        String sql = "SELECT * FROM subTask WHERE taskId = ?";
        // Udfører forespørgslen og mapper hver række til et SubTask-objekt via SubTaskRowMapper
        List<SubTask> subTasks = jdbcTemplate.query(sql, new SubTaskRowMapper(), taskId);
        // Returnerer listen af SubTasks
        return subTasks;
    }
// Finder ikke lukkede projekter på employee ID
    public List<Project> findActiveProjects(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ? AND projectStatus <> 'Lukket'";
        List<Project> project = jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
        return project;
    }
    // Finder ikke lukkede subProjekter på projekt ID
    public List<SubProject> findActiveSubProjects(int projectId) {
        String sql = "SELECT * FROM subProject WHERE projectId = ?";
        List<SubProject> SubProjects = jdbcTemplate.query(sql, new SubProjectRowMapper(), projectId);
        return SubProjects;
    }
    // Finder ikke lukkede Tasks på sub projekt ID
    public List<Task> findActiveTasks(int subProjectId) {
        String sql = "SELECT * FROM task WHERE subProjectId = ?";
        List<Task> Task = jdbcTemplate.query(sql, new TaskRowMapper(), subProjectId);
        return Task;
    }
// Finder task på ID
    public Task findTaskById(int taskID) {
        String sql = "SELECT * FROM task WHERE taskId = ?";
        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), taskID);
    }
    // Finder projekt på ID
    public Project findProjectById(int projectId) {
        String sql = "SELECT * FROM project WHERE projectId = ?";
        return jdbcTemplate.queryForObject(sql, new ProjectRowMapper(), projectId);
    }
// finder sub projekt på ID
    public SubProject findSubProjectById(int subProjectId) {
        String sql = "SELECT * FROM subProject WHERE subProjectId = ?";
        return jdbcTemplate.queryForObject(sql, new SubProjectRowMapper(), subProjectId);
    }

    // Finder og returnerer alle SubTasks, der hører til en given Task.
    public List<SubTask> findSubTasksByTaskId(int taskId) {
        // SQL-forespørgsel der henter alle subtasks med reference til det angivne taskId
        String sql = "SELECT * FROM subTask WHERE taskId = ?";
        // Udfører forespørgslen og mapper hver række til et SubTask-objekt ved hjælp af SubTaskRowMapper
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), taskId);
    }

    public List<Project> findClosedProjects(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ? AND projectStatus = 'Lukket'";
        return jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
    }
// Finder tasks på sub projekt ID
    public List<Task> findTasksBySubProjectId(int subProjectId) {
        String sql = "SELECT * FROM task WHERE subProjectId = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), subProjectId);
    }
    // Finder subProjekter på projekt ID
    public List<SubProject> findSubProjectByProjectId(int projectId){
        String sql = "SELECT * FROM subProject WHERE projectId = ?";
        return jdbcTemplate.query(sql, new SubProjectRowMapper(), projectId);
    }

    public SubTask findSubTaskById(int subTaskId) {
        String sql = "SELECT * FROM subTask WHERE subTaskId = ?";
        return jdbcTemplate.queryForObject(sql, new SubTaskRowMapper(), subTaskId);
    }

    // Finder og returnerer alle employees i systemet.
    public List<Employee> findAllEmployees() {
        // SQL-forespørgsel der henter alle rækker fra employee-tabellen
        String sql = "SELECT * FROM employee";
        // Udfører forespørgslen og mapper hver række til et Employee-objekt ved hjælp af EmployeeRowMapper
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    public List<Project> findProjectsForTeamMember(int employeeId){
        String sql =
                "SELECT DISTINCT p.* " +
                "FROM project p " +
                "JOIN subproject sp ON sp.projectId = p.projectId " +
                "JOIN task t ON t.subProjectId = sp.subProjectId " +
                "JOIN subtask st ON st.taskId = t.taskId " +
                "WHERE st.employeeId = ? " +
                "AND p.projectStatus <> 'Lukket'";
        return jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
    }



}
