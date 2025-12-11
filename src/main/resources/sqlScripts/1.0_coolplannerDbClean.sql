CREATE database coolplannerdb;
USE coolplannerdb;
CREATE TABLE employee (
	employeeId INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    employeeRole VARCHAR(20),
    email VARCHAR(50),
    employeePassword VARCHAR(16)
);
CREATE TABLE project (
	projectId INT AUTO_INCREMENT PRIMARY KEY,
    projectName VARCHAR(50),
    projectDetails VARCHAR(100),
    projectStartDate DATE,
    projectDeadline DATE,
    projectTimeEstimate INT,
    projectActualTime INT,
    projectStatus VARCHAR(20),
    employeeId INT NOT NULL,
    FOREIGN KEY (employeeId) REFERENCES employee(employeeId)
);
CREATE TABLE subProject (
	subProjectId INT AUTO_INCREMENT PRIMARY KEY,
    subProjectName VARCHAR(50),
    subProjectDetails VARCHAR(100),
    subProjectStartDate DATE,
    subProjectDeadline DATE,
    subProjectTimeEstimate INT,
    subProjectActualTime INT,
    subProjectStatus VARCHAR(20),
    projectId INT NOT NULL,
    FOREIGN KEY (projectId) REFERENCES project(projectId)
);
CREATE TABLE userStory (
    userStoryId INT AUTO_INCREMENT PRIMARY KEY,
    userStoryName VARCHAR(50),
    userStoryDetails VARCHAR(100),
    userStoryStartDate DATE,
    userStoryDeadline DATE,
    userStoryTimeEstimate INT,
    userStoryActualTime INT,
    userStoryStatus VARCHAR(20),
    subProjectId INT NOT NULL,
    FOREIGN KEY (subProjectId) REFERENCES subProject(subProjectId)
);
CREATE TABLE task (
    taskId INT AUTO_INCREMENT PRIMARY KEY,
    taskName VARCHAR(50),
    taskDetails VARCHAR(100),
    taskStartDate DATE,
    taskDeadline DATE,
    taskTimeEstimate INT,
    taskActualTime INT,
    taskStatus VARCHAR(20),
    userStoryId INT NOT NULL,
    FOREIGN KEY (userStoryId) REFERENCES userStory(userStoryId)
);