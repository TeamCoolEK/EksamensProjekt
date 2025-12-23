package org.example.coolplanner.model;

import java.time.LocalDate;

// Domæneklasse der repræsenterer en SubTask (delopgave) i systemet.
// Klassen bruges som model mellem database, service- og controller-lag.
public class SubTask {
    // Unik identifikation af subtask (primærnøgle i databasen)
    private int subTaskId;
    // Navn på delopgaven
    private String subTaskName;
    // Beskrivelse af delopgaven
    private String subTaskDetails;
    // Startdato for delopgaven
    private LocalDate subTaskStartDate;
    // Deadline for delopgaven
    private LocalDate subTaskDeadLine;
    // Estimeret tidsforbrug (i timer)
    private int subTaskTimeEstimate;
    // Faktisk registreret tidsforbrug (i timer)
    private int subTaskActualTime;
    // Status på delopgaven (Enum: Ikke_startet, I_gang, Lukket)
    private Status subTaskStatus;
    // ID på den medarbejder der er ansvarlig for subtasken (FK)
    private int employeeId;
    // ID på den Task som denne SubTask hører under (FK)
    private int taskId;
    // Objekt-reference til ansvarlig medarbejder
    // Bruges primært i dashboard-controlleren til visning
    // (ikke direkte gemt i databasen)
    private Employee responsibleEmployee;

    // Fuld constructor – bruges typisk ved mapping fra database
    public SubTask(int subTaskId,
                   String subTaskName,
                   String subTaskDetails,
                   LocalDate subTaskStartDate,
                   LocalDate subTaskDeadLine,
                   int subTaskTimeEstimate,
                   int subTaskActualTime,
                   Status subTaskStatus,
                   int taskId) {

        this.subTaskId = subTaskId;
        this.subTaskName = subTaskName;
        this.subTaskDetails = subTaskDetails;
        this.subTaskStartDate = subTaskStartDate;
        this.subTaskDeadLine = subTaskDeadLine;
        this.subTaskTimeEstimate = subTaskTimeEstimate;
        this.subTaskActualTime = subTaskActualTime;
        this.subTaskStatus = subTaskStatus;
        this.taskId = taskId;
    }

    // Tom constructor
    // Bruges af Spring, RowMappers og ved binding fra formularer (Thymeleaf)
    public SubTask() {
    }

    // Getters og Setters
    public int getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public String getSubTaskName() {
        return subTaskName;
    }

    public void setSubTaskName(String subTaskName) {
        this.subTaskName = subTaskName;
    }

    public String getSubTaskDetails() {
        return subTaskDetails;
    }

    public void setSubTaskDetails(String subTaskDetails) {
        this.subTaskDetails = subTaskDetails;
    }

    public LocalDate getSubTaskStartDate() {
        return subTaskStartDate;
    }

    public void setSubTaskStartDate(LocalDate subTaskStartDate) {
        this.subTaskStartDate = subTaskStartDate;
    }

    public LocalDate getSubTaskDeadLine() {
        return subTaskDeadLine;
    }

    public void setSubTaskDeadLine(LocalDate subTaskDeadLine) {
        this.subTaskDeadLine = subTaskDeadLine;
    }

    public int getSubTaskTimeEstimate() {
        return subTaskTimeEstimate;
    }

    public void setSubTaskTimeEstimate(int subTaskTimeEstimate) {
        this.subTaskTimeEstimate = subTaskTimeEstimate;
    }

    public int getSubTaskActualTime() {
        return subTaskActualTime;
    }

    public void setSubTaskActualTime(int subTaskActualTime) {
        this.subTaskActualTime = subTaskActualTime;
    }

    public Status getSubTaskStatus() {
        return subTaskStatus;
    }

    public void setSubTaskStatus(Status subTaskStatus) {
        this.subTaskStatus = subTaskStatus;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    // Returnerer ansvarlig medarbejder som objekt
    public Employee getResponsibleEmployee() {
        return responsibleEmployee;
    }

    // Sætter ansvarlig medarbejder (bruges typisk kun til visning)
    public void setResponsibleEmployee(Employee responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }
}