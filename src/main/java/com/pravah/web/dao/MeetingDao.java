package com.pravah.web.dao;

import java.util.List;

import com.pravah.web.entity.Meeting;
import com.pravah.web.entity.mapper.MeetingMapper;
import org.jdbi.v3.core.extension.NoSuchExtensionException;
import org.jdbi.v3.spring4.JdbiFactoryBean;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingDao {
	@Autowired JdbiFactoryBean jdbiFactoryBean;
	private MeetingJdbiDao getMeetingJdbiDao() throws NoSuchExtensionException, Exception {
		return	jdbiFactoryBean.getObject().onDemand(MeetingJdbiDao.class);	
	}

	public int addOrUpdateMeeting(Meeting meeting) throws NoSuchExtensionException, Exception {
		MeetingJdbiDao meetingJdbiDao =  getMeetingJdbiDao();
		return meetingJdbiDao.addOrUpdateMeeting(meeting);
	}
	public List<Meeting> getAllMeetings() throws NoSuchExtensionException, Exception{
		MeetingJdbiDao meetingJdbiDao =  getMeetingJdbiDao();
		return meetingJdbiDao.getAllMeetings();
	}
	public Meeting getMeeting(int id) throws NoSuchExtensionException, Exception{
		MeetingJdbiDao meetingJdbiDao =  getMeetingJdbiDao();
		return meetingJdbiDao.getMeeting(id);
	}
	public List<Meeting> getMeetings(String name) throws Exception {
		MeetingJdbiDao meetingJdbiDao =  getMeetingJdbiDao();
		return meetingJdbiDao.getMeetings(name);
	}
	
	interface MeetingJdbiDao{

		@SqlQuery("select * from meetings order by id desc")
		@RegisterRowMapper(MeetingMapper.class)
		public List<Meeting> getAllMeetings();


		@SqlQuery("select * from meetings where" +
				" grammarian_name= :name or" +
				" ah_counter_name = :name or" +
				" ge_name= :name or" +
				" tmod_name= :name or" +
				" timer_name= :name or" +
				" ttm_name = :name")
		@RegisterRowMapper(MeetingMapper.class)
		public List<Meeting> getMeetings(@Bind("name")  String name);


		@SqlQuery("select * from meetings where id= :id")
		@RegisterRowMapper(MeetingMapper.class)
		public Meeting getMeeting(@Bind("id") int id);

		@SqlUpdate("insert into meetings (id,time,ttm_name,ttm_id,grammarian_name,grammarian_id,ah_counter_name,ah_counter_id,tmod_name,tmod_id,timer_name,timer_id,ge_name,ge_id,theme,venue,date)" +
				"values ("
				+ ":getId,"
				+ ":getTime,"
				+ ":getTtmName,"
				+ ":getTtmId,"
				+ ":getGrammarianName,"
				+ ":getGrammarianId,"
				+ ":getAhCounterName,"
				+ ":getAhCounterId,"
				+ ":getTmodName,"
				+ ":getTmodId,"
				+ ":getTimerName,"
				+ ":getTimerId,"
				+ ":getGeName,"
				+ ":getGeId,"
				+ ":getTheme,"
				+ ":getVenue,"
				+ ":getDate)" +
				"on CONFLICT(id) do update set "
				+ "ttm_name=EXCLUDED.ttm_name, "
				+ "ttm_id=EXCLUDED.ttm_id,"
				+ "grammarian_name=EXCLUDED.grammarian_name,"
				+ "grammarian_id=EXCLUDED.grammarian_id,"
				+ "ah_counter_name=EXCLUDED.ah_counter_name,"
				+ "ah_counter_id=EXCLUDED.ah_counter_id,"
				+ "tmod_name=EXCLUDED.tmod_name,"
				+ "tmod_id=EXCLUDED.tmod_id,"
				+ "timer_name=EXCLUDED.timer_name,"  
				+ "timer_id=EXCLUDED.timer_id,"
				+ "ge_name=EXCLUDED.ge_name,"
				+ "ge_id=EXCLUDED.ge_id,"
				+ "theme=EXCLUDED.theme,"
				+ "venue=EXCLUDED.venue, "
				+ "date=EXCLUDED.date, "
				+ "time=EXCLUDED.time;")
		public int addOrUpdateMeeting(@BindMethods Meeting meeting);
	}


}
