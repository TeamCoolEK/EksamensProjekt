package org.example.coolplanner.model;

import java.time.LocalDate;
import java.util.Date;

public class SubTask {
    private int subTaskId;
    private String subTaskName;
    private String subTaskDetails;
    private LocalDate subTaskStartDate;
    private LocalDate subTaskDeadLine;
    private int subTaskTimeEstimate;
    private int subTaskActualTime;
    private Status subTaskStatus;
    private int employeeId;

    private int taskId;
    //Tildeler en employee til subTask objekt i Dashboard controlleren
    private Employee responsibleEmployee;

    public SubTask(int subTaskId, String subTaskName, String subTaskDetails, LocalDate subTaskStartDate, LocalDate subTaskDeadLine, int subTaskTimeEstimate, int subTaskActualTime, Status subTaskStatus, int taskId) {
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

    public SubTask() {
    }

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

    public Employee getResponsibleEmployee () {
        return responsibleEmployee;
    }

    public void setResponsibleEmployee (Employee responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }

}

