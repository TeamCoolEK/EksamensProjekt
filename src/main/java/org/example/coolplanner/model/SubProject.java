package org.example.coolplanner.model;

import java.util.Date;

public class SubProject {
    private int subProjectId;
    private String subProjectName;
    private String subProjectDetails;
    private Date subProjectStartDate;
    private Date subProjectDeadLine;
    private int subProjectTimeEstimate;
    private int subProjectActualTime;
    private Status status;

    public SubProject(int subProjectId, String subProjectName, String subProjectDetails, Date subProjectDeadLine, Date subProjectStartDate, int subProjectTimeEstimate, int subProjectActualTime, Status status) {
        this.subProjectId = subProjectId;
        this.subProjectName = subProjectName;
        this.subProjectDetails = subProjectDetails;
        this.subProjectDeadLine = subProjectDeadLine;
        this.subProjectStartDate = subProjectStartDate;
        this.subProjectTimeEstimate = subProjectTimeEstimate;
        this.subProjectActualTime = subProjectActualTime;
        this.status = status;
    }

    public SubProject () {} //tom konstuktør

    public int getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public String getSubProjectDetails() {
        return subProjectDetails;
    }

    public void setSubProjectDetails(String subProjectDetails) {
        this.subProjectDetails = subProjectDetails;
    }

    public Date getSubProjectStartDate() {
        return subProjectStartDate;
    }

    public void setSubProjectStartDate(Date subProjectStartDate) {
        this.subProjectStartDate = subProjectStartDate;
    }

    public Date getSubProjectDeadLine() {
        return subProjectDeadLine;
    }

    public void setSubProjectDeadLine(Date subProjectDeadLine) {
        this.subProjectDeadLine = subProjectDeadLine;
    }

    public int getSubProjectActualTime() {
        return subProjectActualTime;
    }

    public void setSubProjectActualTime(int subProjectActualTime) {
        this.subProjectActualTime = subProjectActualTime;
    }

    public int getSubProjectTimeEstimate() {
        return subProjectTimeEstimate;
    }

    public void setSubProjectTimeEstimate(int subProjectTimeEstimate) {
        this.subProjectTimeEstimate = subProjectTimeEstimate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
