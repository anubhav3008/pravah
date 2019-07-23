package com.pravah.web.entity.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.pravah.web.entity.Speech;

public class SpeechMapper implements RowMapper<Speech> {

	@Override
	public Speech map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new Speech(rs.getInt("id"), 
				rs.getInt("meeting_id"), 
				rs.getString("project_name"), 
				rs.getString("speaker_name"), 
				rs.getString("speaker_id"),
				rs.getString("evaluator_name"), 
				rs.getString("evaluator_id"), 
				rs.getDate("date"), 
				rs.getDouble("time_min"), 
				rs.getDouble("time_max")
				);
    	}

}
