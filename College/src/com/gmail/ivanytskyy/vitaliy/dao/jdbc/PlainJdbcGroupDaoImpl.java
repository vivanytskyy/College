package com.gmail.ivanytskyy.vitaliy.dao.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.GroupDao;
import com.gmail.ivanytskyy.vitaliy.domain.Group;
/*
 * Task #2/2015/12/08 (web project #2)
 * PlainJdbcGroupDaoImpl class
 * @version 1.02 2015.12.09
 * @author Vitaliy Ivanytskyy
 */
public class PlainJdbcGroupDaoImpl implements GroupDao{
	private DataSource dataSource;
	private static final Logger log = Logger.getLogger(PlainJdbcGroupDaoImpl.class);	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public Group createGroup(String groupName) throws DAOException{
		log.info("Creating new group with groupName = " + groupName);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Group group = null;
		String query = "INSERT INTO groups (name) VALUES (?)";
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
				statement.setString(1, groupName);
				statement.execute();
				try {
					log.trace("Get result set");
					resultSet = statement.getGeneratedKeys();
					log.trace("Create group to return");
					while(resultSet.next()){
						group = new Group();
						group.setGroupName(resultSet.getString("name"));
						group.setGroupId(resultSet.getLong(1));
					}
					log.trace("Group with groupName = " + groupName + " was created");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot create group", e);
			throw new DAOException("Cannot create group", e);
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
		log.trace("Returning group");
		return group;
	}
	@Override
	public Group findGroupById(long groupId) throws DAOException{
		log.info("Getting group by groupId = " + groupId);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Group group = null;
		String query = "SELECT name FROM groups WHERE id = ? ";
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
					log.trace("Find group to return");
					while (resultSet.next()) {
						group = new Group();
						group.setGroupName(resultSet.getString("name"));
						group.setGroupId(groupId);
					}
					log.trace("Group with groupId = " + groupId + " was found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find classroom by groupId", e);
			throw new DAOException("Cannot find classroom by groupId", e);
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
		log.trace("Returning group");
		return group;
	}
	@Override
	public List<Group> findGroupsByName(String groupName) throws DAOException{
		log.info("Getting groups by groupName = " + groupName);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Group> groups = new LinkedList<Group>();
		String query = "SELECT id, name FROM groups WHERE name = ?";
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
				statement.setString(1, groupName);
				try {
					log.trace("Get result set");
					resultSet = statement.executeQuery();
					log.trace("Find groups to return");
					while (resultSet.next()) {
						Group group = new Group();
						group.setGroupName(resultSet.getString("name"));
						group.setGroupId(resultSet.getLong("id"));
						groups.add(group);
					}
					log.trace("Groups with groupName = " + groupName + " were found");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot find groups by groupName", e);
			throw new DAOException("Cannot find groups by groupName", e);
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
		log.trace("Returning groups");
		return groups;
	}
	@Override
	public List<Group> findAllGroups() throws DAOException{
		log.info("Getting all groups");
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Group> groups = new LinkedList<Group>();
		String query = "SELECT * FROM groups";
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
					log.trace("Getting groups");
					while (resultSet.next()) {
						Group group = new Group();
						group.setGroupName(resultSet.getString("name"));
						group.setGroupId(resultSet.getLong("id"));
						groups.add(group);
					}
					log.trace("Groups were gotten");
				} catch (SQLException e) {
					log.error("Cannot get result set", e);
					throw new DAOException("Cannot get result set", e);
				}
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot get all groups", e);
			throw new DAOException("Cannot get all groups", e);
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
		log.trace("Returning all groups");
		return groups;
	}
	@Override
	public void updateGroup(long groupId, String newGroupName) throws DAOException{
		log.info("Updating group with groupId = " + groupId + " by new groupName = " + newGroupName);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE groups SET name = ? WHERE id = ?";
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
				statement.setString(1, newGroupName);
				statement.setLong(2, groupId);
				statement.executeUpdate();
				log.trace("Group with groupId = " + groupId + " was updated by groupName = " + newGroupName);
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot update group", e);
			throw new DAOException("Cannot update group", e);
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
	public void deleteGroupById(long groupId) throws DAOException{
		log.info("Removing group by groupId = " + groupId);
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "DELETE FROM groups WHERE id = ?";
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
				statement.executeUpdate();
				log.trace("Group with groupId = " + groupId + " was removed");
			} catch (SQLException e) {
				log.error("Cannot create prepared statement", e);
				throw new DAOException("Cannot create prepared statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove group", e);
			throw new DAOException("Cannot remove group", e);
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
	public void deleteAllGroups() throws DAOException{
		log.info("Removing all groups");
		Connection connection = null;
		Statement statement = null;
		String query = "DELETE FROM groups";
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
				log.trace("Groups were removed");
			} catch (SQLException e) {
				log.error("Cannot create statement", e);
				throw new DAOException("Cannot create statement", e);
			}
		} catch (DAOException e) {
			log.error("Cannot remove groups", e);
			throw new DAOException("Cannot remove groups", e);
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