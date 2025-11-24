package org.example.coolplanner.repository.Rowmapper;

import org.example.coolplanner.model.Status;
import org.example.coolplanner.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();

        task.setTaskID(rs.getInt("TaskID"));
        task.setTaskName(rs.getString("TaskName"));
        task.setTaskDetails(rs.getString("TaskDetails"));
        task.setTaskStartDate(rs.getDate("TaskStartDate").toLocalDate());
        task.setTaskDeadline(rs.getDate("TaskDeadline").toLocalDate());
        task.setTaskTimeEstimate(rs.getInt("TaskTimeEstimate"));
        task.setTaskActualTime(rs.getInt("TaskActualTime"));
        task.setTaskStatus(Status.valueOf(rs.getString("taskStatus")));
        task.setSubprojectID(rs.getInt("SubprojectID"));


        return task;


    }
}
