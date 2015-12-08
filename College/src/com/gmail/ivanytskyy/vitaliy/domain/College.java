package com.gmail.ivanytskyy.vitaliy.domain;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleDao;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * College class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class College {
	private String collegeName;
	private List<Schedule> schedules;
	private static DaoManager daoManager = DaoManager.getInstance();
	private static final Logger log = Logger.getLogger(College.class.getName());
	public College(){
		this("Java EE college");
	}
	public College(String name){
		this.collegeName = name;
		schedules = new LinkedList<Schedule>();
	}
	public String getCollegeName() {
		return collegeName;
	}
	/**
	 * obtainSchedule method
	 * @param group is Group object
	 * @param someDate is GregorianCalendar object
	 * @return result as String type variable
	 */
	public String obtainSchedule(Group group, Calendar someDate){		
		log.info("Obtaining schedule as string for group with "
				+ "groupId = " + group.getGroupId() 
				+ " and date = " + dateToString(someDate));
		log.info("Get ScheduleDao object");
		ScheduleDao scheduleDao = daoManager.getScheduleDao();
		StringBuffer sb = new StringBuffer();		
		try {
			log.trace("Get all schedules from DB");
			schedules = scheduleDao.findAllSchedules();
			log.trace("All schedules were gotten");
		} catch (DAOException e) {
			log.error("Cannot get schedules", e);
		}		
		for (Schedule schedule : schedules) {
			if(dateToString(schedule.getScheduleDate()).equals(dateToString(someDate))){
				log.trace("Found schedule with id = " + schedule.getScheduleId());
				List<String> scheduleItemsAsStringList = new LinkedList<String>();
				try {
					scheduleItemsAsStringList = schedule.obtainScheduleItemsAsStringList(group);
				} catch (DAOException e) {
					log.error("Cannot get scheduleItems", e);
				}
				for (String item : scheduleItemsAsStringList) {
					sb.append(item);
					if(!item.equals("\n")){
						sb.append("|");
					}
				}
			}
		}
		log.trace("Returning schedule as string");
		return sb.toString();		
	}
	/**
	 * obtainSchedule method
	 * @param classroom is Classroom object
	 * @param someDate is GregorianCalendar object
	 * @return result as String type variable
	 */
	public String obtainSchedule(Classroom classroom, Calendar someDate){
		log.info("Obtaining schedule as string for classroom with "
				+ "classroomId = " + classroom.getClassroomId() 
				+ " and date = " + dateToString(someDate));
		log.info("Get ScheduleDao object");
		ScheduleDao scheduleDao = daoManager.getScheduleDao();
		StringBuffer sb = new StringBuffer();
		try {
			log.trace("Get all schedules from DB");
			schedules = scheduleDao.findAllSchedules();
			log.trace("All schedules were gotten");
		} catch (DAOException e) {
			log.error("Cannot get schedules", e);
		}
		for (Schedule schedule : schedules) {
			if(dateToString(schedule.getScheduleDate()).equals(dateToString(someDate))){
				log.trace("Found schedule with id = " + schedule.getScheduleId());
				List<String> scheduleItemsAsStringList = new LinkedList<String>();
				try {
					scheduleItemsAsStringList = schedule.obtainScheduleItemsAsStringList(classroom);
				} catch (DAOException e) {
					log.error("Cannot get scheduleItems", e);
				}
				for (String item : scheduleItemsAsStringList) {
					sb.append(item);
					if(!item.equals("\n")){
						sb.append("|");
					}
				}
			}
		}
		log.trace("Returning schedule as string");
		return sb.toString();
	}
	/**
	 * obtainSchedule method
	 * @param lecturer is Lecturer object
	 * @param someDate is GregorianCalendar object
	 * @return result as String type variable
	 */
	public String obtainSchedule(Lecturer lecturer, Calendar someDate){
		log.info("Obtaining schedule as string for lecturer with "
				+ "lecturerId = " + lecturer.getLecturerId() 
				+ " and date = " + dateToString(someDate));
		log.info("Get ScheduleDao object");
		ScheduleDao scheduleDao = daoManager.getScheduleDao();
		StringBuffer sb = new StringBuffer();
		try {
			log.trace("Get all schedules from DB");
			schedules = scheduleDao.findAllSchedules();
			log.trace("All schedules were gotten");
		} catch (DAOException e) {
			log.error("Cannot get schedules");
		}
		for (Schedule schedule : schedules) {			
			if(dateToString(schedule.getScheduleDate()).equals(dateToString(someDate))){
				log.trace("Found schedule with id = " + schedule.getScheduleId());
				List<String> scheduleItemsAsStringList = new LinkedList<String>();
				try {
					scheduleItemsAsStringList = schedule.obtainScheduleItemsAsStringList(lecturer);
				} catch (DAOException e) {
					log.error("Cannot get scheduleItems", e);
				}
				for (String item : scheduleItemsAsStringList) {
					sb.append(item);
					if(!item.equals("\n")){
						sb.append("|");
					}
				}
			}
		}
		log.trace("Returning schedule as string");
		return sb.toString();
	}
	private String dateToString(Calendar date){
		return date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
	}	
}