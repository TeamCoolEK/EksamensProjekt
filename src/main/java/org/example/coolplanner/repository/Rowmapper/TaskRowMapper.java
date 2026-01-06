package org.example.coolplanner.repository.Rowmapper;

import org.example.coolplanner.model.Status;
import org.example.coolplanner.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// TaskRowMapper bruges  til at konvertere rækker fra databasen til Task-objekter //
// Når Spring/JDBC henter data fra Task-tabellen, opretter denne Task-objekter //
public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Opretter et tomt Task-objekt //
        // Tom konstruktør bruges her fordi værdierne sættes én af gangen //
        Task task = new Task();

        // Sætter værdier fra databasen ind i Task-objektet //
        task.setTaskId(rs.getInt("taskID"));
        task.setTaskName(rs.getString("taskName"));
        task.setTaskDetails(rs.getString("taskDetails"));
        task.setTaskStartDate(rs.getDate("taskStartDate").toLocalDate());
        task.setTaskDeadline(rs.getDate("taskDeadline").toLocalDate());
        task.setTaskTimeEstimate(rs.getInt("taskTimeEstimate"));
        task.setTaskActualTime(rs.getInt("taskActualTime"));
        task.setTaskStatus(Status.valueOf(rs.getString("taskStatus")));
        task.setSubprojectID(rs.getInt("subProjectID"));

        // Returnerer det udfyldte Task-objekt til Spring/JDBC //
        return task;


    }
}
