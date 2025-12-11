package org.example.coolplanner.repository.Rowmapper;

import org.example.coolplanner.model.Status;
import org.example.coolplanner.model.SubTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubTaskRowMapper implements RowMapper<SubTask> {
    @Override
    public SubTask mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubTask subTask = new SubTask();

        subTask.setSubTaskId(rs.getInt("subTaskId"));
        subTask.setSubTaskName(rs.getString("subTaskName"));
        subTask.setSubTaskDetails(rs.getString("subTaskDetails"));
        subTask.setSubTaskStartDate(rs.getDate("subTaskStartDate").toLocalDate());
        subTask.setSubTaskDeadLine(rs.getDate("subTaskDeadline").toLocalDate());
        subTask.setSubTaskTimeEstimate(rs.getInt("subTaskTimeEstimate"));
        subTask.setSubTaskActualTime(rs.getInt("subTaskActualTime"));
        subTask.setSubTaskStatus(Status.valueOf(rs.getString("subTaskStatus")));
        subTask.setTaskId(rs.getInt("taskId"));
        subTask.setEmployeeId(rs.getInt("employeeId"));

        return subTask;
    }
}
