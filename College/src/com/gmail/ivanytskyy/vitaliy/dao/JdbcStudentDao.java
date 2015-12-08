package com.gmail.ivanytskyy.vitaliy.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.domain.Student;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * JdbcStudentDao class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class JdbcStudentDao implements StudentDao{
	private DataSource dataSource;
	private static final Logger log = Logger.getLogger(JdbcStudentDao.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public Student createStudent(String studentName, long groupId) throws DAOException{
		log.info("Creating new student with studentName = " + studentName + " and groupId = " + groupId);
		Student student = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "INSERT INTO students (name, group_id) VALUES (?, ?)";
		try {
			log.trace("Open connection");
			try {
				connection = dataSource.getConnection();
				log.trace("Connection was opened");
			} catch (SQLException e1) {
				log.error("Cannot open connection", e1);
				throw new DAOException("Cannot open connection", e1);
			}
			try {
				log.trace("Create prepared statement");
				statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, studentName);
				statement.setLong(2, groupId);
				statement.execute();
				try {
					log.trace("Get result set");
					resultSet = statement.getGeneratedKeys();
					log.trace("Create student to return");
					while(resultSet.next()){
						student = new Student();
						student.setStudentName(resultSet.getString("name"));
						student.setGroupId(resultSet.getLong("group_id"));
						student.setStudentId(resultSet.getLong(1));
					}
					log.trace("Student with studentName = " + studentName + " and groupId = " + groupId + " was created");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot create student", e);
			throw new DAOException("Cannot create student", e);
		}finally{
			try {
				if (resultSet != null) {
					resultSet.close();
					log.trace("Result set was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close result set", e);
			}
			try {
				if (statement != null) {
					statement.close();
					log.trace("Prepared statement was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close prepared statement", e);
			}
			try {
				if (connection != null) {
					connection.close();
					log.trace("Connection was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning student");
		return student;
	}
	@Override
	public void moveStudentToAnotherGroup(long studentId, long groupId) throws DAOException{
		log.info("Moving student with studentId = " + studentId + " to group with groupId = " + groupId);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE students SET group_id = ? WHERE id = ?";
		try {
			log.trace("Open connection");
			try {
				connection = dataSource.getConnection();
				log.trace("Connection was opened");
			} catch (SQLException e1) {
				log.error("Cannot open connection", e1);
				throw new DAOException("Cannot open connection", e1);
			}
			try {
				log.trace("Create prepared statement");
				statement = connection.prepareStatement(query);
				statement.setLong(1, groupId);
				statement.setLong(2, studentId);
				statement.executeUpdate();
				log.trace("Student with studentId = " + studentId + " was moved to group with groupId = " + groupId);
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot move student", e);
			throw new DAOException("Cannot move student", e);
		}finally{
			try {
				if (statement != null) {
					statement.close();
					log.trace("Prepared statement was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close prepared statement", e);
			}
			try {
				if (connection != null) {
					connection.close();
					log.trace("Connection was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
	}
	@Override
	public Student findStudentById(long studentId) throws DAOException{
		log.info("Getting student by studentId = " + studentId);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Student student = null;
		String query = "SELECT name, group_id FROM students WHERE id = ? ";
		try {
			log.trace("Open connection");
			try {
				connection = dataSource.getConnection();
				log.trace("Connection was opened");
			} catch (SQLException e1) {
				log.error("Cannot open connection", e1);
				throw new DAOException("Cannot open connection", e1);
			}
			try {
				log.trace("Create prepared statement");
				statement = connection.prepareStatement(query);
				statement.setLong(1, studentId);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find student to return");
					while (resultSet.next()) {
						student = new Student();
						student.setStudentName(resultSet.getString("name"));
						student.setGroupId(resultSet.getLong("group_id"));
						student.setStudentId(studentId);
					}
					log.trace("Student with studentId = " + studentId + " was found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find student by studentId", e);
			throw new DAOException("Cannot find student by studentId", e);
		}finally{
			try {
				if (resultSet != null) {
					resultSet.close();
					log.trace("Result set was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close result set", e);
			}
			try {
				if (statement != null) {
					statement.close();
					log.trace("Prepared statement was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close prepared statement", e);
			}
			try {
				if (connection != null) {
					connection.close();
					log.trace("Connection was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning student");
		return student;
	}
	@Override
	public List<Student> findStudentsByGroupId(long groupId) throws DAOException{
		log.info("Getting students by groupId = " + groupId);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Student> students = new LinkedList<Student>();
		String query = "SELECT id, name, group_id FROM students WHERE group_id = ?";
		try {
			log.trace("Open connection");
			try {
				connection = dataSource.getConnection();
				log.trace("Connection was opened");
			} catch (SQLException e1) {
				log.error("Cannot open connection", e1);
				throw new DAOException("Cannot open connection", e1);
			}
			try {
				log.trace("Create prepared statement");
				statement = connection.prepareStatement(query);
				statement.setLong(1, groupId);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find students to return");
					while (resultSet.next()) {
						Student student = new Student();
						student.setStudentName(resultSet.getString("name"));
						student.setGroupId(resultSet.getLong("group_id"));						
						student.setStudentId(resultSet.getLong("id"));
						students.add(student);
					}
					log.trace("Students of group with groupId = " + groupId + " were found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find students by groupId", e);
			throw new DAOException("Cannot find students by groupId", e);
		}finally{
			try {
				if (resultSet != null) {
					resultSet.close();
					log.trace("Result set was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close result set", e);
			}
			try {
				if (statement != null) {
					statement.close();
					log.trace("Prepared statement was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close prepared statement", e);
			}
			try {
				if (connection != null) {
					connection.close();
					log.trace("Connection was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning students");
		return students;
	}
	@Override
	public List<Student> findStudentsByName(String studentName) throws DAOException{
		log.info("Getting students by studentName = " + studentName);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Student> students = new LinkedList<Student>();
		String query = "SELECT id, name, group_id FROM students WHERE name = ?";
		try {
			log.trace("Open connection");
			try {
				connection = dataSource.getConnection();
				log.trace("Connection was opened");
			} catch (SQLException e1) {
				log.error("Cannot open connection", e1);
				throw new DAOException("Cannot open connection", e1);
			}
			try {
				log.trace("Create prepared statement");
				statement = connection.prepareStatement(query);
				statement.setString(1, studentName);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find students to return");
					while (resultSet.next()) {
						Student student = new Student();
						student.setStudentName(resultSet.getString("name"));
						student.setGroupId(resultSet.getLong("group_id"));						
						student.setStudentId(resultSet.getLong("id"));
						students.add(student);
					}
					log.trace("Students with studentName = " + studentName + " were found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find students by studentName", e);
			throw new DAOException("Cannot find students by studentName", e);
		}finally{
			try {
				if (resultSet != null) {
					resultSet.close();
					log.trace("Result set was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close result set", e);
			}
			try {
				if (statement != null) {
					statement.close();
					log.trace("Prepared statement was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close prepared statement", e);
			}
			try {
				if (connection != null) {
					connection.close();
					log.trace("Connection was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning students");
		return students;
	}
	@Override
	public List<Student> findAllStudents() throws DAOException{
		log.info("Getting all students");
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Student> students = new LinkedList<Student>();
		String query = "SELECT * FROM students";
		try {
			log.trace("Open connection");
			try {
				connection = dataSource.getConnection();
				log.trace("Connection was opened");
			} catch (SQLException e1) {
				log.error("Cannot open connection", e1);
				throw new DAOException("Cannot open connection", e1);
			}
			try {
				log.trace("Create statement");
				statement = connection.createStatement();
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery(query);
					log.trace("Getting students");
					while (resultSet.next()) {						
						Student student = new Student();
						student.setStudentName(resultSet.getString("name"));
						student.setGroupId(resultSet.getLong("group_id"));						
						student.setStudentId(resultSet.getLong("id"));
						students.add(student);
					}
					log.trace("Students were gotten");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot get all students", e);
			throw new DAOException("Cannot get all students", e);
		}finally{
			try {
				if (resultSet != null) {
					resultSet.close();
					log.trace("Result set was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close result set", e);
			}
			try {
				if (statement != null) {
					statement.close();
					log.trace("Statement was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close statement", e);
			}
			try {
				if (connection != null) {
					connection.close();
					log.trace("Connection was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning all students");
		return students;
	}
	@Override
	public void updateStudent(long studentId, String newStudentName) throws DAOException{
		log.info("Updating student with studentId = " + studentId + " by new studentName = " + newStudentName);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE students SET name = ? WHERE id = ?";
		try {
			log.trace("Open connection");
			try {
				connection = dataSource.getConnection();
				log.trace("Connection was opened");
			} catch (SQLException e1) {
				log.error("Cannot open connection", e1);
				throw new DAOException("Cannot open connection", e1);
			}
			try {
				log.trace("Create prepared statement");
				statement = connection.prepareStatement(query);
				statement.setString(1, newStudentName);
				statement.setLong(2, studentId);
				statement.executeUpdate();
				log.trace("Student with studentId = " + studentId + " was updated by studentName = " + newStudentName);
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot update student", e);
			throw new DAOException("Cannot update student", e);
		}finally{
			try {
				if (statement != null) {
					statement.close();
					log.trace("Prepared statement was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close prepared statement", e);
			}
			try {
				if (connection != null) {
					connection.close();
					log.trace("Connection was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
	}
	@Override
	public void deleteStudentById(long studentId) throws DAOException{
		log.info("Removing student by studentId = " + studentId);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "DELETE FROM students WHERE id = ?";
		try {
			log.trace("Open connection");
			try {
				connection = dataSource.getConnection();
				log.trace("Connection was opened");
			} catch (SQLException e1) {
				log.error("Cannot open connection", e1);
				throw new DAOException("Cannot open connection", e1);
			}
			try {
				log.trace("Create prepared statement");
				statement = connection.prepareStatement(query);
				statement.setLong(1, studentId);
				statement.executeUpdate();
				log.trace("Student with studentId = " + studentId + " was removed");
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove student", e);
			throw new DAOException("Cannot remove student", e);
		}finally{
			try {
				if (statement != null) {
					statement.close();
					log.trace("Prepared statement was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close prepared statement", e);
			}
			try {
				if (connection != null) {
					connection.close();
					log.trace("Connection was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
	}
	@Override
	public void deleteAllStudents() throws DAOException{
		log.info("Removing all students");
		Connection connection = null;
		Statement statement = null;
		String query = "DELETE FROM students";
		try {
			log.trace("Open connection");
			try {
				connection = dataSource.getConnection();
				log.trace("Connection was opened");
			} catch (SQLException e1) {
				log.error("Cannot open connection", e1);
				throw new DAOException("Cannot open connection", e1);
			}
			try {
				log.trace("Create statement");
				statement = connection.createStatement();
				statement.executeUpdate(query);
				log.trace("Students were removed");
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove students", e);
			throw new DAOException("Cannot remove students", e);
		}finally{
			try {
				if (statement != null) {
					statement.close();
					log.trace("Statement was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close statement", e);
			}
			try {
				if (connection != null) {
					connection.close();
					log.trace("Connection was closed");
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}		
	}
}