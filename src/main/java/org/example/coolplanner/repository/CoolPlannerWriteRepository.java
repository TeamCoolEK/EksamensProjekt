package org.example.coolplanner.repository;


import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.SubProject;
import org.example.coolplanner.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CoolPlannerWriteRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoolPlannerWriteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
 // Metode til at oprette en bruger
    public void createEmployee(Employee employee) {
        String sql = "INSERT INTO employee (firstName, lastName, email, EmployeePassword, EmployeeRole) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getRole().name());
    }
// Metode til at opdatere en bruger
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
// Tjekker om man opretter sig med en Email som er i brug
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM employee WHERE email =?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
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
        jdbcTemplate.update(sql, task.getTaskName(),
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
                task.getTaskId());
    }
// Metode til at oprette SubTask.
    public SubTask createSubTask(SubTask subTask, Task task) {
        String sql = "INSERT INTO subTask (subTaskName, subTaskDetails, subTaskStartDate, subTaskDeadline, subTaskTimeEstimate, subTaskActualTime, subTaskStatus, taskId, employeeId) VALUES (?,?,?,?,?,?,?,?,?)";

        subTask.setSubTaskActualTime(0);
        subTask.setSubTaskStatus(Status.I_gang);

        jdbcTemplate.update(sql,
                subTask.getSubTaskName(),
                subTask.getSubTaskDetails(),
                subTask.getSubTaskStartDate(),
                subTask.getSubTaskDeadLine(),
                subTask.getSubTaskTimeEstimate(),
                subTask.getSubTaskActualTime(),
                subTask.getSubTaskStatus().name(),
                task.getTaskId(),
                subTask.getEmployeeId()
        );

        return subTask;
    }

    public void updateSubTask(SubTask subTask) {
        String sql = "UPDATE subTask SET subTaskName = ?, subTaskDetails = ?, subTaskStartDate = ?, " +
                "subTaskDeadline = ?, subTaskTimeEstimate = ?, subTaskActualTime = ?, subTaskStatus = ? " +
                "WHERE subTaskId = ?";

        jdbcTemplate.update(sql,
                subTask.getSubTaskName(),
                subTask.getSubTaskDetails(),
                subTask.getSubTaskStartDate(),
                subTask.getSubTaskDeadLine(),
                subTask.getSubTaskTimeEstimate(),
                subTask.getSubTaskActualTime(),
                subTask.getSubTaskStatus().name(),
                subTask.getSubTaskId());

    }
// Metode til at opdatere et Project
    public void updateProject(Project project) {
        String sql = "UPDATE project SET projectName = ?, projectDetails = ?, projectStartDate = ?, " +
                "projectDeadline = ?, projectTimeEstimate = ?, projectActualTime = ?, projectStatus = ? " +
                "WHERE projectId = ?";

        jdbcTemplate.update(sql,
                project.getProjectName(),
                project.getProjectDetails(),
                project.getProjectStartDate(),
                project.getProjectDeadLine(),
                project.getProjectTimeEstimate(),
                project.getProjectActualTime(),
                project.getStatus().name(),
                project.getProjectId());
    }
    // Metode til at opdatere et SubProject
    public void updateSubProject(SubProject subProject) {
        String sql = "UPDATE subProject SET subProjectName = ?, subProjectDetails = ?, subProjectStartDate = ?, " +
                "subProjectDeadline = ?, subProjectTimeEstimate = ?, subProjectActualTime = ?, subProjectStatus = ? " +
                "WHERE subProjectId = ?";

        jdbcTemplate.update(sql,
                subProject.getSubProjectName(),
                subProject.getSubProjectDetails(),
                subProject.getSubProjectStartDate(),
                subProject.getSubProjectDeadLine(),
                subProject.getSubProjectTimeEstimate(),
                subProject.getSubProjectActualTime(),
                subProject.getStatus().name(),
                subProject.getSubProjectId());
    }
// Metode som opdatere den estimerede tid på en Task.
    public void updateTaskTimeEstimate(Task task) {
        String sql = "UPDATE task SET taskTimeEstimate = ? WHERE taskId = ?";
        jdbcTemplate.update(sql, task.getTaskTimeEstimate(), task.getTaskId());
    }

    public void closeProject(int projectId) {
        String sql = "UPDATE project SET projectStatus = 'Lukket' WHERE projectId = ?";
        jdbcTemplate.update(sql, projectId);
    }

    // Metode som opdatere den estimerede tid på et SubProject.
    public void updateSubProjectTimeEstimate(SubProject subProject) {
        String sql = "UPDATE subProject SET subProjectTimeEstimate = ? WHERE subProjectId = ?";
        jdbcTemplate.update(sql, subProject.getSubProjectTimeEstimate(), subProject.getSubProjectId());
    }
    // Metode som opdatere den estimerede tid på et Project.
    public void updateProjectTimeEstimate(Project project) {
        String sql = "UPDATE project SET projectTimeEstimate = ? WHERE projectId = ?";
        jdbcTemplate.update(sql, project.getProjectTimeEstimate(), project.getProjectId());
    }
// Metode til at markere en SubTasks faktiske tid og lukke den
    public void completeSubTask(int subTaskId, int actualTime) {
        String sql = "UPDATE subTask SET subTaskActualTime = ?, subTaskStatus = ? WHERE subTaskId = ?";
        jdbcTemplate.update(sql, actualTime, Status.Lukket.name(), subTaskId);
    }
// Sætter Tasks faktiske tid.
    public void updateTaskActualTime(Task task) {
        String sql = "UPDATE task SET taskActualTime = ? WHERE taskId = ?";
        jdbcTemplate.update(sql, task.getTaskActualTime(), task.getTaskId());
    }
// Sætter SubProjects faktiske tid.
    public void updateSubProjectActualTime(SubProject subProject){
        String sql = "UPDATE subProject SET  subProjectActualTime = ? WHERE subProjectId = ?";
        jdbcTemplate.update(sql, subProject.getSubProjectActualTime(), subProject.getSubProjectId());
    }
    // Sætter Projects faktiske tid.
    public void updateProjectActualTime(Project project){
        String sql = "UPDATE project SET projectActualTime = ? WHERE projectId = ?";
        jdbcTemplate.update(sql, project.getProjectActualTime(), project.getProjectId());
    }

    public void updateSubTaskStatus(int subTaskId, Status status) {
        String sql = "UPDATE subTask SET subTaskStatus = ? WHERE subTaskId = ?";
        jdbcTemplate.update(sql, status.name(), subTaskId);
    }

    public void updateTaskStatus(int taskId, Status status){
        String sql = "UPDATE task SET taskStatus = ? WHERE taskId = ?";
        jdbcTemplate.update(sql, status.name(), taskId);
    }

    public void updateSubProjectStatus(int subProjectId, Status status){
        String sql = "UPDATE subProject SET subProjectStatus = ? WHERE subProjectId = ?";
        jdbcTemplate.update(sql, status.name(), subProjectId);
    }

    public void updateProjectStatus(int projectId, Status status){
        String sql = "UPDATE project SET projectStatus = ? WHERE projectId = ?";
        jdbcTemplate.update(sql, status.name(), projectId);
    }

//    public int countSubTasksByProjectId(int projectId){
//        String sql = ""
//    return jdbcTemplate.queryForObject(sql, Integer.class, projectId);
//    }

}