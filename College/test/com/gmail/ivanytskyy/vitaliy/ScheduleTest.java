package com.gmail.ivanytskyy.vitaliy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.gmail.ivanytskyy.vitaliy.dao.ClassroomDao;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.GroupDao;
import com.gmail.ivanytskyy.vitaliy.dao.LecturerDao;
import com.gmail.ivanytskyy.vitaliy.dao.LessonIntervalDao;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleDao;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleItemDao;
import com.gmail.ivanytskyy.vitaliy.dao.SubjectDao;
import com.gmail.ivanytskyy.vitaliy.domain.Classroom;
import com.gmail.ivanytskyy.vitaliy.domain.Group;
import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
import com.gmail.ivanytskyy.vitaliy.domain.LessonInterval;
import com.gmail.ivanytskyy.vitaliy.domain.Schedule;
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;
import com.gmail.ivanytskyy.vitaliy.domain.Subject;
public class ScheduleTest {	
	static Schedule schedule1;
	static Schedule schedule2;
	static Group group1;
	static Group group2;
	static Classroom classroom1;
	static Classroom classroom2;
	static Lecturer lecturer1;
	static Lecturer lecturer2;
	static Subject subject1;
	static Subject subject2;
	static LessonInterval lessonInterval1;
	static LessonInterval lessonInterval2;
	static GroupDao groupDao = DaoManager.getInstance().getGroupDao();
	static ClassroomDao classroomDao = DaoManager.getInstance().getClassroomDao();
	static LecturerDao lecturerDao = DaoManager.getInstance().getLecturerDao();
	static SubjectDao subjectDao = DaoManager.getInstance().getSubjectDao();
	static LessonIntervalDao lessonIntervalDao = DaoManager.getInstance().getLessonIntervalDao();
	static ScheduleItemDao scheduleItemDao = DaoManager.getInstance().getScheduleItemDao();
	static ScheduleDao scheduleDao = DaoManager.getInstance().getScheduleDao();
	static Calendar date1;
	static Calendar date2;
	@BeforeClass
	public static void setUp() {
		date1 = new GregorianCalendar(2015, 10, 16);
		try {
			schedule1 = scheduleDao.createSchedule(date1);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		date2 = new GregorianCalendar(2015, 11, 12);
		try {
			schedule2 = scheduleDao.createSchedule(date2);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			group1 = groupDao.createGroup("Group 1");
			group2 = groupDao.createGroup("Group 2");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			classroom1 = classroomDao.createClassroom("Classroom #1");
			classroom2 = classroomDao.createClassroom("Classroom #2");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			lecturer1 = lecturerDao.createLecturer("Sonia Gold Hand");
			lecturer2 = lecturerDao.createLecturer("Ostap Bender");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			subject1 = subjectDao.createSubject("The Art of Robbery");
			subject2 = subjectDao.createSubject("Chess War");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			lessonInterval1 = lessonIntervalDao.createLessonInterval("9.00", "10.45");
			lessonInterval2 = lessonIntervalDao.createLessonInterval("11.00", "12.45");
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetScheduleDate() {
		Calendar scheduleDate1 = schedule1.getScheduleDate();
		String result1 = scheduleDate1.get(Calendar.DAY_OF_MONTH)
				+ "/" + scheduleDate1.get(Calendar.MONTH) 
				+ "/" + scheduleDate1.get(Calendar.YEAR);
		assertEquals("16/10/2015", result1);		
		Calendar scheduleDate2 = schedule2.getScheduleDate();
		String result2 = scheduleDate2.get(Calendar.DAY_OF_MONTH)
				+ "/" + scheduleDate2.get(Calendar.MONTH) 
				+ "/" + scheduleDate2.get(Calendar.YEAR);		
		assertEquals("12/11/2015", result2);		
	}
	@Test
	public void testCreateScheduleItem(){
		ScheduleItem scheduleItem = null;
		try {
			scheduleItem = schedule1.createScheduleItem(group1, lecturer2, classroom1, subject1, lessonInterval1);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			assertEquals(scheduleItem, scheduleItemDao.findScheduleItemById(scheduleItem.getScheduleItemId()));
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItem.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetScheduleItems() {
		ScheduleItem scheduleItem1 = null;
		ScheduleItem scheduleItem2 = null;
		ScheduleItem scheduleItem3 = null;
		ScheduleItem scheduleItem4 = null;
		try {
			scheduleItem1 = schedule2.createScheduleItem(group1, lecturer2, classroom1, subject1, lessonInterval1);
			scheduleItem2 = schedule2.createScheduleItem(group2, lecturer1, classroom2, subject2, lessonInterval1);
			scheduleItem3 = schedule2.createScheduleItem(group1, lecturer1, classroom2, subject2, lessonInterval2);
			scheduleItem4 = schedule2.createScheduleItem(group2, lecturer2, classroom1, subject1, lessonInterval2);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		List<ScheduleItem> result = new LinkedList<ScheduleItem>();
		try {
			result = schedule2.getScheduleItems();
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		assertEquals(group1.getGroupId(), result.get(0).getGroupId());
		assertEquals(group2.getGroupId(), result.get(1).getGroupId());
		assertEquals(group1.getGroupId(), result.get(2).getGroupId());
		assertEquals(group2.getGroupId(), result.get(3).getGroupId());
		assertEquals(lecturer2.getLecturerId(), result.get(0).getLecturerId());
		assertEquals(lecturer1.getLecturerId(), result.get(1).getLecturerId());
		assertEquals(lecturer1.getLecturerId(), result.get(2).getLecturerId());
		assertEquals(lecturer2.getLecturerId(), result.get(3).getLecturerId());
		assertEquals(classroom1.getClassroomId(), result.get(0).getClassroomId());
		assertEquals(classroom2.getClassroomId(), result.get(1).getClassroomId());
		assertEquals(classroom2.getClassroomId(), result.get(2).getClassroomId());
		assertEquals(classroom1.getClassroomId(), result.get(3).getClassroomId());
		assertEquals(subject1.getSubjectId(), result.get(0).getSubjectId());
		assertEquals(subject2.getSubjectId(), result.get(1).getSubjectId());
		assertEquals(subject2.getSubjectId(), result.get(2).getSubjectId());
		assertEquals(subject1.getSubjectId(), result.get(3).getSubjectId());
		assertEquals(lessonInterval1.getLessonIntervalId(), result.get(0).getLessonIntervalId());
		assertEquals(lessonInterval1.getLessonIntervalId(), result.get(1).getLessonIntervalId());
		assertEquals(lessonInterval2.getLessonIntervalId(), result.get(2).getLessonIntervalId());
		assertEquals(lessonInterval2.getLessonIntervalId(), result.get(3).getLessonIntervalId());
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItem1.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem2.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem3.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem4.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}	
	@Test
	public void testObtainScheduleItemsAsStringListByGroup(){
		ScheduleItem scheduleItem1 = null;
		ScheduleItem scheduleItem2 = null;
		ScheduleItem scheduleItem3 = null;
		ScheduleItem scheduleItem4 = null;
		try {
			scheduleItem1 = schedule1.createScheduleItem(group1, lecturer2, classroom1, subject1, lessonInterval1);
			scheduleItem2 = schedule1.createScheduleItem(group2, lecturer1, classroom2, subject2, lessonInterval1);
			scheduleItem3 = schedule1.createScheduleItem(group1, lecturer1, classroom2, subject2, lessonInterval2);
			scheduleItem4 = schedule1.createScheduleItem(group2, lecturer2, classroom1, subject1, lessonInterval2);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		List<String> result = new LinkedList<String>();
		try {
			result = schedule1.obtainScheduleItemsAsStringList(group1);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		for (String string : result) {
			sb.append(string);
		}
		assertEquals("16/11/2015Group 1The Art of RobberyOstap BenderClassroom #19.00 -10.45\n" 
				+ "16/11/2015Group 1Chess WarSonia Gold HandClassroom #211.00-12.45\n",
				sb.toString());
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItem1.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem2.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem3.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem4.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}	
	@Test
	public void testObtainScheduleItemsAsStringListByLecturer(){
		ScheduleItem scheduleItem1 = null;
		ScheduleItem scheduleItem2 = null;
		ScheduleItem scheduleItem3 = null;
		ScheduleItem scheduleItem4 = null;
		try {
			scheduleItem1 = schedule2.createScheduleItem(group1, lecturer2, classroom1, subject1, lessonInterval1);
			scheduleItem2 = schedule2.createScheduleItem(group2, lecturer1, classroom2, subject2, lessonInterval1);
			scheduleItem3 = schedule2.createScheduleItem(group1, lecturer1, classroom2, subject2, lessonInterval2);
			scheduleItem4 = schedule2.createScheduleItem(group2, lecturer2, classroom1, subject1, lessonInterval2);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		List<String> result = new LinkedList<String>();
		try {
			result = schedule2.obtainScheduleItemsAsStringList(lecturer2);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		for (String string : result) {
			sb.append(string);
		}
		assertEquals("12/12/2015Ostap BenderGroup 1The Art of RobberyClassroom #19.00 -10.45\n" 
				+ "12/12/2015Ostap BenderGroup 2The Art of RobberyClassroom #111.00-12.45\n",
				sb.toString());
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItem1.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem2.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem3.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem4.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
	@Test
	public void testObtainScheduleItemsAsStringListByClassroom(){
		ScheduleItem scheduleItem1 = null;
		ScheduleItem scheduleItem2 = null;
		ScheduleItem scheduleItem3 = null;
		ScheduleItem scheduleItem4 = null;
		try {
			scheduleItem1 = schedule1.createScheduleItem(group1, lecturer2, classroom1, subject1, lessonInterval1);
			scheduleItem2 = schedule1.createScheduleItem(group2, lecturer1, classroom2, subject2, lessonInterval1);
			scheduleItem3 = schedule1.createScheduleItem(group1, lecturer1, classroom2, subject2, lessonInterval2);
			scheduleItem4 = schedule1.createScheduleItem(group2, lecturer2, classroom1, subject1, lessonInterval2);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}				
		List<String> result = new LinkedList<String>();
		try {
			result = schedule1.obtainScheduleItemsAsStringList(classroom1);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		for (String string : result) {
			sb.append(string);
		}
		assertEquals("16/11/2015Classroom #1Group 1The Art of RobberyOstap Bender9.00 -10.45\n" 
				+ "16/11/2015Classroom #1Group 2The Art of RobberyOstap Bender11.00-12.45\n",
				sb.toString());
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItem1.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem2.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem3.getScheduleItemId());
			scheduleItemDao.deleteScheduleItemById(scheduleItem4.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
	@Test
	public void testCreateSchedule(){
		Schedule schedule = null;
		try {
			schedule = Schedule.createSchedule(date1);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Schedule result = null;
		try {
			result = scheduleDao.findScheduleById(schedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(schedule.getScheduleId(), result.getScheduleId());
		try {
			scheduleDao.deleteScheduleById(schedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetScheduleById(){
		Schedule schedule = null;
		try {
			schedule = scheduleDao.createSchedule(date1);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long scheduleId = schedule.getScheduleId();
		Schedule result = null;
		try {
			result = Schedule.getScheduleById(scheduleId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(scheduleId, result.getScheduleId());
		try {
			scheduleDao.deleteScheduleById(scheduleId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetSchedulesByDate(){
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(1991, 1, 29);
		Schedule schedule1 = null;
		Schedule schedule2 = null;
		try {
			schedule1 = scheduleDao.createSchedule(currentDate);
			schedule2 = scheduleDao.createSchedule(currentDate);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long schedule1Id = schedule1.getScheduleId();
		long schedule2Id = schedule2.getScheduleId();		
		List<Schedule> result = new LinkedList<Schedule>();
		try {
			result = Schedule.getSchedulesByDate(currentDate);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		long schedule1IdResult = result.get(0).getScheduleId();
		long schedule2IdResult = result.get(1).getScheduleId();
		assertEquals(schedule1Id, schedule1IdResult);
		assertEquals(schedule2Id, schedule2IdResult);
		try {
			scheduleDao.deleteScheduleById(schedule1Id);
			scheduleDao.deleteScheduleById(schedule2Id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetAllSchedules(){		
		List<Schedule> result = new LinkedList<Schedule>();
		try {
			result = Schedule.getAllSchedules();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Schedule> allSchedules = new LinkedList<Schedule>();
		try {
			allSchedules = scheduleDao.findAllSchedules();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(allSchedules.size(), result.size());
		for (int i = 0; i < allSchedules.size(); i++) {
			assertEquals(allSchedules.get(i).getScheduleId(), result.get(i).getScheduleId());
		}
	}
	@Test
	public void testUpdateSchedule(){
		Calendar initialDate = new GregorianCalendar();
		initialDate.set(1997, 1, 29);
		String initialDateAsStr = initialDate.get(Calendar.DAY_OF_MONTH)
				+ "/" + (initialDate.get(Calendar.MONTH) + 1) 
				+ "/" + initialDate.get(Calendar.YEAR);
		Calendar updatedDate = new GregorianCalendar();
		updatedDate.set(1998, 2, 18);
		String updatedDateAsStr = updatedDate.get(Calendar.DAY_OF_MONTH)
				+ "/" + (updatedDate.get(Calendar.MONTH) + 1) 
				+ "/" + updatedDate.get(Calendar.YEAR);
		Schedule schedule = null;
		try {
			schedule = Schedule.createSchedule(initialDate);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Schedule result = null;
		try {
			result = scheduleDao.findScheduleById(schedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(initialDateAsStr, result.getScheduleDateAsStr());		
		try {
			Schedule.updateSchedule(schedule.getScheduleId(), updatedDate);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			result = scheduleDao.findScheduleById(schedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(updatedDateAsStr, result.getScheduleDateAsStr());
		try {
			scheduleDao.deleteScheduleById(schedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRemoveScheduleById(){
		Schedule schedule = null;
		try {
			schedule = scheduleDao.createSchedule(date1);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long scheduleId = schedule.getScheduleId();
		try {
			Schedule.removeScheduleById(scheduleId);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		Schedule result = null;
		try {
			result = scheduleDao.findScheduleById(scheduleId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertNull(result);
		try {
			scheduleDao.deleteScheduleById(scheduleId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@AfterClass
	public static void cleanUp(){		
		try {
			groupDao.deleteGroupById(group1.getGroupId());
			groupDao.deleteGroupById(group2.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			classroomDao.deleteClassroomById(classroom1.getClassroomId());
			classroomDao.deleteClassroomById(classroom2.getClassroomId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			lecturerDao.deleteLecturerById(lecturer1.getLecturerId());
			lecturerDao.deleteLecturerById(lecturer2.getLecturerId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			subjectDao.deleteSubjectById(subject1.getSubjectId());
			subjectDao.deleteSubjectById(subject2.getSubjectId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			lessonIntervalDao.deleteLessonIntervalById(lessonInterval1.getLessonIntervalId());
			lessonIntervalDao.deleteLessonIntervalById(lessonInterval2.getLessonIntervalId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			scheduleDao.deleteScheduleById(schedule1.getScheduleId());
			scheduleDao.deleteScheduleById(schedule2.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
}