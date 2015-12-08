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
import com.gmail.ivanytskyy.vitaliy.domain.Subject;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * JdbcSubjectDao class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class JdbcSubjectDao implements SubjectDao{
	private DataSource dataSource;
	private static final Logger log = Logger.getLogger(JdbcSubjectDao.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public Subject createSubject(String subjectName) throws DAOException{
		log.info("Creating new subject with subjectName = " + subjectName);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Subject subject = null;
		String query = "INSERT INTO subjects (name) VALUES (?)";
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
				statement.setString(1, subjectName);
				statement.execute();
				try {
					log.trace("Get result set");
					resultSet = statement.getGeneratedKeys();
					log.trace("Create subject to return");
					while(resultSet.next()){
						subject = new Subject();
						subject.setSubjectName(resultSet.getString("name"));
						subject.setSubjectId(resultSet.getLong(1));
					}
					log.trace("Subject with subjectName = " + subjectName + " was created");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot create subject", e);
			throw new DAOException("Cannot create subject", e);
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
		log.trace("Returning subject");
		return subject;
	}
	@Override
	public Subject findSubjectById(long subjectId) throws DAOException{
		log.info("Getting subject by subjectId = " + subjectId);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Subject subject = null;
		String query = "SELECT name FROM subjects WHERE id = ? ";
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
				statement.setLong(1, subjectId);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find subject to return");
					while (resultSet.next()) {
						subject = new Subject();
						subject.setSubjectName(resultSet.getString("name"));
						subject.setSubjectId(subjectId);
					}
					log.trace("Subject with subjectId = " + subjectId + " was found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find subject by subjectId", e);
			throw new DAOException("Cannot find subject by subjectId", e);
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
		log.trace("Returning subject");
		return subject;
	}
	@Override
	public List<Subject> findSubjectsByName(String subjectName) throws DAOException{
		log.info("Getting subjects by subjectName = " + subjectName);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Subject> subjects = new LinkedList<Subject>();
		String query = "SELECT id, name FROM subjects WHERE name = ?";
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
				statement.setString(1, subjectName);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find subjects to return");
					while (resultSet.next()) {
						Subject subject = new Subject();
						subject.setSubjectName(resultSet.getString("name"));
						subject.setSubjectId(resultSet.getLong("id"));
						subjects.add(subject);
					}
					log.trace("Subjects with subjectName = " + subjectName + " were found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find subjects by subjectName", e);
			throw new DAOException("Cannot find subjects by subjectName", e);
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
		log.trace("Returning subjects");
		return subjects;
	}
	@Override
	public List<Subject> findAllSubjects() throws DAOException{
		log.info("Getting all subjects");
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Subject> subjects = new LinkedList<Subject>();
		String query = "SELECT * FROM subjects";
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
					log.trace("Getting subjects");
					while (resultSet.next()) {
						Subject subject = new Subject();
						subject.setSubjectName(resultSet.getString("name"));					
						subject.setSubjectId(resultSet.getLong("id"));
						subjects.add(subject);
					}
					log.trace("Subjects were gotten");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot get all subjects", e);
			throw new DAOException("Cannot get all subjects", e);
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
		log.trace("Returning all subjects");
		return subjects;
	}
	@Override
	public void updateSubject(long subjectId, String newSubjectName) throws DAOException{
		log.info("Updating subject with subjectId = " + subjectId + " by new subjectName = " + newSubjectName);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE subjects SET name = ? WHERE id = ?";
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
				statement.setString(1, newSubjectName);
				statement.setLong(2, subjectId);
				statement.executeUpdate();
				log.trace("Subject with subjectId = " + subjectId + " was updated by new subjectName = " + newSubjectName);
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot update subject", e);
			throw new DAOException("Cannot update subject", e);
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
	public void deleteSubjectById(long subjectId) throws DAOException{
		log.info("Removing subject by subjectId = " + subjectId);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "DELETE FROM subjects WHERE id = ?";
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
				statement.setLong(1, subjectId);
				statement.executeUpdate();
				log.trace("Subject with subjectId = " + subjectId + " was removed");
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove subject", e);
			throw new DAOException("Cannot remove subject", e);
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
	public void deleteAllSubjects() throws DAOException{
		log.info("Removing all subjects");
		Connection connection = null;
		Statement statement = null;
		String query = "DELETE FROM subjects";
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
				log.trace("Subjects were removed");
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove subjects", e);
			throw new DAOException("Cannot remove subjects", e);
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