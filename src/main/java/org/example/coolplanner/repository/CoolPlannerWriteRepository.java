package org.example.coolplanner.repository;

import org.example.coolplanner.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CoolPlannerWriteRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoolPlannerWriteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Opretter en ny bruger (Employee) i databasen.
// Metoden indsætter brugerens oplysninger i employee-tabellen.
    public void createEmployee(Employee employee) {

        // SQL-statement der indsætter en ny række i employee-tabellen
        String sql = "INSERT INTO employee (firstName, lastName, email, EmployeePassword, EmployeeRole) VALUES (?,?,?,?,?)";

        // Udfører INSERT med JdbcTemplate og mapper værdier fra Employee-objektet
        jdbcTemplate.update(sql,
                employee.getFirstName(),   // Fornavn
                employee.getLastName(),    // Efternavn
                employee.getEmail(),       // Email (unik)
                employee.getPassword(),    // Password (lagres som string)
                employee.getRole().name()  // Rolle gemmes som enum-navn
        );
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

    // Tjekker om der allerede findes en bruger med den angivne email.
    public boolean emailExists(String email) {
        // SQL-forespørgsel der tæller hvor mange rækker der findes med den givne email
        String sql = "SELECT COUNT(*) FROM employee WHERE email = ?";
        // Udfører forespørgslen og får antallet af matches tilbage
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        // Returnerer true hvis der findes mindst én bruger med emailen,
        // ellers false (eller hvis resultatet mod forventning er null)
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

    // Opretter en ny SubTask og gemmer den i databasen.
// SubTask får som udgangspunkt 0 i faktisk tid og status "Ikke_startet".
    public SubTask createSubTask(SubTask subTask, Task task) {
        // SQL-statement der indsætter en ny række i subTask-tabellen
        String sql = "INSERT INTO subTask (subTaskName, subTaskDetails, subTaskStartDate, subTaskDeadline, " +
                "subTaskTimeEstimate, subTaskActualTime, subTaskStatus, taskId, employeeId) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        // Initialiserer standardværdier for en ny subtask
        subTask.setSubTaskActualTime(0);           // Ingen tid registreret endnu
        subTask.setSubTaskStatus(Status.Ikke_startet); // SubTask starter som ikke påbegyndt

        // Udfører INSERT og mapper værdier fra SubTask- og Task-objekterne
        jdbcTemplate.update(sql,
                subTask.getSubTaskName(),           // Navn på subtask
                subTask.getSubTaskDetails(),        // Beskrivelse
                subTask.getSubTaskStartDate(),      // Startdato
                subTask.getSubTaskDeadLine(),       // Deadline
                subTask.getSubTaskTimeEstimate(),   // Estimeret tid
                subTask.getSubTaskActualTime(),     // Faktisk tid (0 ved oprettelse)
                subTask.getSubTaskStatus().name(),  // Status gemmes som enum-navn
                task.getTaskId(),                   // Reference til parent Task
                subTask.getEmployeeId()             // Ansvarlig medarbejder
        );

        // Returnerer det oprettede SubTask-objekt
        return subTask;
    }

    public void updateSubTask(SubTask subTask) {
        String sql = "UPDATE subTask SET subTaskName = ?, subTaskDetails = ?, subTaskStartDate = ?, " +
                "subTaskDeadline = ?, subTaskTimeEstimate = ?, subTaskActualTime = ?, subTaskStatus = ?, employeeId = ? " +
                "WHERE subTaskId = ?";

        jdbcTemplate.update(sql,
                subTask.getSubTaskName(),
                subTask.getSubTaskDetails(),
                subTask.getSubTaskStartDate(),
                subTask.getSubTaskDeadLine(),
                subTask.getSubTaskTimeEstimate(),
                subTask.getSubTaskActualTime(),
                subTask.getSubTaskStatus().name(),
                subTask.getEmployeeId(),
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

    // Marker en SubTask som færdig ved at registrere faktisk tidsforbrug og sætte dens status til "Lukket".
    public void completeSubTask(int subTaskId, int actualTime) {

        // SQL-statement der opdaterer faktisk tid og status for den valgte subtask
        String sql = "UPDATE subTask SET subTaskActualTime = ?, subTaskStatus = ? WHERE subTaskId = ?";

        // Udfører opdateringen: actualTime gemmes som den faktiske brugte tid - status sættes til "Lukket"
        // - opdateringen gælder kun den subtask med det angivne ID
        jdbcTemplate.update(sql,
                actualTime,
                Status.Lukket.name(),
                subTaskId
        );
    }

    // Opdaterer en Tasks samlede faktiske tid i databasen.
    public void updateTaskActualTime(Task task) {
        // SQL-statement der opdaterer taskens faktiske tid baseret på taskId
        String sql = "UPDATE task SET taskActualTime = ? WHERE taskId = ?";

        // Udfører opdateringen med værdier fra Task-objektet
        jdbcTemplate.update(sql,
                task.getTaskActualTime(), // Samlet faktisk tid for tasken
                task.getTaskId()          // ID på den task der skal opdateres
        );
    }

    // Sætter SubProjects faktiske tid.
    public void updateSubProjectActualTime(SubProject subProject) {
        String sql = "UPDATE subProject SET  subProjectActualTime = ? WHERE subProjectId = ?";
        jdbcTemplate.update(sql, subProject.getSubProjectActualTime(), subProject.getSubProjectId());
    }

    // Sætter Projects faktiske tid.
    public void updateProjectActualTime(Project project) {
        String sql = "UPDATE project SET projectActualTime = ? WHERE projectId = ?";
        jdbcTemplate.update(sql, project.getProjectActualTime(), project.getProjectId());
    }

    // Opdaterer status på en SubTask.
    public void updateSubTaskStatus(int subTaskId, Status status) {
        // SQL-statement der opdaterer status-feltet for den valgte subtask
        String sql = "UPDATE subTask SET subTaskStatus = ? WHERE subTaskId = ?";

        // Udfører opdateringen: status gemmes som enum-navn - opdateringen gælder kun subtasken med det angivne ID
        jdbcTemplate.update(sql,
                status.name(),
                subTaskId
        );
    }

    // opdatere Tasks status
    public void updateTaskStatus(int taskId, Status status) {
        String sql = "UPDATE task SET taskStatus = ? WHERE taskId = ?";
        jdbcTemplate.update(sql, status.name(), taskId);
    }

    // opdatere SubProjects status
    public void updateSubProjectStatus(int subProjectId, Status status) {
        String sql = "UPDATE subProject SET subProjectStatus = ? WHERE subProjectId = ?";
        jdbcTemplate.update(sql, status.name(), subProjectId);
    }

    // opdatere Projects status
    public void updateProjectStatus(int projectId, Status status) {
        String sql = "UPDATE project SET projectStatus = ? WHERE projectId = ?";
        jdbcTemplate.update(sql, status.name(), projectId);
    }
}