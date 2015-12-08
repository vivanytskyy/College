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
import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * JdbcLecturerDao class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class JdbcLecturerDao implements LecturerDao{
	private DataSource dataSource;
	private static final Logger log = Logger.getLogger(JdbcLecturerDao.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public Lecturer createLecturer(String lecturerName) throws DAOException{
		log.info("Creating new lecturer with lecturerName = " + lecturerName);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Lecturer lecturer = null;
		String query = "INSERT INTO lecturers (name) VALUES (?)";
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
				statement.setString(1, lecturerName);
				statement.execute();
				try {
					log.trace("Get result set");
					resultSet = statement.getGeneratedKeys();
					log.trace("Create lecturer to return");
					while(resultSet.next()){
						lecturer = new Lecturer();
						lecturer.setLecturerName(resultSet.getString("name"));
						lecturer.setLecturerId(resultSet.getLong(1));
					}
					log.trace("Lecturer with lecturerName = " + lecturerName + " was created");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot create lecturer", e);
			throw new DAOException("Cannot create lecturer", e);
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
		log.trace("Returning lecturer");
		return lecturer;
	}
	@Override
	public Lecturer findLecturerById(long lecturerId) throws DAOException{
		log.info("Getting lecturer by lecturerId = " + lecturerId);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Lecturer lecturer = null;
		String query = "SELECT name FROM lecturers WHERE id = ? ";
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
				statement.setLong(1, lecturerId);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find lecturer to return");
					while (resultSet.next()) {
						lecturer = new Lecturer();
						lecturer.setLecturerName(resultSet.getString("name"));
						lecturer.setLecturerId(lecturerId);
					}
					log.trace("Lecturer with lecturerId = " + lecturerId + " was found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find lecturer by lecturerId", e);
			throw new DAOException("Cannot find lecturer by lecturerId", e);
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
		log.trace("Returning lecturer");
		return lecturer;
	}
	@Override
	public List<Lecturer> findLecturersByName(String lecturerName) throws DAOException{
		log.info("Getting lecturers by lecturerName = " + lecturerName);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Lecturer> lecturers = new LinkedList<Lecturer>();
		String query = "SELECT id, name FROM lecturers WHERE name = ?";
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
				statement.setString(1, lecturerName);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find lecturers to return");
					while (resultSet.next()) {
						Lecturer lecturer = new Lecturer();
						lecturer.setLecturerName(resultSet.getString("name"));						
						lecturer.setLecturerId(resultSet.getLong("id"));
						lecturers.add(lecturer);
					}
					log.trace("Lecturers with lecturerName = " + lecturerName + " were found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find lecturers by lecturerName", e);
			throw new DAOException("Cannot find lecturers by lecturerName", e);
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
		log.trace("Returning lecturers");
		return lecturers;
	}
	@Override
	public List<Lecturer> findAllLecturers() throws DAOException{
		log.info("Getting all lecturers");
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Lecturer> lecturers = new LinkedList<Lecturer>();
		String query = "SELECT * FROM lecturers";
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
					log.trace("Getting lecturers");
					while (resultSet.next()) {
						Lecturer lecturer = new Lecturer();
						lecturer.setLecturerName(resultSet.getString("name"));						
						lecturer.setLecturerId(resultSet.getLong("id"));
						lecturers.add(lecturer);
					}
					log.trace("Lecturers were gotten");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot get all lecturers", e);
			throw new DAOException("Cannot get all lecturers", e);
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
		log.trace("Returning all lecturers");
		return lecturers;
	}
	@Override
	public void updateLecturer(long lecturerId, String newLecturerName) throws DAOException{
		log.info("Updating subject with lecturerId = " + lecturerId
				+ " by new lecturerName = " + newLecturerName);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE lecturers SET name = ? WHERE id = ?";
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
				statement.setString(1, newLecturerName);
				statement.setLong(2, lecturerId);
				statement.executeUpdate();
				log.trace("Lecturer with lecturerId = " + lecturerId + " was updated by new lecturerName = " + newLecturerName);
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot update lecturer", e);
			throw new DAOException("Cannot update lecturer", e);
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
	public void deleteLecturerById(long lecturerId) throws DAOException{
		log.info("Removing lecturer by lecturerId=" + lecturerId);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "DELETE FROM lecturers WHERE id = ?";
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
				statement.setLong(1, lecturerId);
				statement.executeUpdate();
				log.trace("Lecturer with lecturerId=" + lecturerId + " was removed");
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove lecturer", e);
			throw new DAOException("Cannot remove lecturer", e);
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
	public void deleteAllLecturers() throws DAOException{
		log.info("Removing all lecturers");
		Connection connection = null;
		Statement statement = null;
		String query = "DELETE FROM lecturers";
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
				log.trace("lecturers were removed");
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove lecturers", e);
			throw new DAOException("Cannot remove lecturers", e);
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