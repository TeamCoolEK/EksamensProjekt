package org.example.coolplanner.model;

import java.time.LocalDate;

public class SubProject {
    private int subProjectId;
    private String subProjectName;
    private String subProjectDetails;
    private LocalDate subProjectStartDate;
    private LocalDate subProjectDeadLine;
    private int subProjectTimeEstimate;
    private int subProjectActualTime;
    private Status status;

    public SubProject(int subProjectId, String subProjectName, String subProjectDetails, LocalDate subProjectDeadLine, LocalDate subProjectStartDate, int subProjectTimeEstimate, int subProjectActualTime, Status status) {
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

    public LocalDate getSubProjectStartDate() {
        return subProjectStartDate;
    }

    public void setSubProjectStartDate(LocalDate subProjectStartDate) {
        this.subProjectStartDate = subProjectStartDate;
    }

    public LocalDate getSubProjectDeadLine() {
        return subProjectDeadLine;
    }

    public void setSubProjectDeadLine(LocalDate subProjectDeadLine) {
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
