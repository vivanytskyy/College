package com.gmail.ivanytskyy.vitaliy.domain;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleItemDao;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * ScheduleItem class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class ScheduleItem {
	private static DaoManager daoManager = DaoManager.getInstance();
	private static final Logger log = Logger.getLogger(ScheduleItem.class);
	private long scheduleItemId;
	private long groupId;	
	private long lecturerId;
	private long classroomId;
	private long subjectId;
	private long lessonIntervalId;
	private long scheduleId;
	public long getScheduleItemId() {
		return scheduleItemId;
	}
	public void setScheduleItemId(long scheduleItemId) {
		this.scheduleItemId = scheduleItemId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}	
	public long getLecturerId() {
		return lecturerId;
	}
	public void setLecturerId(long lecturerId) {
		this.lecturerId = lecturerId;
	}
	public long getClassroomId() {
		return classroomId;
	}
	public void setClassroomId(long classroomId) {
		this.classroomId = classroomId;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public long getLessonIntervalId() {
		return lessonIntervalId;
	}
	public void setLessonIntervalId(long lessonIntervalId) {
		this.lessonIntervalId = lessonIntervalId;
	}
	public long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (classroomId ^ (classroomId >>> 32));
		result = prime * result + (int) (groupId ^ (groupId >>> 32));
		result = prime * result + (int) (lecturerId ^ (lecturerId >>> 32));
		result = prime * result
				+ (int) (lessonIntervalId ^ (lessonIntervalId >>> 32));
		result = prime * result + (int) (scheduleId ^ (scheduleId >>> 32));
		result = prime * result
				+ (int) (scheduleItemId ^ (scheduleItemId >>> 32));
		result = prime * result + (int) (subjectId ^ (subjectId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleItem other = (ScheduleItem) obj;
		if (classroomId != other.classroomId)
			return false;
		if (groupId != other.groupId)
			return false;
		if (lecturerId != other.lecturerId)
			return false;
		if (lessonIntervalId != other.lessonIntervalId)
			return false;
		if (scheduleId != other.scheduleId)
			return false;
		if (scheduleItemId != other.scheduleItemId)
			return false;
		if (subjectId != other.subjectId)
			return false;
		return true;
	}
	public static ScheduleItem createScheduleItem(
			long groupId,
			long lecturerId,
			long classroomId,
			long subjectId,
			long lessonIntervalId,
			long scheduleId) throws DAOException{
		log.info("Creating new scheduleItem with"
				+ " groupId/lecturerId/classroomId/subjectId/lessonIntervalId/scheduleId = " 
				+ groupId + "/" 
				+ lecturerId + "/" 
				+ classroomId + "/" 
				+ subjectId + "/" 
				+ lessonIntervalId + "/" 
				+ scheduleId);
		log.info("Get ScheduleItemDao object");
		ScheduleItemDao scheduleItemDao = daoManager.getScheduleItemDao();
		ScheduleItem scheduleItem = null;
		try {
			log.trace("Create scheduleItem");
			scheduleItem = scheduleItemDao.createScheduleItem(
					groupId,
					lecturerId,
					classroomId,
					subjectId,
					lessonIntervalId,
					scheduleId);
			log.trace("scheduleItem was created");
		} catch (DAOException e) {
			log.error("Cannot create scheduleItem", e);
			new DAOException("Cannot create scheduleItem", e);
		}
		log.trace("Returning scheduleItem");
		return scheduleItem;
	}
	public static ScheduleItem getScheduleItemById(long scheduleItemId) throws DAOException{
		log.info("Getting scheduleItem by scheduleItemId = " + scheduleItemId);
		log.info("Get ScheduleItemDao object");
		ScheduleItemDao scheduleItemDao = daoManager.getScheduleItemDao();
		ScheduleItem scheduleItem = null;
		try {
			log.trace("Get scheduleItem with scheduleItemId = " + scheduleItemId);
			scheduleItem = scheduleItemDao.findScheduleItemById(scheduleItemId);
			log.trace("ScheduleItem was gotten");
		} catch (DAOException e) {
			log.error("Cannot get scheduleItem", e);
			throw new DAOException("Cannot get scheduleItem", e);
		}
		log.trace("Returning scheduleItem");
		return scheduleItem;
	}
	public static List<ScheduleItem> getAllScheduleItems() throws DAOException{
		log.info("Getting all scheduleItems");
		log.info("Get ScheduleItemDao object");
		ScheduleItemDao scheduleItemDao = daoManager.getScheduleItemDao();
		List<ScheduleItem> scheduleItems = new LinkedList<ScheduleItem>();
		try {
			log.trace("Find scheduleItems");
			scheduleItems = scheduleItemDao.findAllScheduleItems();
			log.trace("ScheduleItems were gotten");
		} catch (DAOException e) {
			log.error("Cannot get scheduleItems", e);
			throw new DAOException("Cannot get scheduleItems", e);
		}
		log.trace("Returning scheduleItems");
		return scheduleItems;
	}
	public static List<ScheduleItem> getScheduleItemsByScheduleId(long scheduleId) throws DAOException {
		log.info("Getting scheduleItems by scheduleId = " + scheduleId);
		log.info("Get ScheduleItemDao object");
		ScheduleItemDao scheduleItemDao = daoManager.getScheduleItemDao();
		List<ScheduleItem> scheduleItems = new LinkedList<ScheduleItem>();
		try {
			log.trace("Find scheduleItems");
			scheduleItems = scheduleItemDao.findScheduleItemsByScheduleId(scheduleId);
			log.trace("scheduleItems were gotten");
		} catch (DAOException e) {
			log.error("Cannot get scheduleItems", e);
			throw new DAOException("Cannot get scheduleItems", e);
		}
		log.trace("Returning scheduleItems");
		return scheduleItems;
	}
	public static void moveScheduleItemToAnotherSchedule(long scheduleItemId, long anotherScheduleId) throws DAOException{
		log.info("Moving scheduleItem with scheduleItemId = " + scheduleItemId 
				+ " to new schedule with scheduleId = " + anotherScheduleId);
		log.info("Get ScheduleItemDao object");
		ScheduleItemDao scheduleItemDao = daoManager.getScheduleItemDao();
		try {
			log.trace("Moving scheduleItem with scheduleItemId = " + scheduleItemId
					+ " to another schedule with scheduleId = " + anotherScheduleId);
			scheduleItemDao.moveScheduleItemToAnotherSchedule(scheduleItemId, anotherScheduleId);
			log.trace("ScheduleItem with scheduleItemId = " + scheduleItemId 
					+ " was moved to schedule with scheduleId = " + anotherScheduleId);
		} catch (DAOException e) {
			log.error("Cannot move scheduleItem", e);
			throw new DAOException("Cannot move scheduleItem", e);
		}
	}
	public static void updateScheduleItem(long scheduleItemId,
			long newGroupId, long newLecturerId, long newClassroomId,
			long newSubjectId, long newLessonIntervalId, long newScheduleId) throws DAOException{
		log.info("Updating scheduleItem with scheduleItemId = " + scheduleItemId 
				+ " by new groupId/lecturerId/classroomId/subjectId/lessonIntervalId = " 
				+ newGroupId + "/" 
				+ newLecturerId + "/" 
				+ newClassroomId + "/" 
				+ newSubjectId + "/" 
				+ newLessonIntervalId + "/" 
				+ newScheduleId);
		log.info("Get ScheduleItemDao object");
		ScheduleItemDao scheduleItemDao = daoManager.getScheduleItemDao();
		try {
			log.trace("Update scheduleItem");
			scheduleItemDao.updateScheduleItem(scheduleItemId, newGroupId, newLecturerId, newClassroomId, newSubjectId, newLessonIntervalId, newScheduleId);
			log.trace("ScheduleItem was updated");
		} catch (DAOException e) {
			log.error("Cannot update scheduleItem", e);
			throw new DAOException("Cannot update scheduleItem", e);
		}
	}
	public static void removeScheduleItemById(long scheduleItemId) throws DAOException{
		log.info("Remove scheduleItem by scheduleItemId = " + scheduleItemId);
		log.info("Get ScheduleItemDao object");
		ScheduleItemDao scheduleItemDao = daoManager.getScheduleItemDao();
		try {
			log.trace("Remove scheduleItem with scheduleItemId = " + scheduleItemId);
			scheduleItemDao.deleteScheduleItemById(scheduleItemId);
			log.trace("ScheduleItem was removed");
		} catch (DAOException e) {
			log.error("Cannot remove scheduleItem", e);
			throw new DAOException("Cannot remove scheduleItem", e);
		}
	}
}