package com.gmail.ivanytskyy.vitaliy.domain;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.LecturerDao;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * Lecturer class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class Lecturer {
	private static DaoManager daoManager = DaoManager.getInstance();
	private static final Logger log = Logger.getLogger(Lecturer.class);
	private long lecturerId;
	private String lecturerName;
	public long getLecturerId() {
		return lecturerId;
	}
	public void setLecturerId(long lecturerId) {
		this.lecturerId = lecturerId;
	}
	public String getLecturerName() {
		return lecturerName;
	}
	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}
	public static Lecturer createLecturer(String lecturerName) throws DAOException{
		log.info("Creating lecturer with lecturerName = " + lecturerName);
		log.info("Get LecturerDao object");
		LecturerDao lecturerDao = daoManager.getLecturerDao();
		Lecturer lecturer = null;
		try {
			log.trace("Create lecturer");
			lecturer = lecturerDao.createLecturer(lecturerName);
			log.trace("Lecturer was created");
		} catch (DAOException e) {
			log.error("Cannot create lecturer", e);
			throw new DAOException("Cannot create lecturer", e);
		}
		log.trace("Returning lecturer");
		return lecturer;
	}	
	public static Lecturer getLecturerById(long lecturerId) throws DAOException{
		log.info("Getting lecturer by lecturerId = " + lecturerId);
		log.info("Get LecturerDao object");
		LecturerDao lecturerDao = daoManager.getLecturerDao();
		Lecturer lecturer = null;
		try {
			log.trace("Get lecturer with lecturerId = " + lecturerId);
			lecturer = lecturerDao.findLecturerById(lecturerId);
			log.trace("Lecturer was gotten");
		} catch (DAOException e) {
			log.error("Cannot get Lecturer", e);
			throw new DAOException("Cannot get lecturer", e);
		}
		log.trace("Returning lecturer");
		return lecturer;
	}
	public static List<Lecturer> getLecturersByName(String lecturerName) throws DAOException{
		log.info("Getting lecturers by lecturerName = " + lecturerName);
		log.info("Get LecturerDao object");
		LecturerDao lecturerDao = daoManager.getLecturerDao();
		List<Lecturer> lecturers = new LinkedList<Lecturer>();
		try {
			log.trace("Get lecturers with lecturerName = " + lecturerName);
			lecturers = lecturerDao.findLecturersByName(lecturerName);
			log.trace("Lecturers were gotten");
		} catch (DAOException e) {
			log.error("Cannot get lecturers", e);
			throw new DAOException("Cannot get lecturers", e);
		}
		log.trace("Returning lecturers");
		return lecturers;
	}
	public static List<Lecturer> getAllLecturers() throws DAOException{
		log.info("Getting all lecturers");
		log.info("Get LecturerDao object");
		LecturerDao lecturerDao = daoManager.getLecturerDao();
		List<Lecturer> lecturers = new LinkedList<Lecturer>();
		try {
			log.trace("Find lecturers");
			lecturers = lecturerDao.findAllLecturers();
			log.trace("Lecturers were gotten");
		} catch (DAOException e) {
			log.error("Cannot get lecturers", e);
			throw new DAOException("Cannot get lecturers", e);
		}
		log.trace("Returning lecturers");
		return lecturers;
	}
	public static void updateLecturer(long lecturerId, String newLecturerName) throws DAOException{
		log.info("Updating lecturer  with lecturerId = " + lecturerId 
				+ " by new lecturerName = " + newLecturerName);
		log.info("Get LecturerDao object");
		LecturerDao lecturerDao = daoManager.getLecturerDao();		
		try {
			log.trace("Update lecturer");
			lecturerDao.updateLecturer(lecturerId, newLecturerName);
			log.trace("Lecturer was updated");
		} catch (DAOException e) {
			log.error("Cannot update lecturer", e);
			throw new DAOException("Cannot update lecturer", e);
		}
	}
	public static void removeLecturerById(long lecturerId) throws DAOException{
		log.info("Remove lecturer by lecturerId = " + lecturerId);
		log.info("Get LecturerDao object");
		LecturerDao lecturerDao = daoManager.getLecturerDao();
		try {
			log.trace("Remove lecturer with lecturerId = " + lecturerId);
			lecturerDao.deleteLecturerById(lecturerId);
			log.trace("Lecturer was removed");
		} catch (DAOException e) {
			log.error("Cannot remove lecturer", e);
			throw new DAOException("Cannot remove lecturer", e);
		}
	}
}