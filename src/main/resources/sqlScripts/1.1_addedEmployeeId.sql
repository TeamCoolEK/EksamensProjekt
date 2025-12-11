USE coolplannerdb;

ALTER TABLE task
ADD COLUMN employeeId INT NOT NULL;

ALTER TABLE task
ADD CONSTRAINT task_employeeId
FOREIGN KEY (employeeId) REFERENCES employee(employeeId);