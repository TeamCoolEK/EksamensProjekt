--h2 tabeller--
DROP TABLE IF EXISTS subTask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS subProject;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS employee;

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

INSERT INTO project (projectId, projectName, projectDetails, projectStartDate, projectDeadline, projectTimeEstimate, projectActualTime, projectStatus, employeeId)
VALUES (2, 'ProjectTest', 'Test', '2025-12-17', '2025-12-17', 5, 5, 'Ikke_startet', 2);

INSERT INTO subProject (subProjectId, subProjectName, subProjectDetails, subProjectStartDate, subProjectDeadline, subProjectTimeEstimate, subProjectActualTime, subProjectStatus, projectId)
VALUES (2, 'SubProjectTest', 'Test', '2025-12-17', '2025-12-17', 5, 5, 'Ikke_startet', 2);

INSERT INTO task (taskId, taskName, taskDetails, taskStartDate, taskDeadline, taskTimeEstimate, taskActualTime, taskStatus, subProjectId)
VALUES (2, 'TaskTest', 'Test', '2025-12-17', '2025-12-17', 5, 5, 'Ikke_startet', 2);

INSERT INTO subTask (subTaskId, subTaskName, subTaskDetails, subTaskStartDate, subTaskDeadline, subTaskTimeEstimate, subTaskActualTime, subTaskStatus, taskId, employeeId)
VALUES (2, 'SubTaskTest', 'Test', '2025-12-17', '2025-12-17', 5, 5, 'Ikke_startet', 2, 2);