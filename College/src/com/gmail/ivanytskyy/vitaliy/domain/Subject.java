package com.gmail.ivanytskyy.vitaliy.domain;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.SubjectDao;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * Subject class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class Subject {
	private static DaoManager daoManager = DaoManager.getInstance();
	public static final Logger log = Logger.getLogger(Subject.class);
	private long subjectId;
	private String subjectName;
	public long getSubjectId() {
		return subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public static Subject createSubject(String subjectName) throws DAOException{
		log.info("Creating subject with subjectName = " + subjectName);
		log.info("Get SubjectDao object");
		SubjectDao subjectDao = daoManager.getSubjectDao();
		Subject subject = null;
		try {
			log.trace("Create subject");
			subject = subjectDao.createSubject(subjectName);
			log.trace("Subject with subjectName = " + subjectName + " was created");
		} catch (DAOException e) {
			log.error("Cannot create subject", e);
			throw new DAOException("Cannot create subject", e);
		}
		log.trace("Returning subject");
		return subject;
	}
	public static Subject getSubjectById(long subjectId) throws DAOException{
		log.info("Getting subject by subjectId = " + subjectId);
		log.info("Get SubjectDao object");
		SubjectDao subjectDao = daoManager.getSubjectDao();
		Subject subject = null;
		try {
			log.trace("Get subject with subjectId = " + subjectId);
			subject = subjectDao.findSubjectById(subjectId);
			log.trace("Subject was gotten");
		} catch (DAOException e) {
			log.error("Cannot get subject", e);
			throw new DAOException("Cannot get subject", e);
		}
		log.trace("Returning subject");
		return subject;
	}
	public static List<Subject> getSubjectsByName(String subjectName) throws DAOException{
		log.info("Getting subjects by subjectName = " + subjectName);
		log.info("Get SubjectDao object");
		SubjectDao subjectDao = daoManager.getSubjectDao();
		List<Subject> subjects = new LinkedList<Subject>();
		try {
			log.trace("Get subjects with subjectName = " + subjectName);
			subjects = subjectDao.findSubjectsByName(subjectName);
			log.trace("Subjects were gotten");
		} catch (DAOException e) {
			log.error("Cannot get subjects", e);
			throw new DAOException("Cannot get subjects", e);
		}
		log.trace("Returning subjects");
		return subjects;
	}
	public static List<Subject> getAllSubjects() throws DAOException{
		log.info("Getting all subjects");
		log.info("Get SubjectDao object");
		SubjectDao subjectDao = daoManager.getSubjectDao();
		List<Subject> subjects = new LinkedList<Subject>();
		try {
			log.trace("Find subjects");
			subjects = subjectDao.findAllSubjects();
			log.trace("Subjects were gotten");
		} catch (DAOException e) {
			log.error("Cannot get subjects", e);
			throw new DAOException("Cannot get subjects", e);
		}
		log.trace("Returning subjects");
		return subjects;
	}
	public static void updateSubject(long subjectId, String newSubjectName) throws DAOException{
		log.info("Updating subject  with subjectId = " + subjectId + " by new subjectName = " + newSubjectName);
		log.info("Get SubjectDao object");
		SubjectDao subjectDao = daoManager.getSubjectDao();	
		try {
			log.trace("Update subject");
			subjectDao.updateSubject(subjectId, newSubjectName);
			log.trace("Subject was updated");
		} catch (DAOException e) {
			log.error("Cannot update subject", e);
			throw new DAOException("Cannot update subject", e);
		}
	}
	public static void removeSubjectById(long subjectId) throws DAOException{
		log.info("Remove subject by subjectId = " + subjectId);
		log.info("Get SubjectDao object");
		SubjectDao subjectDao = daoManager.getSubjectDao();
		try {
			log.trace("Remove subject with subjectId = " + subjectId);
			subjectDao.deleteSubjectById(subjectId);
			log.trace("Subject was removed");
		} catch (DAOException e) {
			log.error("Cannot remove subject", e);
			throw new DAOException("Cannot remove subject", e);
		}
	}
}