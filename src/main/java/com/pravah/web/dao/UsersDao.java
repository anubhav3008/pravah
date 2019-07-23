package com.pravah.web.dao;

import java.util.List;

import com.pravah.web.entity.User;
import com.pravah.web.entity.mapper.UserMapper;
import org.jdbi.v3.core.extension.NoSuchExtensionException;
import org.jdbi.v3.spring4.JdbiFactoryBean;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsersDao{
	@Autowired JdbiFactoryBean jdbiFactoryBean;
	private UsersJDBIDao getUsersJDBIDao() throws NoSuchExtensionException, Exception {
		return	jdbiFactoryBean.getObject().onDemand(UsersJDBIDao.class);	
	}
	 public List<User> getAllUsers() throws NoSuchExtensionException, Exception{
		 UsersJDBIDao usersJDBIDao =  getUsersJDBIDao();
		 return usersJDBIDao.getAllUsers();
	 }
	 public int addUser(User user) throws NoSuchExtensionException, Exception {
		 UsersJDBIDao usersJDBIDao =  getUsersJDBIDao();
		 return usersJDBIDao.addUser(user);
	 }
	 
	 
	
	interface  UsersJDBIDao {
		@SqlQuery("SELECT * FROM users")
	    @RegisterRowMapper(UserMapper.class)
	    public List<User> getAllUsers();
		@SqlUpdate("insert into users(id,name,is_active) values(:getId,:getName,:getIsActive)")
		public int addUser(@BindMethods User user);
	}
	
	
}

 