package org.example.coolplanner.repository.Rowmapper;

import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();

        project.setProjectId(rs.getInt("projectId"));
        project.setProjectName(rs.getString("projectName"));
        project.setProjectDetails(rs.getString("projectDetails"));
        project.setProjectStartDate(rs.getDate("projectStartDate").toLocalDate());
        project.setProjectDeadLine(rs.getDate("projectDeadline").toLocalDate());
        project.setProjectTimeEstimate(rs.getInt("projectTimeEstimate"));
        project.setProjectActualTime(rs.getInt("projectActualTime"));
        project.setStatus(Status.valueOf(rs.getString("projectStatus")));

        return project;
    }
}
