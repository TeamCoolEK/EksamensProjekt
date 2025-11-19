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
}
