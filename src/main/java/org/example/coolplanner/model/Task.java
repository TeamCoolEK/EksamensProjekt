package org.example.coolplanner.model;

import java.time.LocalDate;

public class Task {

    private int taskID;
    private String taskName;
    private String taskDetails;
    private LocalDate taskStartDate;
    private LocalDate taskDeadline;
    private int taskTimeEstimate;
    private int taskActualTime;
    private Status taskStatus;
    private int subprojectID;

    public Task(int taskID, String taskName, String taskDetails, LocalDate taskStartDate, LocalDate taskDeadline, int taskTimeEstimate, int taskActualTime, Status taskStatus, int subprojectID) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDetails = taskDetails;
        this.taskStartDate = taskStartDate;
        this.taskDeadline = taskDeadline;
        this.taskTimeEstimate = taskTimeEstimate;
        this.taskActualTime = taskActualTime;
        this.taskStatus = taskStatus;
        this.subprojectID = subprojectID;
    }

    public Task(){
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
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

