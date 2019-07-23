package com.pravah.web.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.pravah.web.entity.Goal;

public class GoalMapper implements RowMapper<Goal> {

	@Override
	public Goal map(ResultSet rs, StatementContext ctx) throws SQLException {
		
		return new Goal(rs.getInt("id"), 
				rs.getString("user_id"), 
				rs.getString("user_name"), 
				rs.getString("project_name"), 
				rs.getDate("date"),
				rs.getInt("meeting_id"));	
	}

}
