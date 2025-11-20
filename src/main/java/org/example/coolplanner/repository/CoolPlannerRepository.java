package org.example.coolplanner.repository;


import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.SubProject;
import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.Rowmapper.*;
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
        String sql = "INSERT INTO employee (firstName, lastName, email, EmployeePassword, EmployeeRole) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getRole().name());
    }

    public void updateEmployee(Employee employee){
        String sql = "UPDATE employee SET firstName = ?, lastName = ?, email = ?, EmployeePassword = ?, EmployeeRole = ? WHERE employeeId = ?";
        jdbcTemplate.update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getRole().name(),
                employee.getEmployeeId());
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM employee WHERE email =?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public Employee findEmployeeById(int employeeId) {
        String sql = "SELECT * FROM employee WHERE employeeId = ?";
        return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), employeeId);
    }

    public Employee findEmployeeByEmail(String email) {
        String sql = "SELECT * FROM employee WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), email);
    }

    public SubProject createSubProject(SubProject subProject) {
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

    public Project createProject(Project project) {
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

    public SubTask createSubTask(SubTask subTask, UserStory userStory) {
        String sql = "INSERT INTO subTask (subTaskName, subTaskDetails, subTaskStartDate, subTaskDeadline, subTaskTimeEstimate, subTaskActualTime, subTaskStatus, userStoryId, employeeId) VALUES (?,?,?,?,?,?,?,?,?)";
        subTask.setSubTaskActualTime(0);
        subTask.setStatus(Status.Ikke_startet);
        jdbcTemplate.update(sql, subTask.getSubTaskName(),
                subTask.getSubTaskDetails(),
                subTask.getSubTaskStartDate(),
                subTask.getSubTaskDeadLine(),
                subTask.getSubTaskTimeEstimate(),
                subTask.getSubTaskActualTime(),
                subTask.getStatus().name(),
                userStory.getUserStoryID());
        return subTask;
    }

    public List<SubTask> findActiveSubTasks(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ?";
        List<SubTask> SubTask = jdbcTemplate.query(sql, new SubTaskRowMapper(), employeeId);
        return SubTask;
    }

    public List<Project> findActiveProjects(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ?";
        List<Project> projects = jdbcTemplate.query(sql, new ProjectRowMapper(), employeeId);
        return projects;
    }

    public List<SubProject> findActiveSubProjects(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ?";
        List<SubProject> SubProjects = jdbcTemplate.query(sql, new SubProjectRowMapper(), employeeId);
        return SubProjects;
    }

    public List<UserStory> findActiveUserStories(int employeeId) {
        String sql = "SELECT * FROM project WHERE employeeId = ?";
        List<UserStory> UserStory = jdbcTemplate.query(sql, new UserStoryRowMapper(), employeeId);
        return UserStory;
    }

    public UserStory findUserStoryById(int userStoryID) {
        String sql = "SELECT * FROM UserStory WHERE UserStoryID = ?";
        return jdbcTemplate.queryForObject(sql, new UserStoryRowMapper(), userStoryID);
    }


    public Project findProjectById(int projectId) {
        String sql = "SELECT * FROM project WHERE projectId = ?";
        return jdbcTemplate.queryForObject(sql, new ProjectRowMapper(), projectId);
    }


    public void updateProject(Project project){
        String sql = "UPDATE project SET projectName = ?, projectDetails = ?, projectStartDato = ?, " +
                "projectDeadline = ?, projectTimeEstimate = ?, projectActualTime = ?, projectStatus = ?, " +
                "WHERE projectId = ?";

        jdbcTemplate.update(sql,
                project.getProjectName(),
                project.getProjectDetails(),
                project.getProjectStartDate(),
                project.getProjectDeadLine(),
                project.getProjectTimeEstimate(),
                project.getProjectActualTime(),
                project.getStatus(),
                project.getProjectId());
    }

    public SubProject findSubProjectById(int subProjectId) {
        String sql = "SELECT * FROM project WHERE subProjectId = ?";
        return jdbcTemplate.queryForObject(sql, new SubProjectRowMapper(), subProjectId);
    }

    public void updateSubProject(SubProject subProject){
        String sql = "UPDATE subProject SET subProjectName = ?, subProjectDetails = ?, subProjectStartDato = ?, " +
                "subProjectDeadline = ?, subProjectTimeEstimate = ?, subProjectActualTime = ?, subProjectStatus = ?, " +
                "WHERE subProjectId = ?";

        jdbcTemplate.update(sql,
                subProject.getSubProjectName(),
                subProject.getSubProjectDetails(),
                subProject.getSubProjectStartDate(),
                subProject.getSubProjectDeadLine(),
                subProject.getSubProjectTimeEstimate(),
                subProject.getSubProjectActualTime(),
                subProject.getStatus(),
                subProject.getSubProjectId());
    }

//        public List<Task> findActiveTasks ( int employeeId){
//            String sql = "SELECT * FROM project WHERE employeeId = ?";
//            List<Task> Task = jdbcTemplate.query(sql, new TaskRowMapper(), employeeId);
//            return Task;
//        }






