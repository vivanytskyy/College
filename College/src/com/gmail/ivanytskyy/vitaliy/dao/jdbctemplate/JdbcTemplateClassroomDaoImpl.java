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
import com.gmail.ivanytskyy.vitaliy.dao.ClassroomDao;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.domain.Classroom;
/*
 * Task #2/2015/12/08 (web project #2)
 * JdbcTemplateClassroomDaoImpl class
 * @version 1.01 2015.12.09
 * @author Vitaliy Ivanytskyy
 */
public class JdbcTemplateClassroomDaoImpl implements ClassroomDao {
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateClassroomDaoImpl.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Classroom createClassroom(final String classroomName) throws DAOException {
		log.info("Creating new classroom with classroomName = " + classroomName);
		final String insertQuery = "INSERT INTO classrooms (name) VALUES (?)";
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setString(1, classroomName);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newClassroomId = keyHolder.getKey().longValue();
		log.info("Return Classroom object by calling findClassroomById method");
		return findClassroomById(newClassroomId);
	}
	@Override
	public Classroom findClassroomById(long classroomId) throws DAOException {
		log.info("Getting classroom by classroomId = " + classroomId);
		String query = "SELECT * FROM classrooms WHERE id = ?";
		log.info("Return Classroom object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{classroomId},
		        new ClassroomMapper());
	}
	@Override
	public List<Classroom> findClassroomsByName(String classroomName) throws DAOException {
		log.info("Getting classrooms by classroomName = " + classroomName);
		String query = "SELECT name, id FROM classrooms WHERE name = ?";
		log.info("Return List<Classroom> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{classroomName},
		        new ClassroomMapper());
	}
	@Override
	public List<Classroom> findAllClassrooms() throws DAOException {
		log.info("Getting all classrooms");
		String query = "SELECT * FROM classrooms";
		log.info("Return List<Classroom> (all classrooms) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new ClassroomMapper());
	}
	@Override
	public void updateClassroom(long classroomId, String newClassroomName) throws DAOException {
		log.info("Updating classroom with classroomId = " + classroomId 
				+ " by new classroomName = " + newClassroomName);
		String query = "UPDATE classrooms SET name = ? WHERE id = ?";
		log.info("Update classroom by jdbcTemplate.update");
		this.jdbcTemplate.update(query, newClassroomName, classroomId);
	}
	@Override
	public void deleteClassroomById(long classroomId) throws DAOException {
		log.info("Delete classroom by classroomId = " + classroomId);
		String query = "DELETE FROM classrooms WHERE id = ?";
		log.info("Delete classroom by jdbcTemplate.update");
		this.jdbcTemplate.update(query, classroomId);
	}
	@Override
	public void deleteAllClassrooms() throws DAOException {
		log.info("Delete all classrooms");
		String query = "DELETE FROM classrooms";
		log.info("Delete all classrooms by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class ClassroomMapper implements RowMapper<Classroom>{
		@Override
		public Classroom mapRow(ResultSet rs, int rowNum) throws SQLException {
			Classroom classroom = new Classroom();
			classroom.setClassroomName(rs.getString("name"));
			classroom.setClassroomId(rs.getLong("id"));
			return classroom;
		}		
	}
}