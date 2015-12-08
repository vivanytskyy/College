package com.gmail.ivanytskyy.vitaliy.dao;
import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.Group;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * GroupDao interface
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public interface GroupDao {
	public Group createGroup(String groupName) throws DAOException;
	public Group findGroupById(long groupId) throws DAOException;
	public List<Group> findGroupsByName(String groupName) throws DAOException;
	public List<Group> findAllGroups() throws DAOException;
	public void updateGroup(long groupId, String newGroupName) throws DAOException;
	public void deleteGroupById(long groupId) throws DAOException;
	public void deleteAllGroups() throws DAOException;
}