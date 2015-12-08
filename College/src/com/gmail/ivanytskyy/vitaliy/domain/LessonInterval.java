package com.gmail.ivanytskyy.vitaliy.domain;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.LessonIntervalDao;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * LessonInterval class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class LessonInterval {
	private static DaoManager daoManager = DaoManager.getInstance();
	private static final Logger log = Logger.getLogger(LessonInterval.class);
	private long lessonIntervalId;
	private String lessonStart;
	private String lessonFinish;
	public long getLessonIntervalId() {
		return lessonIntervalId;
	}
	public void setLessonIntervalId(long lessonIntervalId) {
		this.lessonIntervalId = lessonIntervalId;
	}
	public String getLessonStart() {
		return lessonStart;
	}
	public void setLessonStart(String lessonStart) {
		this.lessonStart = lessonStart;
	}
	public String getLessonFinish() {
		return lessonFinish;
	}
	public void setLessonFinish(String lessonFinish) {
		this.lessonFinish = lessonFinish;
	}
	public static LessonInterval createLessonInterval(String lessonStart, String lessonFinish) throws DAOException{
		log.info("Creating lessonInterval with lessonStart = " + lessonStart 
				+ " and lessonFinish = " + lessonFinish);
		log.info("Get LessonIntervalDao object");
		LessonIntervalDao lessonIntervalDao = daoManager.getLessonIntervalDao();
		LessonInterval lessonInterval = null;
		try {
			log.trace("Create lessonInterval");
			lessonInterval = lessonIntervalDao.createLessonInterval(lessonStart, lessonFinish);
			log.trace("LessonInterval was created");
		} catch (DAOException e) {
			log.error("Cannot create lessonInterval", e);
			throw new DAOException("Cannot create lessonInterval", e);
		}
		log.trace("Returning lessonInterval");
		return lessonInterval;
	}
	public static LessonInterval getLessonIntervalById(long lessonIntervalId) throws DAOException{
		log.info("Getting lessonInterval by lessonIntervalId = " + lessonIntervalId);
		log.info("Get LessonIntervalDao object");
		LessonIntervalDao lessonIntervalDao = daoManager.getLessonIntervalDao();
		LessonInterval lessonInterval = null;
		try {
			log.trace("Get lessonInterval with lessonIntervalId = " + lessonIntervalId);
			lessonInterval = lessonIntervalDao.findLessonIntervalById(lessonIntervalId);
			log.trace("LessonInterval was gotten");
		} catch (DAOException e) {
			log.error("Cannot get lessonInterval", e);
			throw new DAOException("Cannot get lessonInterval", e);
		}
		log.trace("Returning lessonInterval");
		return lessonInterval;
	}
	public static List<LessonInterval> getAllLessonIntervals() throws DAOException{
		log.info("Getting all lessonIntervals");
		log.info("Get LessonIntervalDao object");
		LessonIntervalDao lessonIntervalDao = daoManager.getLessonIntervalDao();
		List<LessonInterval> lessonIntervals = new LinkedList<LessonInterval>();
		try {
			log.trace("Find lessonIntervals");
			lessonIntervals = lessonIntervalDao.findAllLessonIntervals();
			log.trace("LessonIntervals were gotten");
		} catch (DAOException e) {
			log.error("Cannot get lessonIntervals", e);
			throw new DAOException("Cannot get lessonIntervals", e);
		}
		log.trace("Returning lessonIntervals");
		return lessonIntervals;
	}
	public static void updateLessonInterval(
			long lessonIntervalId,
			String newLessonStart,
			String newLessonFinish) throws DAOException{
		log.info("Updating lessonInterval with lessonIntervalId = " + lessonIntervalId);
		log.info("Get LessonIntervalDao object");
		LessonIntervalDao lessonIntervalDao = daoManager.getLessonIntervalDao();
		try {
			log.trace("Update lessonInterval");
			lessonIntervalDao.updateLessonInterval(lessonIntervalId, newLessonStart, newLessonFinish);
			log.trace("LessonInterval was updated");
		} catch (DAOException e) {
			log.error("Cannot update lessonInterval", e);
			throw new DAOException("Cannot update lessonInterval", e);
		}
	}
	public static void removeLessonIntervalById(long lessonIntervalId) throws DAOException{
		log.info("Remove lessonInterval by lessonIntervalId = " + lessonIntervalId);
		log.info("Get LessonIntervalDao object");
		LessonIntervalDao lessonIntervalDao = daoManager.getLessonIntervalDao();
		try {
			log.trace("Remove lessonInterval with lessonIntervalId = " + lessonIntervalId);
			lessonIntervalDao.deleteLessonIntervalById(lessonIntervalId);
			log.trace("LessonInterval was removed");
		} catch (DAOException e) {
			log.error("Cannot remove lessonInterval", e);
			throw new DAOException("Cannot remove lessonInterval", e);
		}
	}	
}