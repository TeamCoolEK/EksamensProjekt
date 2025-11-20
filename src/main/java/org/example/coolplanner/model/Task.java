package org.example.coolplanner.model;

import java.time.LocalDate;

public class Task {
    private int taskId;
    private String taskName;
    private String taskDetails;
    private LocalDate taskStartDate;
    private LocalDate taskDeadLine;
    private int taskTimeEstimate;
    private int taskActualTime;
    private Status status;

    public Task(int taskId, String taskName, String taskDetails, LocalDate taskStartDate, LocalDate taskDeadLine, int taskTimeEstimate, int taskActualTime, Status status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDetails = taskDetails;
        this.taskStartDate = taskStartDate;
        this.taskDeadLine = taskDeadLine;
        this.taskTimeEstimate = taskTimeEstimate;
        this.taskActualTime = taskActualTime;
        this.status = status;
    }

    public Task () {} //tom konstuktør <3

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

    public LocalDate getTaskDeadLine() {
        return taskDeadLine;
    }

    public void setTaskDeadLine(LocalDate taskDeadLine) {
        this.taskDeadLine = taskDeadLine;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
