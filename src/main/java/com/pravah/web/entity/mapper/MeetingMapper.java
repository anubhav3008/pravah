package com.pravah.web.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.pravah.web.entity.Meeting;

public class MeetingMapper  implements RowMapper<Meeting>{

	@Override
	public Meeting map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new Meeting(rs.getInt("id"),
				rs.getString("time"),
				rs.getDate("date"),
				rs.getString("ttm_name"), 
				rs.getString("ttm_Id"), 
				rs.getString("grammarian_name"), 
				rs.getString("grammarian_id"), 
				rs.getString("ah_counter_name"), 
				rs.getString("ah_counter_id"), 
				rs.getString("tmod_name"),
				rs.getString("tmod_id"), 
				rs.getString("timer_name"), 
				rs.getString("timer_id"), 
				rs.getString("ge_name"), 
				rs.getString("ge_id"), 
				rs.getString("theme"), 
				rs.getString("venue"),
				rs.getString("clubname"),
				rs.getString("saa_id"),
				rs.getString("saa_name"),
				rs.getString("club_id"),
				rs.getString("president_id"),
				rs.getString("president_name"),
				rs.getString("vpe_id"),
				rs.getString("vpe_name")
				);
	}

}
