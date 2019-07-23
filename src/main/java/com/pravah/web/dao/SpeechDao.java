package com.pravah.web.dao;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.pravah.web.entity.Speech;
import com.pravah.web.entity.mapper.SpeechMapper;
import org.jdbi.v3.core.extension.NoSuchExtensionException;
import org.jdbi.v3.spring4.JdbiFactoryBean;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpeechDao {
	@Autowired JdbiFactoryBean jdbiFactoryBean;
	private SpeechJdbiDao getSpeechJdbiDao() throws NoSuchExtensionException, Exception {
		return	jdbiFactoryBean.getObject().onDemand(SpeechJdbiDao.class);	
	}
	
	public void addOrupdateSpeech(List<Speech> speeches, Integer meetingId) throws  Exception {
				List<Integer> meetingIds=  speeches.stream().filter(speech->Objects.nonNull(speech.getMeetingId())).map(Speech::getMeetingId).collect(Collectors.toList());
				if(!meetingIds.contains(meetingId)){
					meetingIds.add(meetingId);
				}
				getSpeechJdbiDao().deleteSpeechesByMeetingId(meetingIds);
				getSpeechJdbiDao().addSpeeches(speeches);
	}
	
	public List<Speech> getSpeechByMeetingId(int MeetingId) throws Exception{
		return getSpeechJdbiDao().getSpeechByMeetingId(MeetingId);
	}
	public List<Speech> getSpeechByName(String name) throws Exception {
		return getSpeechJdbiDao().getSpeechByUserName(name);
	}
	public List<Speech> getAllSpeeches() throws Exception {
		return getSpeechJdbiDao().getAllSpeech();
	}


	@UseStringTemplate3StatementLocator
	interface SpeechJdbiDao{
		@SqlQuery("select * from speeches where meeting_id = :id")
		@RegisterRowMapper(SpeechMapper.class)
		public List<Speech> getSpeechByMeetingId( @Bind("id")  int  id);

		@SqlQuery("select  * from speeches where evaluator_name= :name or speaker_name = :name ")
		@RegisterRowMapper(SpeechMapper.class)
		public List<Speech> getSpeechByUserName(@Bind("name") String name);


		@SqlQuery("select  * from speeches")
		@RegisterRowMapper(SpeechMapper.class)
		public List<Speech> getAllSpeech();



		@SqlBatch("insert into speeches (id, meeting_id, project_name, speaker_name, speaker_id, evaluator_name, evaluator_id, time_min, time_max, date) " +
				"values("
				+ ":getId,"
				+ ":getMeetingId,"
				+ ":getProjectName,"
				+ ":getSpeakerName,"
				+ ":getSpeakerId,"
				+ ":getEvaluatorName,"
				+ ":getEvaluatorId,"
				+ ":getTimeMin,"
				+ ":getTimeMax,"
				+ ":getDate)"
				+ " on conflict (id) "
				+ " do update set "
				+ " meeting_id=EXCLUDED.meeting_id,"
				+ " project_name=EXCLUDED.project_name,"
				+ " speaker_name=EXCLUDED.speaker_name,"
				+ " speaker_id=EXCLUDED.speaker_id,"
				+ " evaluator_name=EXCLUDED.evaluator_name,"
				+ " evaluator_id=EXCLUDED.evaluator_id,"
				+ " date=EXCLUDED.date,"
				+ " time_min=EXCLUDED.time_min,"
				+ " time_max=EXCLUDED.time_max"
				)
		public int[] updateSpeeches(@BindMethods List<Speech> speeches);
		

		@SqlBatch("insert into speeches (meeting_id, project_name, speaker_name, speaker_id, evaluator_name, evaluator_id, date, time_min, time_max) " + 
				"values("
				+ ":getMeetingId,"
				+ ":getProjectName,"
				+ ":getSpeakerName,"
				+ ":getSpeakerId,"
				+ ":getEvaluatorName,"
				+ ":getEvaluatorId,"
				+ ":getDate,"
				+ ":getTimeMin,"
				+ ":getTimeMax)")
		public int[] addSpeeches(@BindMethods List<Speech> speeches);

		@SqlBatch("delete from speeches where meeting_id = :ids")
		public int[] deleteSpeechesByMeetingId(@Bind("ids") List<Integer> id);
		
		
		
		
	}
}
