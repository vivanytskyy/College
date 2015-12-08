package com.gmail.ivanytskyy.vitaliy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
public class ScheduleItemTest {
	static Group group;
	static Classroom classroom;
	static Lecturer lecturer;
	static Subject subject;
	static LessonInterval lessonInterval;
	static ScheduleItem scheduleItem;
	static Schedule schedule;
	static GroupDao groupDao = DaoManager.getInstance().getGroupDao();
	static ClassroomDao classroomDao = DaoManager.getInstance().getClassroomDao();
	static LecturerDao lecturerDao = DaoManager.getInstance().getLecturerDao();
	static SubjectDao subjectDao = DaoManager.getInstance().getSubjectDao();
	static LessonIntervalDao lessonIntervalDao = DaoManager.getInstance().getLessonIntervalDao();
	static ScheduleItemDao scheduleItemDao = DaoManager.getInstance().getScheduleItemDao();
	static ScheduleDao scheduleDao = DaoManager.getInstance().getScheduleDao();
	@BeforeClass
	public static void setUp(){		
		try {
			group = groupDao.createGroup("Group 1");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			classroom = classroomDao.createClassroom("Classroom #1");
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			lecturer = lecturerDao.createLecturer("Donald Ervin Knuth");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			subject = subjectDao.createSubject("Sport life");
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			lessonInterval = lessonIntervalDao.createLessonInterval("9.00", "10.45");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(2015, 10, 14);
		try {
			schedule = scheduleDao.createSchedule(currentDate);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			scheduleItem = scheduleItemDao.createScheduleItem(group.getGroupId(), lecturer.getLecturerId(),
					classroom.getClassroomId(), subject.getSubjectId(), lessonInterval.getLessonIntervalId(), schedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetGroupId() {
		long result = scheduleItem.getGroupId();
		assertEquals(group.getGroupId(), result);
	}
	@Test
	public void testGetLecturerId() {
		long result = scheduleItem.getLecturerId();
		assertEquals(lecturer.getLecturerId(), result);
	}
	@Test
	public void testGetClassroomId() {
		long result = scheduleItem.getClassroomId();
		assertEquals(classroom.getClassroomId(), result);
	}
	@Test
	public void testGetSubjectId() {
		long result = scheduleItem.getSubjectId();
		assertEquals(subject.getSubjectId(), result);
	}
	@Test
	public void testGetLessonIntervalId() {
		long result = scheduleItem.getLessonIntervalId();
		assertEquals(lessonInterval.getLessonIntervalId(), result);
	}
	public void testCreateScheduleItem(){
		ScheduleItem scheduleItem = null;
		try {
			scheduleItem = ScheduleItem.createScheduleItem(1, 2, 3, 4, 5, schedule.getScheduleId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		ScheduleItem result = null;
		try {
			result = scheduleItemDao.findScheduleItemById(scheduleItem.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(scheduleItem.getScheduleItemId(), result.getScheduleItemId());
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItem.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetScheduleItemById(){
		ScheduleItem scheduleItem = null;
		try {
			scheduleItem = scheduleItemDao.createScheduleItem(6, 7, 8, 9, 10, schedule.getScheduleId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long scheduleItemId = scheduleItem.getScheduleItemId();
		ScheduleItem result = null;
		try {
			result = ScheduleItem.getScheduleItemById(scheduleItemId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(scheduleItemId, result.getScheduleItemId());
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItemId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetScheduleItemsByScheduleId(){
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(1998, 12, 10);
		Schedule currentSchedule = null;
		try {
			currentSchedule = scheduleDao.createSchedule(currentDate);
		} catch (DAOException e2) {
			e2.printStackTrace();
		}
		long numberOfScheduleItems = 2;
		ScheduleItem scheduleItem1 = null;
		ScheduleItem scheduleItem2 = null;
		try {
			scheduleItem1 = scheduleItemDao.createScheduleItem(1, 3, 5, 7, 9, currentSchedule.getScheduleId());
			scheduleItem2 = scheduleItemDao.createScheduleItem(9, 7, 5, 3, 1, currentSchedule.getScheduleId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long scheduleItem1Id = scheduleItem1.getScheduleItemId();
		long scheduleItem2Id = scheduleItem2.getScheduleItemId();		
		List<ScheduleItem> result = new LinkedList<ScheduleItem>();
		try {
			result = ScheduleItem.getScheduleItemsByScheduleId(currentSchedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(numberOfScheduleItems, result.size());
		long scheduleItem1IdResult = result.get(0).getScheduleItemId();
		long scheduleItem2IdResult = result.get(1).getScheduleItemId();
		assertEquals(scheduleItem1Id, scheduleItem1IdResult);
		assertEquals(scheduleItem2Id, scheduleItem2IdResult);
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItem1Id);
			scheduleItemDao.deleteScheduleItemById(scheduleItem2Id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			scheduleDao.deleteScheduleById(currentSchedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetAllScheduleItems(){		
		List<ScheduleItem> result = new LinkedList<ScheduleItem>();
		try {
			result = ScheduleItem.getAllScheduleItems();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<ScheduleItem> allScheduleItems = new LinkedList<ScheduleItem>();
		try {
			allScheduleItems = scheduleItemDao.findAllScheduleItems();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(allScheduleItems.size(), result.size());
		for (int i = 0; i < allScheduleItems.size(); i++) {
			assertEquals(allScheduleItems.get(i).getScheduleItemId(), result.get(i).getScheduleItemId());
		}
	}
	@Test
	public void testMoveScheduleItemToAnotherSchedule(){
		Calendar firstDate = new GregorianCalendar();
		firstDate.set(1995, 11, 15);
		Calendar secondDate = new GregorianCalendar();
		secondDate.set(1997, 10, 14);
		Schedule currentSchedule1 = null;
		Schedule currentSchedule2 = null;
		try {
			currentSchedule1 = scheduleDao.createSchedule(firstDate);
			currentSchedule2 = scheduleDao.createSchedule(secondDate);
		} catch (DAOException e2) {
			e2.printStackTrace();
		}
		ScheduleItem scheduleItem = null;
		try {
			scheduleItem = scheduleItemDao.createScheduleItem(11, 22, 33, 44, 55,
					currentSchedule1.getScheduleId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long scheduleItemId = scheduleItem.getScheduleItemId();
		long scheduleIdIntoScheduleItemBeforeMoving = scheduleItem.getScheduleId();
		assertEquals(currentSchedule1.getScheduleId(), scheduleIdIntoScheduleItemBeforeMoving);
		try {
			ScheduleItem.moveScheduleItemToAnotherSchedule(scheduleItemId, currentSchedule2.getScheduleId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			scheduleItem = scheduleItemDao.findScheduleItemById(scheduleItemId);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long scheduleIdIntoScheduleItemAfterMoving = scheduleItem.getScheduleId();
		assertNotEquals(scheduleIdIntoScheduleItemBeforeMoving, scheduleIdIntoScheduleItemAfterMoving);
		assertEquals(currentSchedule2.getScheduleId(), scheduleIdIntoScheduleItemAfterMoving);
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItemId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			scheduleDao.deleteScheduleById(currentSchedule1.getScheduleId());
			scheduleDao.deleteScheduleById(currentSchedule2.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testUpdateScheduleItem(){		
		long initialGroupId = 22;
		long initialLecturerId = 23;
		long initialClassroomId = 24;
		long initialSubjectId = 25;
		long initialLessonIntervalId = 26;
		long initialScheduleId = schedule.getScheduleId();
		Calendar newDate = new GregorianCalendar();
		newDate.set(1996, 12, 11);
		Schedule newSchedule = null;
		try {
			newSchedule = scheduleDao.createSchedule(newDate);
		} catch (DAOException e2) {
			e2.printStackTrace();
		}
		long newGroupId = 32;
		long newLecturerId = 33;
		long newClassroomId = 34;
		long newSubjectId = 35;
		long newLessonIntervalId = 36;
		long newScheduleId = newSchedule.getScheduleId();
		ScheduleItem scheduleItem = null;
		try {
			scheduleItem = ScheduleItem.createScheduleItem(
					initialGroupId,	initialLecturerId,
					initialClassroomId,	initialSubjectId,
					initialLessonIntervalId, initialScheduleId);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		ScheduleItem result = null;
		try {
			result = scheduleItemDao.findScheduleItemById(scheduleItem.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(initialGroupId, result.getGroupId());
		assertEquals(initialLecturerId, result.getLecturerId());
		assertEquals(initialClassroomId, result.getClassroomId());
		assertEquals(initialSubjectId, result.getSubjectId());
		assertEquals(initialLessonIntervalId, result.getLessonIntervalId());
		assertEquals(initialScheduleId, result.getScheduleId());
		try {
			ScheduleItem.updateScheduleItem(scheduleItem.getScheduleItemId(),
					newGroupId, newLecturerId,
					newClassroomId, newSubjectId,
					newLessonIntervalId, newScheduleId);;
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			result = scheduleItemDao.findScheduleItemById(scheduleItem.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(newGroupId, result.getGroupId());
		assertEquals(newLecturerId, result.getLecturerId());
		assertEquals(newClassroomId, result.getClassroomId());
		assertEquals(newSubjectId, result.getSubjectId());
		assertEquals(newLessonIntervalId, result.getLessonIntervalId());
		assertEquals(newScheduleId, result.getScheduleId());
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItem.getScheduleItemId());
			scheduleDao.deleteScheduleById(newSchedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRemoveScheduleItemById(){
		ScheduleItem scheduleItem = null;
		try {
			scheduleItem = scheduleItemDao.createScheduleItem(2, 4, 6, 8, 10, schedule.getScheduleId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long scheduleItemId = scheduleItem.getScheduleItemId();
		try {
			ScheduleItem.removeScheduleItemById(scheduleItemId);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		ScheduleItem result = null;
		try {
			result = scheduleItemDao.findScheduleItemById(scheduleItemId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertNull(result);
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItemId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@AfterClass
	public static void cleanUp(){		
		try {
			groupDao.deleteGroupById(group.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			classroomDao.deleteClassroomById(classroom.getClassroomId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			lecturerDao.deleteLecturerById(lecturer.getLecturerId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			subjectDao.deleteSubjectById(subject.getSubjectId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			lessonIntervalDao.deleteLessonIntervalById(lessonInterval.getLessonIntervalId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			scheduleItemDao.deleteScheduleItemById(scheduleItem.getScheduleItemId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			scheduleDao.deleteScheduleById(schedule.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}