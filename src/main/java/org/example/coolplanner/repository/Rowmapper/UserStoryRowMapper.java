package org.example.coolplanner.repository.Rowmapper;

import org.example.coolplanner.model.UserStory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStoryRowMapper implements RowMapper<UserStory> {

    @Override
    public UserStory mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserStory userStory = new UserStory();

        userStory.setUserStoryID(rs.getInt("UserStoryID"));
        userStory.setUserStoryName(rs.getString("UserStoryName"));
        userStory.setUserStoryDetails(rs.getString("UserStoryDetails"));
        userStory.setUserStoryStartDate(rs.getDate("UserStoryStartDate").toLocalDate());
        userStory.setUserStoryDeadline(rs.getDate("UserStoryDeadline").toLocalDate());
        userStory.setUserStoryTimeEstimate(rs.getInt("UserStoryTimeEstimate"));
        userStory.setUserStoryActualTime(rs.getInt("UserStoryActualTime"));
        userStory.setUserStoryStatus(rs.getString("UserStoryStatus"));
        userStory.setSubprojectID(rs.getInt("SubprojectID"));


        return userStory;


    }
}
