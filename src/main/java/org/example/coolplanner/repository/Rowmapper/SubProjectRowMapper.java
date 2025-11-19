package org.example.coolplanner.repository.Rowmapper;

import org.example.coolplanner.model.Status;
import org.example.coolplanner.model.SubProject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubProjectRowMapper implements RowMapper<SubProject> {
    @Override
    public SubProject mapRow(ResultSet rs, int rowNun) throws SQLException {
        SubProject subProject = new SubProject();

        subProject.setSubProjectId(rs.getInt("subProjectId"));
        subProject.setSubProjectName(rs.getString("subProjectName"));
        subProject.setSubProjectDetails(rs.getString("subProjectDetails"));
        subProject.setSubProjectStartDate(rs.getDate("subProjectStartDate"));
        subProject.setSubProjectDeadLine(rs.getDate("subProjectDeadline"));
        subProject.setSubProjectTimeEstimate(rs.getInt("subProjectTimeEstimate"));
        subProject.setSubProjectActualTime(rs.getInt("subProjectActualTime"));
        subProject.setStatus(Status.valueOf(rs.getString("subProjectStatus")));

        return subProject;
    }
}
