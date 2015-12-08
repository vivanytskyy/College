package com.gmail.ivanytskyy.vitaliy.dao;
import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * ScheduleItemDao interface
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public interface ScheduleItemDao {
	public ScheduleItem createScheduleItem(
			long groupId, long lecturerId,
			long classroomId, long subjectId,
			long lessonIntervalId, long scheduleId) throws DAOException;
	public ScheduleItem findScheduleItemById(long scheduleItemId) throws DAOException;
	public List<ScheduleItem> findScheduleItemsByScheduleId(long scheduleId) throws DAOException;
	public List<ScheduleItem> findAllScheduleItems() throws DAOException;
	public void moveScheduleItemToAnotherSchedule(long scheduleItemId, long scheduleId) throws DAOException;
	public void updateScheduleItem(
			long scheduleItemId, long newGroupId,
			long newLecturerId, long newClassroomId,
			long newSubjectId, long newLessonIntervalId,
			long newScheduleId) throws DAOException;
	public void deleteScheduleItemById(long scheduleItemId) throws DAOException;
	public void deleteAllScheduleItems() throws DAOException;
}