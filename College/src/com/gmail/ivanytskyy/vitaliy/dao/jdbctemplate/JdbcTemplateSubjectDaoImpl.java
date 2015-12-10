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
import com.gmail.ivanytskyy.vitaliy.dao.SubjectDao;
import com.gmail.ivanytskyy.vitaliy.domain.Subject;
/*
 * Task #2/2015/12/08 (web project #2)
 * JdbcTemplateSubjectDaoImpl class
 * @version 1.01 2015.12.09
 * @author Vitaliy Ivanytskyy
 */
public class JdbcTemplateSubjectDaoImpl implements SubjectDao {
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateSubjectDaoImpl.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Subject createSubject(final String subjectName) throws DAOException {
		log.info("Creating new subject with subjectName = " + subjectName);
		final String insertQuery = "INSERT INTO subjects (name) VALUES (?)";
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setString(1, subjectName);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newSubjectId = keyHolder.getKey().longValue();
		log.info("Return subject by calling findSubjectById method");
		return findSubjectById(newSubjectId);
	}
	@Override
	public Subject findSubjectById(long subjectId) throws DAOException {
		log.info("Getting subject by subjectId = " + subjectId);
		String query = "SELECT * FROM subjects WHERE id = ?";
		log.info("Return subject by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{subjectId},
		        new SubjectMapper());
	}
	@Override
	public List<Subject> findSubjectsByName(String subjectName)	throws DAOException {
		log.info("Getting subjects by subjectName = " + subjectName);
		String sql = "SELECT name, id FROM subjects WHERE name = ?";
		log.info("Return List<Subject> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				sql,
				new Object[]{subjectName},
		        new SubjectMapper());
	}
	@Override
	public List<Subject> findAllSubjects() throws DAOException {
		log.info("Getting all subjects");
		String query = "SELECT * FROM subjects";
		log.info("Return List<Subject> (all subjects) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new SubjectMapper());
	}
	@Override
	public void updateSubject(long subjectId, String newSubjectName) throws DAOException {
		log.info("Updating subject with subjectId = " + subjectId 
				+ " by new subjectName = " + newSubjectName);
		String query = "UPDATE subjects SET name = ? WHERE id = ?";
		log.info("Update subject by jdbcTemplate.update");
		this.jdbcTemplate.update(query, newSubjectName, subjectId);
	}
	@Override
	public void deleteSubjectById(long subjectId) throws DAOException {
		log.info("Delete subject by subjectId = " + subjectId);
		String query = "DELETE FROM subjects WHERE id = ?";
		log.info("Delete subject by jdbcTemplate.update");
		this.jdbcTemplate.update(query, subjectId);
	}
	@Override
	public void deleteAllSubjects() throws DAOException {
		log.info("Delete all subjects");
		String query = "DELETE FROM subjects";
		log.info("Delete all subjects by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class SubjectMapper implements RowMapper<Subject>{
		@Override
		public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
			Subject subject = new Subject();
			subject.setSubjectName(rs.getString("name"));
			subject.setSubjectId(rs.getLong("id"));
			return subject;
		}		
	}
}