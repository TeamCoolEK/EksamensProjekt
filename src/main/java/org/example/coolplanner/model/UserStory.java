package org.example.coolplanner.model;

import java.time.LocalDate;

public class UserStory {

    private int userStoryID;
    private String userStoryName;
    private String userStoryDetails;
    private LocalDate userStoryStartDate;
    private LocalDate userStoryDeadline;
    private int userStoryTimeEstimate;
    private int userStoryActualTime;
    private String userStoryStatus;
    private int subprojectID;

    public int getUserStoryID() {
        return userStoryID;
    }

    public void setUserStoryID(int userStoryID) {
        this.userStoryID = userStoryID;
    }

    public String getUserStoryName() {
        return userStoryName;
    }

    public void setUserStoryName(String userStoryName) {
        this.userStoryName = userStoryName;
    }

    public String getUserStoryDetails() {
        return userStoryDetails;
    }

    public void setUserStoryDetails(String userStoryDetails) {
        this.userStoryDetails = userStoryDetails;
    }

    public LocalDate getUserStoryStartDate() {
        return userStoryStartDate;
    }

    public void setUserStoryStartDate(LocalDate userStoryStartDate) {
        this.userStoryStartDate = userStoryStartDate;
    }

    public LocalDate getUserStoryDeadline() {
        return userStoryDeadline;
    }

    public void setUserStoryDeadline(LocalDate userStoryDeadline) {
        this.userStoryDeadline = userStoryDeadline;
    }

    public int getUserStoryTimeEstimate() {
        return userStoryTimeEstimate;
    }

    public void setUserStoryTimeEstimate(int userStoryTimeEstimate) {
        this.userStoryTimeEstimate = userStoryTimeEstimate;
    }

    public int getUserStoryActualTime() {
        return userStoryActualTime;
    }

    public void setUserStoryActualTime(int userStoryActualTime) {
        this.userStoryActualTime = userStoryActualTime;
    }

    public String getUserStoryStatus() {
        return userStoryStatus;
    }

    public void setUserStoryStatus(String userStoryStatus) {
        this.userStoryStatus = userStoryStatus;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }
}
