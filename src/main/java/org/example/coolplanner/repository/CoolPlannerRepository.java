package org.example.coolplanner.repository;


import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.SubProject;
import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.Rowmapper.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET firstName = ?, lastName = ?, email = ?, EmployeePassword = ?, EmployeeRole = ? WHERE employeeId = ?";
        jdbcTemplate.update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getRole().name(),
                employee.getEmployeeId());
    }

    public SubProject createSubProject(SubProject subProject, Project project) {
        String sql = "INSERT INTO subProject (subProjectId, subProjectName, subProjectDetails, subProjectStartDate, subProjectDeadline, subProjectTimeEstimate, subProjectActualTime, subProjectStatus, projectId) VALUES (?,?,?,?,?,?,?,?,?)";
        //dummy data
        subProject.setSubProjectActualTime(0);
        subProject.setSubProjectTimeEstimate(0);
        subProject.setStatus(Status.Ikke_startet);
        jdbcTemplate.update(sql, subProject.getSubProjectId(),
                subProject.getSubProjectName(),
                subProject.getSubProjectDetails(),
                subProject.getSubProjectStartDate(),
                subProject.getSubProjectDeadLine(),
                subProject.getSubProjectTimeEstimate(),
                subProject.getSubProjectActualTime(),
                subProject.getStatus().name(),
                project.getProjectId());
        return subProject;
    }

    public Project createProject(Project project, Employee employee) {
        String sql = "INSERT INTO project (projectId, projectName, projectDetails, projectStartDate, projectDeadline, projectTimeEstimate, projectActualTime, projectStatus, employeeId) VALUES (?,?,?,?,?,?,?,?,?)";
        project.setProjectTimeEstimate(0);
        project.setProjectActualTime(0);
        project.setStatus(Status.Ikke_startet);
        jdbcTemplate.update(sql, project.getProjectId(),
                project.getProjectName(),
                project.getProjectDetails(),
                project.getProjectStartDate(),
                project.getProjectDeadLine(),
                project.getProjectTimeEstimate(),
                project.getProjectActualTime(),
                project.getStatus().name(),
                employee.getEmployeeId());
        return project;
    }

    public Task createTask(Task task, SubProject subProject) {
        String sql = "INSERT INTO task (taskName, taskDetails, taskStartDate, taskDeadline, taskTimeEstimate, taskActualTime, taskStatus, subProjectId) VALUES (?,?,?,?,?,?,?,?)";
        task.setTaskActualTime(0);
        task.setTaskTimeEstimate(0);
        task.setTaskStatus(Status.Ikke_startet);
        jdbcTemplate.update(sql,task.getTaskName(),
                task.getTaskDetails(),
                task.getTaskStartDate(),
                task.getTaskDeadline(),
                task.getTaskTimeEstimate(),
                task.getTaskActualTime(),
                task.getTaskStatus().name(),
                subProject.getSubProjectId());
        return task;
    }

    public void updateTask(Task task) {
        String sql = "UPDATE task SET taskName = ?, taskDetails = ?, taskStartDate = ?, " + "taskDeadline = ?, taskTimeEstimate = ?, taskActualTime = ?, taskStatus = ? " +
                "WHERE taskId = ?";

        jdbcTemplate.update(sql,
                task.getTaskName(),
                task.getTaskDetails(),
                task.getTaskStartDate(),
                task.getTaskDeadline(),
                task.getTaskTimeEstimate(),
                task.getTaskActualTime(),
                task.getTaskStatus().name(),
                task.getTaskID());
    }

    public SubTask createSubTask(SubTask subTask, Task task) {
        String sql = "INSERT INTO subTask (subTaskName, subTaskDetails, subTaskStartDate, subTaskDeadline, subTaskTimeEstimate, subTaskActualTime, subTaskStatus, taskId, employeeId) VALUES (?,?,?,?,?,?,?,?,?)";

        subTask.setSubTaskActualTime(0);
        subTask.setStatus(Status.Ikke_startet);

        jdbcTemplate.update(sql,
                subTask.getSubTaskName(),
                subTask.getSubTaskDetails(),
                subTask.getSubTaskStartDate(),
                subTask.getSubTaskDeadLine(),
                subTask.getSubTaskTimeEstimate(),
                subTask.getSubTaskActualTime(),
                subTask.getStatus().name(),
                task.getTaskID(),
                subTask.getEmployeeId()
        );

        return subTask;
    }

    public void updateSubTask(SubTask subTask) {
        String sql = "UPDATE subTask SET subTaskName = ?, subTaskDetails = ?, subTaskStartDate = ?, " +
                "subTaskDeadline = ?, subTaskTimeEstimate = ?, subTaskActualTime = ?, subTaskStatus = ?, " +
                "WHERE subTaskId = ?";

        jdbcTemplate.update(sql,
                subTask.getSubTaskName(),
                subTask.getSubTaskDetails(),
                subTask.getSubTaskStartDate(),
                subTask.getSubTaskDeadLine(),
                subTask.getSubTaskTimeEstimate(),
                subTask.getSubTaskActualTime(),
                subTask.getStatus().name(),
                subTask.getSubTaskId());

    }

        public void updateProject(Project project) {
        String sql = "UPDATE project SET projectName = ?, projectDetails = ?, projectStartDato = ?, " +
                "projectDeadline = ?, projectTimeEstimate = ?, projectActualTime = ?, projectStatus = ? " +
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

    public void updateSubProject(SubProject subProject) {
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

    public void updateTaskTimeEstimate(Task task){
        String sql = "UPDATE task SET taskTimeEstimate = ? WHERE taskId = ?";
        jdbcTemplate.update(sql, task.getTaskTimeEstimate(), task.getTaskID());
    }

    public void closeProject(int projectId) {
        String sql = "UPDATE project SET projectStatus = 'LUKKET' WHERE projectId = ?";
        jdbcTemplate.update(sql, projectId);
    }

    public void updateSubProjectTimeEstimate(SubProject subProject) {
        String sql = "UPDATE subProject SET subProjectTimeEstimate = ? WHERE subProjectId = ?";
        jdbcTemplate.update(sql, subProject.getSubProjectTimeEstimate(), subProject.getSubProjectId());
    }
    public void updateProjectTimeEstimate(Project project){
        String sql = "UPDATE project SET projectTimeEstimate = ? WHERE projectId = ?";
        jdbcTemplate.update(sql, project.getProjectTimeEstimate(), project.getProjectId());
    }
}




