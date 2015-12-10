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
import com.gmail.ivanytskyy.vitaliy.dao.LecturerDao;
import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
/*
 * Task #2/2015/12/08 (web project #2)
 * JdbcTemplateLecturerDaoImpl class
 * @version 1.01 2015.12.09
 * @author Vitaliy Ivanytskyy
 */
public class JdbcTemplateLecturerDaoImpl implements LecturerDao {
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateLecturerDaoImpl.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Lecturer createLecturer(final String lecturerName) throws DAOException {
		log.info("Creating new lecturer with lecturerName = " + lecturerName);
		final String insertQuery = "INSERT INTO lecturers (name) VALUES (?)";
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setString(1, lecturerName);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newLecturerId = keyHolder.getKey().longValue();
		log.info("Return Lecturer object by calling findLecturerById method");
		return findLecturerById(newLecturerId);
	}
	@Override
	public Lecturer findLecturerById(long lecturerId) throws DAOException {
		log.info("Getting lecturer by lecturerId = " + lecturerId);
		String query = "SELECT * FROM lecturers WHERE id = ?";
		log.info("Return Lecturer object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{lecturerId},
		        new LecturerMapper());
	}
	@Override
	public List<Lecturer> findLecturersByName(String lecturerName) throws DAOException {
		log.info("Getting lecturers by lecturerName = " + lecturerName);
		String sql = "SELECT name, id FROM lecturers WHERE name = ?";
		log.info("Return List<Lecturer> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				sql,
				new Object[]{lecturerName},
		        new LecturerMapper());
	}
	@Override
	public List<Lecturer> findAllLecturers() throws DAOException {
		log.info("Getting all lecturers");
		String query = "SELECT * FROM lecturers";
		log.info("Return List<Lecturer> (all lecturers) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new LecturerMapper());
	}
	@Override
	public void updateLecturer(long lecturerId, String newLecturerName)	throws DAOException {
		log.info("Updating lecturer with lecturerId = " + lecturerId 
				+ " by new lecturerName = " + newLecturerName);
		String query = "UPDATE lecturers SET name = ? WHERE id = ?";
		log.info("Update lecturer by jdbcTemplate.update");
		this.jdbcTemplate.update(query, newLecturerName, lecturerId);
	}
	@Override
	public void deleteLecturerById(long lecturerId) throws DAOException {
		log.info("Delete lecturer by lecturerId = " + lecturerId);
		String query = "DELETE FROM lecturers WHERE id = ?";
		log.info("Delete lecturer by jdbcTemplate.update");
		this.jdbcTemplate.update(query, lecturerId);
	}
	@Override
	public void deleteAllLecturers() throws DAOException {
		log.info("Delete all lecturers");
		String query = "DELETE FROM lecturers";
		log.info("Delete all lecturers by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class LecturerMapper implements RowMapper<Lecturer>{
		@Override
		public Lecturer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Lecturer lecturer = new Lecturer();
			lecturer.setLecturerName(rs.getString("name"));
			lecturer.setLecturerId(rs.getLong("id"));
			return lecturer;
		}		
	}
}