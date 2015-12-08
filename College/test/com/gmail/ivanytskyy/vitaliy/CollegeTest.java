package com.gmail.ivanytskyy.vitaliy;
import static org.junit.Assert.assertEquals;
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
import com.gmail.ivanytskyy.vitaliy.domain.College;
public class CollegeTest {
	static College college;
	static Schedule schedule1;
	static Schedule schedule2;
	static Group group1;
	static Group group2;
	static Classroom classroom1;
	static Classroom classroom2;
	static Classroom classroom3;
	static Lecturer lecturer1;
	static Lecturer lecturer2;
	static Lecturer lecturer3;
	static Subject subject1;
	static Subject subject2;
	static Subject subject3;
	static LessonInterval lessonInterval1;
	static LessonInterval lessonInterval2;
	static LessonInterval lessonInterval3;
	static List<ScheduleItem> scheduleItems1 = new LinkedList<ScheduleItem>();
	static List<ScheduleItem> scheduleItems2 = new LinkedList<ScheduleItem>();
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
		college = new College("Test College");
		try {
			group1 = groupDao.createGroup("Group 1");
			group2 = groupDao.createGroup("Group 2");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			classroom1 = classroomDao.createClassroom("Classroom #1");
			classroom2 = classroomDao.createClassroom("Classroom #2");
			classroom3 = classroomDao.createClassroom("Classroom #3");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			lecturer1 = lecturerDao.createLecturer("Donald Ervin Knuth");
			lecturer2 = lecturerDao.createLecturer("Herbert Schildt");
			lecturer3 = lecturerDao.createLecturer("Cay Horstmann");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			subject1 = subjectDao.createSubject("The Art of Computer Programming");
			subject2 = subjectDao.createSubject("C++ Course");
			subject3 = subjectDao.createSubject("Java Course");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			lessonInterval1 = lessonIntervalDao.createLessonInterval("9.00", "10.45");
			lessonInterval2 = lessonIntervalDao.createLessonInterval("11.00", "12.45");
			lessonInterval3 = lessonIntervalDao.createLessonInterval("13.00", "14.45");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		date1 = new GregorianCalendar(2015, 9, 23);
		try {
			schedule1 = scheduleDao.createSchedule(date1);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			scheduleItems1.add(schedule1.createScheduleItem(group1, lecturer2, classroom3, subject3, lessonInterval1));
			scheduleItems1.add(schedule1.createScheduleItem(group2, lecturer3, classroom2, subject2, lessonInterval1));
			scheduleItems1.add(schedule1.createScheduleItem(group1, lecturer1, classroom1, subject1, lessonInterval2));
			scheduleItems1.add(schedule1.createScheduleItem(group2, lecturer3, classroom2, subject3, lessonInterval2));
			scheduleItems1.add(schedule1.createScheduleItem(group1, lecturer3, classroom3, subject2, lessonInterval3));
			scheduleItems1.add(schedule1.createScheduleItem(group2, lecturer1, classroom1, subject1, lessonInterval3));
		} catch (DAOException e1) {
			e1.printStackTrace();
		}				
		date2 = new GregorianCalendar(2015, 9, 24);
		try {
			schedule2 = scheduleDao.createSchedule(date2);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			scheduleItems2.add(schedule2.createScheduleItem(group1, lecturer3, classroom2, subject2, lessonInterval1));
			scheduleItems2.add(schedule2.createScheduleItem(group2, lecturer2, classroom3, subject3, lessonInterval1));
			scheduleItems2.add(schedule2.createScheduleItem(group1, lecturer1, classroom1, subject1, lessonInterval2));
			scheduleItems2.add(schedule2.createScheduleItem(group2, lecturer3, classroom2, subject3, lessonInterval2));
			scheduleItems2.add(schedule2.createScheduleItem(group1, lecturer1, classroom1, subject1, lessonInterval3));
			scheduleItems2.add(schedule2.createScheduleItem(group2, lecturer3, classroom3, subject2, lessonInterval3));
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
	@Test
	public void testGetName() {
		String result = college.getCollegeName();
		assertEquals("Test College", result);
	}
	@Test
	public void testObtainScheduleByGroup() {
		String result = college.obtainSchedule(group1, date1);
		assertEquals("23/10/2015|Group 1|Java Course|Herbert Schildt|Classroom #3|9.00 -10.45|\n" 
				+ "23/10/2015|Group 1|The Art of Computer Programming|Donald Ervin Knuth|Classroom #1|11.00-12.45|\n" 
				+ "23/10/2015|Group 1|C++ Course|Cay Horstmann|Classroom #3|13.00-14.45|\n",
				result);	
	}	
	@Test
	public void testObtainScheduleByLecturer() {
		String result = college.obtainSchedule(lecturer3, date2);
		assertEquals("24/10/2015|Cay Horstmann|Group 1|C++ Course|Classroom #2|9.00 -10.45|\n" 
				+ "24/10/2015|Cay Horstmann|Group 2|Java Course|Classroom #2|11.00-12.45|\n" 
				+ "24/10/2015|Cay Horstmann|Group 2|C++ Course|Classroom #3|13.00-14.45|\n",
				result);	
	}	
	@Test
	public void testObtainScheduleByClassroom() {
		String result = college.obtainSchedule(classroom2, date1);
		assertEquals("23/10/2015|Classroom #2|Group 2|C++ Course|Cay Horstmann|9.00 -10.45|\n" 
				+ "23/10/2015|Classroom #2|Group 2|Java Course|Cay Horstmann|11.00-12.45|\n",
				result);	
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
			classroomDao.deleteClassroomById(classroom3.getClassroomId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			lecturerDao.deleteLecturerById(lecturer1.getLecturerId());
			lecturerDao.deleteLecturerById(lecturer2.getLecturerId());
			lecturerDao.deleteLecturerById(lecturer3.getLecturerId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			subjectDao.deleteSubjectById(subject1.getSubjectId());
			subjectDao.deleteSubjectById(subject2.getSubjectId());
			subjectDao.deleteSubjectById(subject3.getSubjectId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		try {
			lessonIntervalDao.deleteLessonIntervalById(lessonInterval1.getLessonIntervalId());
			lessonIntervalDao.deleteLessonIntervalById(lessonInterval2.getLessonIntervalId());
			lessonIntervalDao.deleteLessonIntervalById(lessonInterval3.getLessonIntervalId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		for (ScheduleItem scheduleItem : scheduleItems1) {
			try {
				scheduleItemDao.deleteScheduleItemById(scheduleItem.getScheduleItemId());
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		for (ScheduleItem scheduleItem : scheduleItems2) {
			try {
				scheduleItemDao.deleteScheduleItemById(scheduleItem.getScheduleItemId());
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		try {
			scheduleDao.deleteScheduleById(schedule1.getScheduleId());
			scheduleDao.deleteScheduleById(schedule2.getScheduleId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
}