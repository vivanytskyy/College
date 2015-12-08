package com.gmail.ivanytskyy.vitaliy.domain;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.ClassroomDao;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * Classroom class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class Classroom {
	private static DaoManager daoManager = DaoManager.getInstance();
	private static final Logger log = Logger.getLogger(Classroom.class);	
	private long classroomId;
	private String classroomName;
	public void setClassroomId(long classroomId) {
		this.classroomId = classroomId;
	}
	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}
	public long getClassroomId() {
		return classroomId;
	}
	public String getClassroomName() {
		return classroomName;
	}
	public static Classroom createClassroom(String classroomName) throws DAOException{		
		log.info("Creating classroom with classroomName = " + classroomName);
		log.info("Get ClassroomDao object");
		ClassroomDao classroomDao = daoManager.getClassroomDao();
		Classroom classroom = null;
		try {
			log.trace("Create classroom");
			classroom = classroomDao.createClassroom(classroomName);
			log.trace("Classroom was created");
		} catch (DAOException e) {
			log.error("Cannot create classroom", e);
			throw new DAOException("Cannot create classroom", e);
		}
		log.trace("Returning classroom");
		return classroom;
	}	
	public static Classroom getClassroomById(long classroomId) throws DAOException{
		log.info("Getting classroom by classroomId = " + classroomId);
		log.info("Get ClassroomDao object");
		ClassroomDao classroomDao = daoManager.getClassroomDao();
		Classroom classroom = null;
		try {
			log.trace("Get classroom with classroomId = " + classroomId);
			classroom = classroomDao.findClassroomById(classroomId);
			log.trace("Classroom was gotten");
		} catch (DAOException e) {
			log.error("Cannot get classroom", e);
			throw new DAOException("Cannot get classroom", e);
		}
		log.trace("Returning classroom");
		return classroom;
	}
	public static List<Classroom> getClassroomsByName(String classroomName) throws DAOException{
		log.info("Getting classrooms by classroomName = " + classroomName);
		log.info("Get ClassroomDao object");
		ClassroomDao classroomDao = daoManager.getClassroomDao();
		List<Classroom> classrooms = new LinkedList<Classroom>();
		try {
			log.trace("Get classrooms with classroomName = " + classroomName);
			classrooms = classroomDao.findClassroomsByName(classroomName);
			log.trace("Classrooms were gotten");
		} catch (DAOException e) {
			log.error("Cannot get classrooms", e);
			throw new DAOException("Cannot get classrooms", e);
		}
		log.trace("Returning classrooms");
		return classrooms;
	}
	public static List<Classroom> getAllClassrooms() throws DAOException{
		log.info("Getting all classrooms");
		log.info("Get ClassroomDao object");
		ClassroomDao classroomDao = daoManager.getClassroomDao();
		List<Classroom> classrooms = new LinkedList<Classroom>();
		try {
			log.trace("Find classrooms");
			classrooms = classroomDao.findAllClassrooms();
			log.trace("Classrooms were gotten");
		} catch (DAOException e) {
			log.error("Cannot get classrooms", e);
			throw new DAOException("Cannot get classrooms", e);
		}
		log.trace("Returning classrooms");
		return classrooms;
	}
	public static void updateClassroom(long classroomId, String newClassroomName) throws DAOException{
		log.info("Updating classroom  with classroomId = " + classroomId + " by new classroomName = " + newClassroomName);
		log.info("Get ClassroomDao object");
		ClassroomDao classroomDao = daoManager.getClassroomDao();		
		try {
			log.trace("Update classroom");
			classroomDao.updateClassroom(classroomId, newClassroomName);
			log.trace("Classroom was updated");
		} catch (DAOException e) {
			log.error("Cannot update classroom", e);
			throw new DAOException("Cannot update classroom", e);
		}
	}
	public static void removeClassroomById(long classroomId) throws DAOException{
		log.info("Remove classroom by classroomId = " + classroomId);
		log.info("Get ClassroomDao object");
		ClassroomDao classroomDao = daoManager.getClassroomDao();
		try {
			log.trace("Remove classroom with classroomId = " + classroomId);
			classroomDao.deleteClassroomById(classroomId);
			log.trace("Classroom was removed");
		} catch (DAOException e) {
			log.error("Cannot remove classroom", e);
			throw new DAOException("Cannot remove classroom", e);
		}
	}
}