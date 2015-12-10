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
import com.gmail.ivanytskyy.vitaliy.dao.StudentDao;
import com.gmail.ivanytskyy.vitaliy.domain.Student;
/*
 * Task #2/2015/12/08 (web project #2)
 * JdbcTemplateStudentDaoImpl class
 * @version 1.01 2015.12.10
 * @author Vitaliy Ivanytskyy
 */
public class JdbcTemplateStudentDaoImpl implements StudentDao {
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateStudentDaoImpl.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Student createStudent(final String studentName, final long groupId) throws DAOException {
		log.info("Creating new student with studentName = " + studentName 
				+ " and groupId = " + groupId);
		final String insertQuery = "INSERT INTO students (name, group_id) VALUES (?, ?)";
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setString(1, studentName);
		            ps.setLong(2, groupId);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newStudentId = keyHolder.getKey().longValue();
		log.info("Return Student object by calling findStudentById method");
		return findStudentById(newStudentId);
	}

	@Override
	public Student findStudentById(long studentId) throws DAOException {
		log.info("Getting student by studentId = " + studentId);
		String query = "SELECT * FROM students WHERE id = ?";
		log.info("Return Student object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{studentId},
		        new StudentMapper());
	}
	@Override
	public List<Student> findStudentsByGroupId(long groupId) throws DAOException {
		log.info("Getting students by groupId = " + groupId);
		String query = "SELECT id, name, group_id FROM students WHERE group_id = ?";
		log.info("Return List<Student> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{groupId},
		        new StudentMapper());
	}
	@Override
	public List<Student> findStudentsByName(String studentName)	throws DAOException {
		log.info("Getting students by studentName = " + studentName);
		String query = "SELECT id, name, group_id FROM students WHERE name = ?";
		log.info("Return List<Student> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{studentName},
		        new StudentMapper());
	}
	@Override
	public List<Student> findAllStudents() throws DAOException {
		log.info("Getting all students");
		String query = "SELECT * FROM students";
		log.info("Return List<Student> (all students) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new StudentMapper());
	}
	@Override
	public void moveStudentToAnotherGroup(long studentId, long groupId)	throws DAOException {
		log.info("Moving student with studentId = " + studentId 
				+ " to group with groupId = " + groupId);
		String query = "UPDATE students SET group_id = ? WHERE id = ?";
		log.info("Move student by jdbcTemplate.update");
		this.jdbcTemplate.update(query, groupId, studentId);
	}
	@Override
	public void updateStudent(long studentId, String newStudentName) throws DAOException {
		log.info("Updating student with studentId = " + studentId 
				+ " by new studentName = " + newStudentName);
		String query = "UPDATE students SET name = ? WHERE id = ?";
		log.info("Update student by jdbcTemplate.update");
		this.jdbcTemplate.update(query, newStudentName, studentId);
	}
	@Override
	public void deleteStudentById(long studentId) throws DAOException {
		log.info("Delete student by studentId = " + studentId);
		String query = "DELETE FROM students WHERE id = ?";
		log.info("Delete student by jdbcTemplate.update");
		this.jdbcTemplate.update(query, studentId);
	}
	@Override
	public void deleteAllStudents() throws DAOException {
		log.info("Delete all students");
		String query = "DELETE FROM students";
		log.info("Delete all students by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class StudentMapper implements RowMapper<Student>{
		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setStudentId(rs.getLong("id"));
			student.setStudentName(rs.getString("name"));
			student.setGroupId(rs.getLong("group_id"));
			return student;
		}		
	}
}