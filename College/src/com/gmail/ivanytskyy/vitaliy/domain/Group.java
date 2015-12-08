package com.gmail.ivanytskyy.vitaliy.domain;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.GroupDao;
import com.gmail.ivanytskyy.vitaliy.dao.StudentDao;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * Group class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class Group {
	private long groupId;
	private String groupName;
	private List<Student> students = new LinkedList<Student>();
	private static DaoManager daoManager = DaoManager.getInstance();
	private static final Logger log = Logger.getLogger(Group.class.getName());
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public List<Student> getStudents() throws DAOException{
		log.info("Getting students of group with groupId = " + groupId);
		log.info("Get StudentDao object");
		StudentDao studentDao = daoManager.getStudentDao();
		try {
			log.trace("Find students");
			students = studentDao.findStudentsByGroupId(groupId);
			log.trace("Students were gotten");
		} catch (DAOException e) {
			log.error("Cannot get students", e);
			throw new DAOException("Cannot get students", e);
		}
		log.trace("Returning students");
		return students;
	}	
	public void setStudents(List<Student> students) throws DAOException{
		log.info("Moving students to group with groupId = " + groupId);
		log.info("Get StudentDao object");
		StudentDao studentDao = daoManager.getStudentDao();
		for (Student student : students) {
			try {
				log.trace("Moving student with studentId = " + student.getStudentId() + " to group with groupId = " + groupId);
				studentDao.moveStudentToAnotherGroup(student.getStudentId(), groupId);
				log.trace("Student with studentId = " + student.getStudentId() + " was moved to group with groupId = " + groupId);
			} catch (DAOException e) {
				log.error("Cannot move students", e);
				throw new DAOException("Cannot move students", e);
			}
		}
	}
	public Student createStudent(String studentName) throws DAOException{
		log.info("Creating new student with studentName = " + studentName + " and groupId = " + groupId);
		log.info("Get StudentDao object");
		StudentDao studentDao = daoManager.getStudentDao();
		Student student = null;
		try {
			log.trace("Create student");
			student = studentDao.createStudent(studentName, groupId);
			log.trace("Student was created");
		} catch (DAOException e) {
			log.error("Cannot create student", e);
			throw new DAOException("Cannot create student", e);
		}
		log.trace("Returning student");
		return student;
	}
	public void moveStudent(long studentId) throws DAOException{
		log.info("Moving student to group with groupId = " + groupId);
		log.info("Get StudentDao object");
		StudentDao studentDao = daoManager.getStudentDao();
		try {
			log.trace("Moving student with studentId = " + studentId + " to group with groupId = " + groupId);
			studentDao.moveStudentToAnotherGroup(studentId, groupId);
			log.trace("Student with studentId = " + studentId + " was moved to group with groupId = " + groupId);
		} catch (DAOException e) {
			log.error("Cannot move student", e);
			throw new DAOException("Cannot move student", e);
		}
	}
	public void deleteStudent(Student student) throws DAOException{
		log.info("Deleting student by object");
		log.info("Get StudentDao object");
		StudentDao studentDao = daoManager.getStudentDao();
		try {			
			studentDao.deleteStudentById(student.getStudentId());
			log.trace("Student was deleted");
		} catch (DAOException e) {
			log.error("Cannot delete student", e);
			throw new DAOException("Cannot delete student", e);
		}
	}
	public static Group createGroup(String groupName) throws DAOException{
		log.info("Creating group with groupName = " + groupName);
		log.info("Get GroupDao object");
		GroupDao groupDao = daoManager.getGroupDao();
		Group group = null;
		try {
			log.trace("Create group");
			group = groupDao.createGroup(groupName);
			log.trace("Group with groupName = " + groupName + " was created");
		} catch (DAOException e) {
			log.error("Cannot create group", e);
			throw new DAOException("Cannot create group", e);
		}
		log.trace("Returning group");
		return group;
	}
	public static Group getGroupById(long groupId) throws DAOException{
		log.info("Getting group by groupId = " + groupId);
		log.info("Get GroupDao object");
		GroupDao groupDao = daoManager.getGroupDao();
		Group group = null;
		try {
			log.trace("Get group with groupId = " + groupId);
			group = groupDao.findGroupById(groupId);
			log.trace("Group was gotten");
		} catch (DAOException e) {
			log.error("Cannot get group", e);
			throw new DAOException("Cannot get group", e);
		}
		log.trace("Returning group");
		return group;
	}
	public static List<Group> getGroupsByName(String groupName) throws DAOException{
		log.info("Getting groups by groupName=" + groupName);
		log.info("Get GroupDao object");
		GroupDao groupDao = daoManager.getGroupDao();
		List<Group> groups = new LinkedList<Group>();
		try {
			log.trace("Get groups with groupName=" + groupName);
			groups = groupDao.findGroupsByName(groupName);
			log.trace("Groups were gotten");
		} catch (DAOException e) {
			log.error("Cannot get groups", e);
			throw new DAOException("Cannot get groups", e);
		}
		log.trace("Returning groups");
		return groups;
	}
	public static List<Group> getAllGroups() throws DAOException{
		log.info("Getting all groups");
		log.info("Get GroupDao object");
		GroupDao groupDao = daoManager.getGroupDao();
		List<Group> groups = new LinkedList<Group>();
		try {
			log.trace("Find groups");
			groups = groupDao.findAllGroups();
			log.trace("Groups were gotten");
		} catch (DAOException e) {
			log.error("Cannot get groups", e);
			throw new DAOException("Cannot get groups", e);
		}
		log.trace("Returning groups");
		return groups;
	}
	public static void updateGroup(long groupId, String newGroupName) throws DAOException{
		log.info("Updating group  with groupId = " + groupId + " by new grouptName = " + newGroupName);
		log.info("Get GroupDao object");
		GroupDao groupDao = daoManager.getGroupDao();	
		try {
			log.trace("Update group");
			groupDao.updateGroup(groupId, newGroupName);
			log.trace("Group was updated");
		} catch (DAOException e) {
			log.error("Cannot update group", e);
			throw new DAOException("Cannot update group", e);
		}
	}
	public static void removeGroupById(long groupId) throws DAOException{
		log.info("Remove group by groupId = " + groupId);
		log.info("Get GroupDao object");
		GroupDao groupDao = daoManager.getGroupDao();
		log.info("Get StudentDao object");
		StudentDao studentDao = daoManager.getStudentDao();
		List<Student> students = new LinkedList<Student>();
		try {
			log.trace("Try get information about students of group with groupId=" + groupId);
			students = studentDao.findStudentsByGroupId(groupId);
			log.trace("Information about students of group with groupId=" + groupId + " was gotten");
		} catch (DAOException e) {
			log.error("Cannot get information about students by groupId", e);
			throw new DAOException("Cannot get information about students by groupId", e);
		}
		if(students.isEmpty()){
			try {
				log.trace("Remove group with groupId=" + groupId);
				groupDao.deleteGroupById(groupId);
				log.trace("Group was deleted");
			} catch (DAOException e) {
				log.error("Cannot remove group", e);
				throw new DAOException("Cannot remove group", e);
			}
		}else{
			log.trace("Can not delete group with the students");
		}
	}
}