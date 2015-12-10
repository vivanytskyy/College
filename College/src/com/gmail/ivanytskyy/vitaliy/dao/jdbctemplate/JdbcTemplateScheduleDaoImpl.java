package com.gmail.ivanytskyy.vitaliy.dao.jdbctemplate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleDao;
import com.gmail.ivanytskyy.vitaliy.domain.Schedule;
/*
 * Task #2/2015/12/08 (web project #2)
 * JdbcTemplateScheduleDaoImpl class
 * @version 1.01 2015.12.10
 * @author Vitaliy Ivanytskyy
 */
public class JdbcTemplateScheduleDaoImpl implements ScheduleDao {
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateScheduleDaoImpl.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Schedule createSchedule(final Calendar scheduleDate) throws DAOException {
		log.info("Creating new schedule with scheduleDate = "
				+ scheduleDate.get(Calendar.DAY_OF_MONTH)
				+ "/" + (scheduleDate.get(Calendar.MONTH) + 1)
				+ "/" + scheduleDate.get(Calendar.YEAR));
		final String insertQuery = "INSERT INTO schedules (schedule_date) VALUES (?)";
		final Date dateSql = new Date(scheduleDate.getTimeInMillis());
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setDate(1, dateSql);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newScheduleId = keyHolder.getKey().longValue();
		log.info("Return Schedule object by calling findScheduleById method");
		return findScheduleById(newScheduleId);
	}
	@Override
	public Schedule findScheduleById(long scheduleId) throws DAOException {
		log.info("Getting schedule by scheduleId = " + scheduleId);
		String query = "SELECT * FROM schedules WHERE id = ?";
		log.info("Return Schedule object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{scheduleId},
		        new ScheduleMapper());
	}

	@Override
	public List<Schedule> findSchedulesByDate(Calendar scheduleDate) throws DAOException {
		log.info("Getting schedules by scheduleDate = "
				+ scheduleDate.get(Calendar.DAY_OF_MONTH) 
				+ "/" + (scheduleDate.get(Calendar.MONTH) + 1)
				+ "/" + scheduleDate.get(Calendar.YEAR));
		String query = "SELECT schedule_date, id FROM schedules WHERE schedule_date = ?";
		Date dateSql = new Date(scheduleDate.getTimeInMillis());
		log.info("Return List<Schedule> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{dateSql},
		        new ScheduleMapper());
	}
	@Override
	public List<Schedule> findAllSchedules() throws DAOException {
		log.info("Getting all schedules");
		String query = "SELECT * FROM schedules";
		log.info("Return List<Schedule> (all schedules) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new ScheduleMapper());
	}
	@Override
	public void updateSchedule(long scheduleId, Calendar newScheduleDate) throws DAOException {
		log.info("Updating schedule with scheduleId = " + scheduleId 
				+ " by new scheduleDate = "
				+ newScheduleDate.get(Calendar.DAY_OF_MONTH) 
				+ "/" + (newScheduleDate.get(Calendar.MONTH) + 1)
				+ "/" + newScheduleDate.get(Calendar.YEAR));
		String query = "UPDATE schedules SET schedule_date = ? WHERE id = ?";
		Date dateSql = new Date(newScheduleDate.getTimeInMillis());
		log.info("Update schedule by jdbcTemplate.update");		
		this.jdbcTemplate.update(query, dateSql, scheduleId);
	}
	@Override
	public void deleteScheduleById(long scheduleId) throws DAOException {
		log.info("Delete schedule by scheduleId = " + scheduleId);
		String query = "DELETE FROM schedules WHERE id = ?";
		log.info("Delete schedule by jdbcTemplate.update");
		this.jdbcTemplate.update(query, scheduleId);
	}
	@Override
	public void deleteAllSchedules() throws DAOException {
		log.info("Delete all schedules");
		String query = "DELETE FROM schedules";
		log.info("Delete all schedules by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class ScheduleMapper implements RowMapper<Schedule>{
		@Override
		public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
			Calendar calendar = new GregorianCalendar();
			Schedule schedule = new Schedule();
			Date resultDateSql = rs.getDate("schedule_date");
			calendar.setTime(resultDateSql);
			schedule.setScheduleDate(calendar);
			schedule.setScheduleId(rs.getLong("id"));
			return schedule;
		}		
	}
}