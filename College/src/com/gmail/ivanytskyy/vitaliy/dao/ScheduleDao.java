package com.gmail.ivanytskyy.vitaliy.dao;
import java.util.Calendar;
import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.Schedule;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * ScheduleDao interface
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public interface ScheduleDao {
	public Schedule createSchedule(Calendar scheduleDate) throws DAOException;
	public Schedule findScheduleById(long scheduleId) throws DAOException;
	public List<Schedule> findSchedulesByDate(Calendar scheduleDate) throws DAOException;
	public List<Schedule> findAllSchedules() throws DAOException;
	public void updateSchedule(long scheduleId, Calendar newScheduleDate) throws DAOException;
	public void deleteScheduleById(long scheduleId) throws DAOException;
	public void deleteAllSchedules() throws DAOException;
}