package org.example.coolplanner.model;

import java.util.Date;

public class Project {
    private int projectId;
    private String projectName;
    private String projectDetails;
    private Date projectStartDate;
    private Date projectDeadLine;
    private int projectTimeEstimate;
    private int projectActualTime;

    public Project (int projectId, String projectName, String projectDetails, Date projectStartDate, Date projectDeadLine, int projectTimeEstimate, int projectActualTime) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDetails = projectDetails;
        this.projectStartDate = projectStartDate;
        this.projectDeadLine = projectDeadLine;
        this.projectTimeEstimate = projectTimeEstimate;
        this.projectActualTime = projectActualTime;
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

    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public Date getProjectDeadLine() {
        return projectDeadLine;
    }

    public void setProjectDeadLine(Date projectDeadLine) {
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
}
