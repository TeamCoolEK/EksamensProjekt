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

        task.setTaskId(rs.getInt("taskId"));
        task.setTaskName(rs.getString("taskName"));
        task.setTaskDetails(rs.getString("taskDetails"));
        task.setTaskStartDate(rs.getDate("taskStartDate").toLocalDate());
        task.setTaskDeadLine(rs.getDate("taskDeadline").toLocalDate());
        task.setTaskTimeEstimate(rs.getInt("taskTimeEstimate"));
        task.setTaskActualTime(rs.getInt("taskActualTime"));
        task.setStatus(Status.valueOf(rs.getString("taskStatus")));

        return task;
    }
}
