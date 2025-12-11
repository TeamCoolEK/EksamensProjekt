RENAME TABLE task TO subTask;
RENAME TABLE userStory TO task;

ALTER TABLE task
    CHANGE userStoryId taskId INT AUTO_INCREMENT,
    CHANGE userStoryName taskName VARCHAR(50),
    CHANGE userStoryDetails taskDetails VARCHAR(100),
    CHANGE userStoryStartDate taskStartDate DATE,
    CHANGE userStoryDeadline taskDeadline DATE,
    CHANGE userStoryTimeEstimate taskTimeEstimate INT,
    CHANGE userStoryActualTime taskActualTime INT,
    CHANGE userStoryStatus taskStatus VARCHAR(20);

ALTER TABLE subTask
    CHANGE taskId subTaskId INT AUTO_INCREMENT,
    CHANGE taskName subTaskName VARCHAR(50),
    CHANGE taskDetails subTaskDetails VARCHAR(100),
    CHANGE taskStartDate subTaskStartDate DATE,
    CHANGE taskDeadline subTaskDeadline DATE,
    CHANGE taskTimeEstimate subTaskTimeEstimate INT,
    CHANGE taskActualTime subTaskActualTime INT,
    CHANGE taskStatus subTaskStatus VARCHAR(20);
    
ALTER TABLE subTask
DROP FOREIGN KEY subTask_ibfk_1;

ALTER TABLE subTask
CHANGE userStoryId taskId INT NOT NULL;

ALTER TABLE subTask
ADD CONSTRAINT fk_subtask_task
FOREIGN KEY (taskId)
REFERENCES task(taskId);