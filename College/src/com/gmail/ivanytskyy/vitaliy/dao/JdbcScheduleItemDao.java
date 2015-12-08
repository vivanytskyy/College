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
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * JdbcScheduleItemDao class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class JdbcScheduleItemDao implements ScheduleItemDao{
	private DataSource dataSource;
	private static final Logger log = Logger.getLogger(JdbcScheduleItemDao.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public ScheduleItem createScheduleItem(long groupId, long lecturerId, long classroomId,
			long subjectId, long lessonIntervalId, long scheduleId) throws DAOException{
		log.info("Creating new scheduleItem with"
				+ " groupId/lecturerId/classroomId/subjectId/lessonIntervalId/scheduleId = " 
				+ groupId + "/" 
				+ lecturerId + "/" 
				+ classroomId + "/" 
				+ subjectId + "/" 
				+ lessonIntervalId + "/" 
				+ scheduleId);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ScheduleItem scheduleItem = null;
		String query = "INSERT INTO schedule_items (group_id, lecturer_id, classroom_id, subject_id, "
				+ "lesson_interval_id, schedule_id) VALUES (?, ?, ?, ?, ?, ?)";
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
				statement.setLong(1, groupId);
				statement.setLong(2, lecturerId);
				statement.setLong(3, classroomId);
				statement.setLong(4, subjectId);
				statement.setLong(5, lessonIntervalId);
				statement.setLong(6, scheduleId);
				statement.execute();
				try {
					log.trace("Get result set");
					resultSet = statement.getGeneratedKeys();
					log.trace("Create scheduleItem to return");
					while(resultSet.next()){
						scheduleItem = new ScheduleItem();
						scheduleItem.setGroupId(resultSet.getLong("group_id"));
						scheduleItem.setLecturerId(resultSet.getLong("lecturer_id"));
						scheduleItem.setClassroomId(resultSet.getLong("classroom_id"));
						scheduleItem.setSubjectId(resultSet.getLong("subject_id"));
						scheduleItem.setLessonIntervalId(resultSet.getLong("lesson_interval_id"));
						scheduleItem.setScheduleId(resultSet.getLong("schedule_id"));
						scheduleItem.setScheduleItemId(resultSet.getLong("id"));
					}
					log.trace("scheduleItem with name = " 
							+ " groupId/lecturerId/classroomId/subjectId/lessonIntervalId/scheduleId = " 
							+ groupId + "/" 
							+ lecturerId + "/" 
							+ classroomId + "/" 
							+ subjectId + "/" 
							+ lessonIntervalId + "/" 
							+ scheduleId
							+ " was created");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot create scheduleItem", e);
			throw new DAOException("Cannot create scheduleItem", e);
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
		log.trace("Returning scheduleItem");
		return scheduleItem;
	}
	@Override
	public void moveScheduleItemToAnotherSchedule(long scheduleItemId, long scheduleId) throws DAOException{
		log.info("Moving scheduleItem with scheduleItemid = " + scheduleItemId 
				+ " to schedule with scheduleId = " + scheduleId);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE schedule_items SET schedule_id = ? WHERE id = ?";
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
				statement.setLong(1, scheduleId);
				statement.setLong(2, scheduleItemId);
				statement.executeUpdate();
				log.trace("scheduleItem with scheduleItemid = " + scheduleItemId 
						+ " was moved to schedule with scheduleId = " + scheduleId);
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot move scheduleItem", e);
			throw new DAOException("Cannot move scheduleItem", e);
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
	public ScheduleItem findScheduleItemById(long id) throws DAOException{
		log.info("Getting scheduleItem by id = " + id);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ScheduleItem scheduleItem = null;
		String query = "SELECT id, group_id, lecturer_id, classroom_id, subject_id, "
				+ "lesson_interval_id, schedule_id FROM schedule_items WHERE id = ? ";
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
				statement.setLong(1, id);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find scheduleItem to return");
					while (resultSet.next()) {
						scheduleItem = new ScheduleItem();
						scheduleItem.setGroupId(resultSet.getLong("group_id"));
						scheduleItem.setLecturerId(resultSet.getLong("lecturer_id"));
						scheduleItem.setClassroomId(resultSet.getLong("classroom_id"));
						scheduleItem.setSubjectId(resultSet.getLong("subject_id"));
						scheduleItem.setLessonIntervalId(resultSet.getLong("lesson_interval_id"));
						scheduleItem.setScheduleId(resultSet.getLong("schedule_id"));
						scheduleItem.setScheduleItemId(resultSet.getLong("id"));
					}
					log.trace("scheduleItem with id = " + id + " was found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find scheduleItem by id", e);
			throw new DAOException("Cannot find scheduleItem by id", e);
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
		log.trace("Returning scheduleItem");
		return scheduleItem;
	}
	@Override
	public List<ScheduleItem> findScheduleItemsByScheduleId(long scheduleId) throws DAOException{
		log.info("Getting scheduleItems by scheduleId = " + scheduleId);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<ScheduleItem> scheduleItems = new LinkedList<ScheduleItem>();
		String query = "SELECT * FROM schedule_items WHERE schedule_id = ?";
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
				statement.setLong(1, scheduleId);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find scheduleItems to return");
					while (resultSet.next()) {
						ScheduleItem scheduleItem = new ScheduleItem();
						scheduleItem.setGroupId(resultSet.getLong("group_id"));
						scheduleItem.setLecturerId(resultSet.getLong("lecturer_id"));
						scheduleItem.setClassroomId(resultSet.getLong("classroom_id"));
						scheduleItem.setSubjectId(resultSet.getLong("subject_id"));
						scheduleItem.setLessonIntervalId(resultSet.getLong("lesson_interval_id"));
						scheduleItem.setScheduleId(resultSet.getLong("schedule_id"));
						scheduleItem.setScheduleItemId(resultSet.getLong("id"));
						scheduleItems.add(scheduleItem);
					}
					log.trace("scheduleItems of schedule with scheduleId = " + scheduleId + " were found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find scheduleItems by scheduleId", e);
			throw new DAOException("Cannot find scheduleItems by scheduleId", e);
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
				}				
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
				log.trace("Connection was closed");
			}
		}
		log.trace("Returning scheduleItems");
		return scheduleItems;
	}
	@Override
	public List<ScheduleItem> findAllScheduleItems() throws DAOException{
		log.info("Getting all scheduleItems");
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<ScheduleItem> scheduleItems = new LinkedList<ScheduleItem>();
		String query = "SELECT * FROM schedule_items";
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
					log.trace("Getting scheduleItems");
					while (resultSet.next()) {
						ScheduleItem scheduleItem = new ScheduleItem();
						scheduleItem.setGroupId(resultSet.getLong("group_id"));
						scheduleItem.setLecturerId(resultSet.getLong("lecturer_id"));
						scheduleItem.setClassroomId(resultSet.getLong("classroom_id"));
						scheduleItem.setSubjectId(resultSet.getLong("subject_id"));
						scheduleItem.setLessonIntervalId(resultSet.getLong("lesson_interval_id"));
						scheduleItem.setScheduleId(resultSet.getLong("schedule_id"));
						scheduleItem.setScheduleItemId(resultSet.getLong("id"));
						scheduleItems.add(scheduleItem);
					}
					log.trace("scheduleItems were gotten");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot get all scheduleItems", e);
			throw new DAOException("Cannot get all scheduleItems", e);
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
		log.trace("Returning all scheduleItems");
		return scheduleItems;
	}
	@Override
	public void updateScheduleItem(long scheduleItemId,
			long newGroupId, long newLecturerId, long newClassroomId,
			long newSubjectId, long newLessonIntervalId, long newScheduleId) throws DAOException{
		log.info("Updating scheduleItem with scheduleItemId = " + scheduleItemId 
				+ " by new groupId/lecturerId/classroomId/subjectId/lessonIntervalId/newScheduleId = " 
				+ newGroupId + "/" 
				+ newLecturerId + "/" 
				+ newClassroomId + "/" 
				+ newSubjectId + "/" 
				+ newLessonIntervalId + "/"
				+ newScheduleId);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE schedule_items SET"
				+ " group_id = ?,"
				+ " lecturer_id = ?,"
				+ " classroom_id = ?,"
				+ " subject_id = ?,"
				+ " lesson_interval_id = ?,"
				+ " schedule_id = ?"
				+ " WHERE id = ?";
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
				statement.setLong(1, newGroupId);
				statement.setLong(2, newLecturerId);
				statement.setLong(3, newClassroomId);
				statement.setLong(4, newSubjectId);
				statement.setLong(5, newLessonIntervalId);
				statement.setLong(6, newScheduleId);
				statement.setLong(7, scheduleItemId);
				statement.executeUpdate();
				log.trace("ScheduleItem with scheduleItemId = " + scheduleItemId 
						+ " was updated by new groupId/lecturerId/classroomId/subjectId/lessonIntervalId/scheduleId = "
						+ newGroupId + "/"
						+ newLecturerId + "/"
						+ newClassroomId + "/"
						+ newSubjectId + "/"
						+ newLessonIntervalId + "/"
						+ newScheduleId);
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot update scheduleItem", e);
			throw new DAOException("Cannot update scheduleItem", e);
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
	public void deleteScheduleItemById(long scheduleItemId) throws DAOException{
		log.info("Removing scheduleItem by scheduleItemId = " + scheduleItemId);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "DELETE FROM schedule_items WHERE id = ?";
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
				statement.setLong(1, scheduleItemId);
				statement.executeUpdate();
				log.trace("scheduleItem with scheduleItemId = " + scheduleItemId + " was removed");
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove scheduleItem", e);
			throw new DAOException("Cannot remove scheduleItem", e);
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
	public void deleteAllScheduleItems() throws DAOException{
		log.info("Removing all scheduleItems");
		Connection connection = null;
		Statement statement = null;
		String query = "DELETE FROM schedule_items";
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
				log.trace("scheduleItems were removed");
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove scheduleItems", e);
			throw new DAOException("Cannot remove scheduleItems", e);
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