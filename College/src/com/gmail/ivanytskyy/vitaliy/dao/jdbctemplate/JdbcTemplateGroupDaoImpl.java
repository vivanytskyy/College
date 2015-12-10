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
import com.gmail.ivanytskyy.vitaliy.dao.GroupDao;
import com.gmail.ivanytskyy.vitaliy.domain.Group;
/*
 * Task #2/2015/12/08 (web project #2)
 * JdbcTemplateGroupDaoImpl class
 * @version 1.01 2015.12.09
 * @author Vitaliy Ivanytskyy
 */
public class JdbcTemplateGroupDaoImpl implements GroupDao {
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateGroupDaoImpl.class.getName());	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Group createGroup(final String groupName) throws DAOException {
		log.info("Creating new group with groupName = " + groupName);
		final String insertQuery = "INSERT INTO groups (name) VALUES (?)";
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setString(1, groupName);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newGroupId = keyHolder.getKey().longValue();
		log.info("Return Group object by calling findGroupById method");
		return findGroupById(newGroupId);
	}
	@Override
	public Group findGroupById(long groupId) throws DAOException {
		log.info("Getting group by groupId = " + groupId);
		String query = "SELECT * FROM groups WHERE id = ?";
		log.info("Return Group object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{groupId},
		        new GroupMapper());
	}
	@Override
	public List<Group> findGroupsByName(String groupName) throws DAOException {
		log.info("Getting groups by groupName = " + groupName);
		String sql = "SELECT name, id FROM groups WHERE name = ?";
		log.info("Return List<Group> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				sql,
				new Object[]{groupName},
		        new GroupMapper());
	}
	@Override
	public List<Group> findAllGroups() throws DAOException {
		log.info("Getting all groups");
		String query = "SELECT * FROM groups";
		log.info("Return List<Group> (all groups) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new GroupMapper());
	}
	@Override
	public void updateGroup(long groupId, String newGroupName) throws DAOException {
		log.info("Updating group with groupId = " + groupId 
				+ " by new groupName = " + newGroupName);
		String query = "UPDATE groups SET name = ? WHERE id = ?";
		log.info("Update group by jdbcTemplate.update");
		this.jdbcTemplate.update(query, newGroupName, groupId);
	}
	@Override
	public void deleteGroupById(long groupId) throws DAOException {
		log.info("Delete group by groupId = " + groupId);
		String query = "DELETE FROM groups WHERE id = ?";
		log.info("Delete group by jdbcTemplate.update");
		this.jdbcTemplate.update(query, groupId);
	}
	@Override
	public void deleteAllGroups() throws DAOException {
		log.info("Delete all groups");
		String query = "DELETE FROM groups";
		log.info("Delete all groups by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class GroupMapper implements RowMapper<Group>{
		@Override
		public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			Group group = new Group();
			group.setGroupName(rs.getString("name"));
			group.setGroupId(rs.getLong("id"));
			return group;
		}		
	}
}