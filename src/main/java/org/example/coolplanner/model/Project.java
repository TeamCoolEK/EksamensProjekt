package org.example.coolplanner.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Project {
    private int projectId;
    private String projectName;
    private String projectDetails;
    private LocalDate projectStartDate;
    private LocalDate projectDeadLine;
    private int projectTimeEstimate;
    private int projectActualTime;
    private Status status;


    public Project (int projectId, String projectName, String projectDetails, LocalDate projectStartDate, LocalDate projectDeadLine, int projectTimeEstimate, int projectActualTime, Status status) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDetails = projectDetails;
        this.projectStartDate = projectStartDate;
        this.projectDeadLine = projectDeadLine;
        this.projectTimeEstimate = projectTimeEstimate;
        this.projectActualTime = projectActualTime;
        this.status = status;
    }

    public Project () {} //tom konstruktør

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(String projectDetails) {
        this.projectDetails = projectDetails;
    }

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public LocalDate getProjectDeadLine() {
        return projectDeadLine;
    }

    public void setProjectDeadLine(LocalDate projectDeadLine) {
        this.projectDeadLine = projectDeadLine;
    }

    public int getProjectTimeEstimate() {
        return projectTimeEstimate;
    }

    public void setProjectTimeEstimate(int projectTimeEstimate) {
        this.projectTimeEstimate = projectTimeEstimate;
    }

    public int getProjectActualTime() {
        return projectActualTime;
    }

    public void setProjectActualTime(int projectActualTime) {
        this.projectActualTime = projectActualTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
