--h2 tabeller--
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS subProject;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS subTask;

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

CREATE TABLE task (
                    taskId INT AUTO_INCREMENT PRIMARY KEY,
                    taskName VARCHAR(50),
                    taskDetails VARCHAR(100),
                    taskStartDate DATE,
                    taskDeadline DATE,
                    taskTimeEstimate INT,
                    taskActualTime INT,
                    taskStatus VARCHAR(20),
                    subProjectId INT NOT NULL,
                    FOREIGN KEY (subProjectId) REFERENCES subProject(subProjectId)
);

CREATE TABLE subTask (
                    subTaskId INT AUTO_INCREMENT PRIMARY KEY,
                    subTaskName VARCHAR(50),
                    subTaskDetails VARCHAR(100),
                    subTaskStartDate DATE,
                    subTaskDeadline DATE,
                    subTaskTimeEstimate INT,
                    subTaskActualTime INT,
                    subTaskStatus VARCHAR(20),
                    taskId INT NOT NULL,
                    FOREIGN KEY (taskId) REFERENCES task(taskId),
                    employeeId INT NOT NULL,
                    FOREIGN KEY (employeeId) REFERENCES employee(employeeId)
);

--init data--
INSERT INTO employee (employeeId, firstName, lastName, employeeRole, email, employeePassword)
VALUES (2, 'Sebastian', 'Munt', 'Manager', 'se@mu.dk', '1234');