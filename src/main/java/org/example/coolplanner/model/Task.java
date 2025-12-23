package org.example.coolplanner.model;

import java.time.LocalDate;

// Task-klassen fungerer som skabelon for Tasks.
// Den bruges til at oprette og overfører task-objekter mellem HTML, controller, service og database //


public class Task {

    private int taskId; //task ID bruges fx når en task skal opdateres //

    // Oplysninger som brugeren indtaster i HTML formularen //
    private String taskName;
    private String taskDetails;
    private LocalDate taskStartDate;
    private LocalDate taskDeadline;
    // tidsoplysninger som gemmes sammen med tasken //
    private int taskTimeEstimate;
    private int taskActualTime;
    private Status taskStatus; //Opgavens status via Status enum//
    private int subprojectID; //Hvilket subProject Tasken hører til//

    // Konstruktør, når man vil oprette en task og allerede kender alle værdierne //
    public Task(int taskId, String taskName, String taskDetails, LocalDate taskStartDate, LocalDate taskDeadline, int taskTimeEstimate, int taskActualTime, Status taskStatus, int subprojectID) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDetails = taskDetails;
        this.taskStartDate = taskStartDate;
        this.taskDeadline = taskDeadline;
        this.taskTimeEstimate = taskTimeEstimate;
        this.taskActualTime = taskActualTime;
        this.taskStatus = taskStatus;
        this.subprojectID = subprojectID;
    }

    // Tom konstruktør, bruges af Spring og RowMapper automatisk oprettelse af en tom task, som derefter udfyldes med data //
    public Task(){
    }

    // Getters og setters bruges til at læse og ændre værdierne i objektet //
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public LocalDate getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(LocalDate taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public LocalDate getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(LocalDate taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public int getTaskTimeEstimate() {
        return taskTimeEstimate;
    }

    public void setTaskTimeEstimate(int taskTimeEstimate) {
        this.taskTimeEstimate = taskTimeEstimate;
    }

    public int getTaskActualTime() {
        return taskActualTime;
    }

    public void setTaskActualTime(int taskActualTime) {
        this.taskActualTime = taskActualTime;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }
}

