package com.pravah.web.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.pravah.web.entity.User;

public class UserMapper implements RowMapper<User>{

	@Override
	public User map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new User(rs.getString("id"),rs.getString("name"),rs.getBoolean("is_active"),
				rs.getString("email"));
		
	}

}
