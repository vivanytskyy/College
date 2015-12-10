package com.gmail.ivanytskyy.vitaliy.dao.jdbctemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.LessonIntervalDao;
import com.gmail.ivanytskyy.vitaliy.domain.LessonInterval;
/*
 * Task #2/2015/12/08 (web project #2)
 * JdbcTemplateLessonIntervalDaoImpl class
 * @version 1.01 2015.12.09
 * @author Vitaliy Ivanytskyy
 */
public class JdbcTemplateLessonIntervalDaoImpl implements LessonIntervalDao {
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateLessonIntervalDaoImpl.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public LessonInterval createLessonInterval(final String lessonStart,
			final String lessonFinish) throws DAOException {
		log.info("Creating new lesson interval with start = " + lessonStart + " and finish = " + lessonFinish);
		final String insertQuery = "INSERT INTO lesson_intervals (lesson_start, lesson_finish) VALUES (?, ?)";
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setString(1, lessonStart);
		            ps.setString(2, lessonFinish);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newLessonIntervalId = keyHolder.getKey().longValue();
		log.info("Return LessonInterval object by calling findLessonIntervalById method");
		return findLessonIntervalById(newLessonIntervalId);
	}
	@Override
	public LessonInterval findLessonIntervalById(long lessonIntervalId)
			throws DAOException {
		log.info("Getting lesson interval by id = " + lessonIntervalId);
		String query = "SELECT * FROM lesson_intervals WHERE id = ?";
		log.info("Return LessonInterval object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{lessonIntervalId},
		        new LessonIntervalMapper());
	}
	@Override
	public List<LessonInterval> findLessonIntervalsByLessonStart(String lessonStart) throws DAOException {
		log.info("Getting lesson intervals by start = " + lessonStart);
		String query = "SELECT lesson_start, lesson_finish, id FROM lesson_intervals WHERE lesson_start = ?";
		log.info("Return List<LessonInterval> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{lessonStart},
		        new LessonIntervalMapper());
	}
	@Override
	public List<LessonInterval> findLessonIntervalsByLessonFinish(
			String lessonFinish) throws DAOException {
		log.info("Getting lesson intervals by finish = " + lessonFinish);
		String query = "SELECT lesson_start, lesson_finish, id FROM lesson_intervals WHERE lesson_finish = ?";
		log.info("Return List<LessonInterval> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{lessonFinish},
		        new LessonIntervalMapper());
	}
	@Override
	public List<LessonInterval> findAllLessonIntervals() throws DAOException {
		log.info("Getting all lesson intervals");
		String query = "SELECT * FROM lesson_intervals";
		log.info("Return List<LessonInterval> (all lessonIntervals) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new LessonIntervalMapper());
	}
	@Override
	public void updateLessonInterval(long lessonIntervalId,
			String newLessonStart, String newLessonFinish) throws DAOException {
		log.info("Updating lessonInterval with lessonIntervalId = " + lessonIntervalId);
		String query = "UPDATE lesson_intervals SET lesson_start = ?, lesson_finish = ? WHERE id = ?";
		log.info("Update lessonInterval by jdbcTemplate.update");
		this.jdbcTemplate.update(query, newLessonStart, newLessonFinish, lessonIntervalId);
	}
	@Override
	public void deleteLessonIntervalById(long lessonIntervalId)	throws DAOException {
		log.info("Delete lessonInterval by lessonIntervalId = " + lessonIntervalId);
		String query = "DELETE FROM lesson_intervals WHERE id = ?";
		log.info("Delete lessonInterval by jdbcTemplate.update");
		this.jdbcTemplate.update(query, lessonIntervalId);
	}
	@Override
	public void deleteAllLessonIntervals() throws DAOException {
		log.info("Delete all lessonIntervals");
		String query = "DELETE FROM lesson_intervals";
		log.info("Delete all lessonIntervals by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class LessonIntervalMapper implements RowMapper<LessonInterval>{
		@Override
		public LessonInterval mapRow(ResultSet rs, int rowNum) throws SQLException {
			LessonInterval lessonInterval = new LessonInterval();
			lessonInterval.setLessonStart(rs.getString("lesson_start"));
			lessonInterval.setLessonFinish(rs.getString("lesson_finish"));
			lessonInterval.setLessonIntervalId(rs.getLong("id"));
			return lessonInterval;
		}		
	}
}