package com.gmail.ivanytskyy.vitaliy.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.domain.Classroom;
import com.gmail.ivanytskyy.vitaliy.domain.Group;
import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
import com.gmail.ivanytskyy.vitaliy.domain.LessonInterval;
import com.gmail.ivanytskyy.vitaliy.domain.Schedule;
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;
import com.gmail.ivanytskyy.vitaliy.domain.Subject;
import org.apache.log4j.Logger;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * ScheduleItemServlet
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class ScheduleItemServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(ScheduleItemServlet.class.getName());
	private static final long serialVersionUID = 1L;
	public ScheduleItemServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String scheduleIdStr = request.getParameter("scheduleId");
		String groupIdStr = request.getParameter("groupId");
		String lecturerIdStr = request.getParameter("lecturerId");
		String subjectIdStr = request.getParameter("subjectId");
		String classroomIdStr = request.getParameter("classroomId");
		String lessonIntervalIdStr = request.getParameter("lessonIntervalId");
		String scheduleItemIdStr = request.getParameter("scheduleItemId");
		request.setAttribute("alarmMessageOfScheduleItemForMove", "");
		request.setAttribute("alarmMessageOfScheduleForMove", "");
		request.setAttribute("alarmMessageForAddScheduleId", "");
		request.setAttribute("alarmMessageForAddGroupId", "");
		request.setAttribute("alarmMessageForAddLecturerId", "");
		request.setAttribute("alarmMessageForAddClassroomId", "");
		request.setAttribute("alarmMessageForAddSubjectId", "");
		request.setAttribute("alarmMessageForAddLessonIntervalId", "");		
		request.setAttribute("alarmMessageForEditScheduleId", "");
		request.setAttribute("alarmMessageForEditGroupId", "");
		request.setAttribute("alarmMessageForEditLecturerId", "");
		request.setAttribute("alarmMessageForEditClassroomId", "");
		request.setAttribute("alarmMessageForEditSubjectId", "");
		request.setAttribute("alarmMessageForEditLessonIntervalId", "");		
		ScheduleItem scheduleItem = null;
		if(action != null 
				&& action.equalsIgnoreCase("Add")
				&& InputDataValidator.isPositiveLongNumber(scheduleIdStr)
				&& InputDataValidator.isPositiveLongNumber(groupIdStr)
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)
				&& InputDataValidator.isPositiveLongNumber(subjectIdStr)
				&& InputDataValidator.isPositiveLongNumber(classroomIdStr)
				&& InputDataValidator.isPositiveLongNumber(lessonIntervalIdStr)){
			long scheduleId = Long.valueOf(scheduleIdStr);
			long groupId = Long.valueOf(groupIdStr);
			long lecturerId = Long.valueOf(lecturerIdStr);
			long subjectId = Long.valueOf(subjectIdStr);
			long classroomId = Long.valueOf(classroomIdStr);
			long lessonIntervalId = Long.valueOf(lessonIntervalIdStr);
			if(isScheduleExist(scheduleId) 
					&& isGroupExist(groupId) 
					&& isLecturerExist(lecturerId)
					&& isClassroomExist(classroomId)
					&& isSubjectExist(subjectId)
					&& isLessonIntervalExist(lessonIntervalId)){
				try {
					log.trace("Try create scheduleItem");
					scheduleItem = ScheduleItem.createScheduleItem(groupId, lecturerId, classroomId, subjectId, lessonIntervalId, scheduleId);
					log.trace("ScheduleItem was created");
				} catch (DAOException e) {
					log.error("Cannot create scheduleItem", e);
				}
			}else{
				if(!isScheduleExist(scheduleId)){
					request.setAttribute("alarmMessageForAddScheduleId", "Schedule with this Id does not exist");
				}
				if(!isGroupExist(groupId)){
					request.setAttribute("alarmMessageForAddGroupId", "Group with this Id does not exist");
				}
				if(!isLecturerExist(lecturerId)){
					request.setAttribute("alarmMessageForAddLecturerId", "Lecturer with this Id does not exist");
				}
				if(!isClassroomExist(classroomId)){
					request.setAttribute("alarmMessageForAddClassroomId", "Classroom with this Id does not exist");
				}
				if(!isSubjectExist(subjectId)){
					request.setAttribute("alarmMessageForAddSubjectId", "Subject with this Id does not exist");
				}
				if(!isLessonIntervalExist(lessonIntervalId)){
					request.setAttribute("alarmMessageForAddLessonIntervalId", "Lesson interval with this Id does not exist");
				}
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(scheduleItemIdStr)) {
			long scheduleItemId = Long.valueOf(scheduleItemIdStr);
			try {
				log.trace("Try find scheduleItem with scheduleItemId = " + scheduleItemId);
				request.setAttribute("resultScheduleItem", ScheduleItem.getScheduleItemById(scheduleItemId));
				log.trace("ScheduleItem with scheduleItemId=" + scheduleItemId + " was found");
			} catch (DAOException e) {
				log.error("Cannot find scheduleItem", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")				
				&& InputDataValidator.isPositiveLongNumber(scheduleIdStr)
				&& InputDataValidator.isPositiveLongNumber(groupIdStr)
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)
				&& InputDataValidator.isPositiveLongNumber(subjectIdStr)
				&& InputDataValidator.isPositiveLongNumber(classroomIdStr)
				&& InputDataValidator.isPositiveLongNumber(lessonIntervalIdStr)
				&& InputDataValidator.isPositiveLongNumber(scheduleItemIdStr)) {
			long scheduleItemId = Long.valueOf(scheduleItemIdStr);
			long newScheduleId = Long.valueOf(scheduleIdStr);
			long newGroupId = Long.valueOf(groupIdStr);
			long newLecturerId = Long.valueOf(lecturerIdStr);
			long newSubjectId = Long.valueOf(subjectIdStr);
			long newClassroomId = Long.valueOf(classroomIdStr);
			long newLessonIntervalId = Long.valueOf(lessonIntervalIdStr);
			if(isScheduleExist(newScheduleId) 
					&& isGroupExist(newGroupId) 
					&& isLecturerExist(newLecturerId)
					&& isClassroomExist(newClassroomId)
					&& isSubjectExist(newSubjectId)
					&& isLessonIntervalExist(newLessonIntervalId)){
				try {
					log.trace("Try update scheduleItem with scheduleItemId = " + scheduleItemId);
					ScheduleItem.updateScheduleItem(scheduleItemId, newGroupId, newLecturerId, newClassroomId, newSubjectId, newLessonIntervalId, newScheduleId);
					log.trace("ScheduleItem with scheduleItemId = " + scheduleItemId + " was updated");
				} catch (DAOException e) {
					log.error("Cannot update scheduleItem", e);
				}
			}else{
				if(!isScheduleExist(newScheduleId)){
					request.setAttribute("alarmMessageForEditScheduleId", "Schedule with this Id does not exist");
				}
				if(!isGroupExist(newGroupId)){
					request.setAttribute("alarmMessageForEditGroupId", "Group with this Id does not exist");
				}
				if(!isLecturerExist(newLecturerId)){
					request.setAttribute("alarmMessageForEditLecturerId", "Lecturer with this Id does not exist");
				}
				if(!isClassroomExist(newClassroomId)){
					request.setAttribute("alarmMessageForEditClassroomId", "Classroom with this Id does not exist");
				}
				if(!isSubjectExist(newSubjectId)){
					request.setAttribute("alarmMessageForEditSubjectId", "Subject with this Id does not exist");
				}
				if(!isLessonIntervalExist(newLessonIntervalId)){
					request.setAttribute("alarmMessageForEditLessonIntervalId", "Lesson interval with this Id does not exist");
				}
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Delete") 
				&& InputDataValidator.isPositiveLongNumber(scheduleItemIdStr)) {
			long scheduleItemId = Long.valueOf(scheduleItemIdStr);		
			try {
				log.trace("Try delete scheduleItem");
				ScheduleItem.removeScheduleItemById(scheduleItemId);
				log.trace("ScheduleItem was deleted");
			} catch (DAOException e) {
				log.error("Cannot delete scheduleItem", e);
			}
		}else if(action != null 
				&& action.equalsIgnoreCase("Move")
				&& InputDataValidator.isPositiveLongNumber(scheduleIdStr)
				&& InputDataValidator.isPositiveLongNumber(scheduleItemIdStr)){
			long scheduleId = Long.valueOf(scheduleIdStr);
			long scheduleItemId = Long.valueOf(scheduleItemIdStr);			
			if(isScheduleExist(scheduleId) && isScheduleItemExist(scheduleItemId)){
				try {
					log.trace("Try move scheduleItem to another schedule");
					ScheduleItem.moveScheduleItemToAnotherSchedule(scheduleItemId, scheduleId);
					log.trace("ScheduleItem was moved");
					log.trace("Try get scheduleItem after moving");
					scheduleItem = ScheduleItem.getScheduleItemById(scheduleItemId);
					log.trace("ScheduleItem was gotten");
				} catch (DAOException e) {
					log.error("Cannot move scheduleItem", e);
				}
			}else if(!isScheduleItemExist(scheduleItemId)){
				request.setAttribute("alarmMessageOfScheduleItemForMove", "ScheduleItem with this Id does not exist");
			}else if(!isScheduleExist(scheduleId)){
				request.setAttribute("alarmMessageOfScheduleForMove", "Schedule with this Id does not exist");
			}			
		}
		request.setAttribute("scheduleItem", scheduleItem);
		try {
			log.trace("Try get all schedules for putting to request");
			request.setAttribute("allSchedules", Schedule.getAllSchedules());
			log.trace("Schedules were gotten");
		} catch (DAOException e1) {
			log.error("Cannot get all schedules", e1);
		}
		try {
			log.trace("Try get all scheduleItems for putting to request");
			request.setAttribute("allScheduleItems", ScheduleItem.getAllScheduleItems());
			log.trace("ScheduleItems were gotten");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/jsp/adminScheduleItem.jsp").forward(request, response);
	}
	
	private boolean isScheduleExist(long scheduleId){
		boolean result = false;
		try {
			log.trace("Try get schedule by scheduleId=" + scheduleId + " for exist checking");
			result = (Schedule.getScheduleById(scheduleId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DAOException e) {
			log.error("Cannot get schedule", e);
		}
		return result;
	}
	private boolean isGroupExist(long groupId){
		boolean result = false;
		try {
			log.trace("Try get group by groupId=" + groupId + " for exist checking");
			result = (Group.getGroupById(groupId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DAOException e) {
			log.error("Cannot get group", e);
		}		
		return result;
	}
	private boolean isLecturerExist(long lecturerId){
		boolean result = false;
		try {
			log.trace("Try get lecturer by lecturerId=" + lecturerId + " for exist checking");
			result = (Lecturer.getLecturerById(lecturerId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DAOException e) {
			log.error("Cannot get lecturer", e);
		}		
		return result;
	}
	private boolean isSubjectExist(long subjectId){
		boolean result = false;
		try {
			log.trace("Try get subject by subjectId=" + subjectId + " for exist checking");
			result = (Subject.getSubjectById(subjectId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DAOException e) {
			log.error("Cannot get subject", e);
		}		
		return result;
	}
	private boolean isClassroomExist(long classroomId){
		boolean result = false;
		try {
			log.trace("Try get classroom by classroomId=" + classroomId + " for exist checking");
			result = (Classroom.getClassroomById(classroomId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DAOException e) {
			log.error("Cannot get classroom", e);
		}		
		return result;
	}
	private boolean isLessonIntervalExist(long lessonIntervalId){
		boolean result = false;
		try {
			log.trace("Try get lessonInterval by lessonIntervalId=" + lessonIntervalId + " for exist checking");
			result = (LessonInterval.getLessonIntervalById(lessonIntervalId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DAOException e) {
			log.error("Cannot get lessonInterval", e);
		}		
		return result;
	}
	private boolean isScheduleItemExist(long scheduleItemId){
		boolean result = false;
		try {
			log.trace("Try get scheduleItem by scheduleItemId=" + scheduleItemId + " for exist checking");
			result = (ScheduleItem.getScheduleItemById(scheduleItemId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DAOException e) {
			log.error("Cannot get scheduleItem", e);
		}		
		return result;
	}
}