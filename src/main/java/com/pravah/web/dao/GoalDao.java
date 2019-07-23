package com.pravah.web.dao;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.pravah.web.entity.Goal;
import com.pravah.web.entity.mapper.GoalMapper;
import org.jdbi.v3.core.extension.NoSuchExtensionException;
import org.jdbi.v3.spring4.JdbiFactoryBean;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalDao {
	@Autowired JdbiFactoryBean jdbiFactoryBean;
	private GoalJdbiDao getGoalJdbiDao() throws  Exception {
		return	jdbiFactoryBean.getObject().onDemand(GoalJdbiDao.class);	
	}
	public void addorUpdateGoal(List<Goal> goals, Integer meetingId) throws  Exception {

				List<Integer> meetingIds=  goals.stream().filter(goal->Objects.nonNull(goal.getMeetingId())).map(Goal::getMeetingId).collect(Collectors.toList());
				if(!meetingIds.contains(meetingId)){
					meetingIds.add(meetingId);
				}
				getGoalJdbiDao().deleteGoalsByMeetingId(meetingIds);
				getGoalJdbiDao().addGoals(goals);

	}
	
	public List<Goal> getGoalByMeetingId(int id) throws  Exception{
		return getGoalJdbiDao().getGoalByMeetingId(id);
	}
	
	
	interface GoalJdbiDao{
		
		@SqlBatch("insert  into goals(user_id, user_name, project_name, date, meeting_id) "
				+ "values (:getUserId,:getUserName,:getProjectName,:getDate,:getMeetingId)")
		public int[] addGoals(@BindMethods List<Goal> goals);
		
		@SqlBatch("insert  into goals(id, user_id, user_name, project_name, date, meeting_id) "
				+ "values (:getId,:getUserId,:getUserName,:getProjectName,:getDate,:getMeetingId) "
				+ "on conflict(id) do update set "
				+ "user_id=EXCLUDED.user_id,"
				+ "user_name=EXCLUDED.user_name,"
				+ "project_name=EXCLUDED.project_name,"
				+ "date=EXCLUDED.date,"
				+ "meeting_id=EXCLUDED.meeting_id")
		public int[] updateGoals(@BindMethods List<Goal> goals);
		
		@SqlQuery("select * from goals where meeting_id=:id")
		@RegisterRowMapper(GoalMapper.class)
		public List<Goal> getGoalByMeetingId(@Bind("id")int id);


		@SqlBatch("delete from goals where meeting_id = :ids")
		public int[] deleteGoalsByMeetingId(@Bind("ids") List<Integer> id);
		
		
	}

}
