package com.gmail.ivanytskyy.vitaliy.dao;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * DaoManager class
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public final class DaoManager {
	private static final Logger log = Logger.getLogger(DaoManager.class);
	private static DaoManager instance = new DaoManager();
	private ClassroomDao classroomDao;
	private GroupDao groupDao;
	private LecturerDao lecturerDao;
	private LessonIntervalDao lessonIntervalDao;
	private ScheduleDao scheduleDao;
	private ScheduleItemDao scheduleItemDao;
	private StudentDao studentDao;
	private SubjectDao subjectDao;	
	private DaoManager(){
		log.info("Creating context object");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		log.info("Obtain ClassroomDao object by context");
		classroomDao = (JdbcClassroomDao) context.getBean("classroomDao");
		log.info("Obtain GroupDao object by context");
		groupDao = (JdbcGroupDao) context.getBean("groupDao");
		log.info("Obtain LecturerDao object by context");
		lecturerDao = (JdbcLecturerDao) context.getBean("lecturerDao");
		log.info("Obtain LessonIntervalDao object by context");
		lessonIntervalDao = (JdbcLessonIntervalDao) context.getBean("lessonIntervalDao");
		log.info("Obtain ScheduleDao object by context");
		scheduleDao = (JdbcScheduleDao) context.getBean("scheduleDao");
		log.info("Obtain ScheduleItemDao object by context");
		scheduleItemDao = (JdbcScheduleItemDao) context.getBean("scheduleItemDao");
		log.info("Obtain StudentDao object by context");
		studentDao = (JdbcStudentDao) context.getBean("studentDao");
		log.info("Obtain SubjectDao object by context");
		subjectDao = (JdbcSubjectDao) context.getBean("subjectDao");
		log.info("Close context");
		((ConfigurableApplicationContext) context).close();
	}
	public static DaoManager getInstance(){
		log.info("Return DaoManager instance");
		return instance;
	}	
	public ClassroomDao getClassroomDao() {
		log.info("Return ClassroomDao object");
		return classroomDao;
	}
	public GroupDao getGroupDao() {
		log.info("Return GroupDao object");
		return groupDao;
	}
	public LecturerDao getLecturerDao() {
		log.info("Return LecturerDao object");
		return lecturerDao;
	}
	public LessonIntervalDao getLessonIntervalDao() {
		log.info("Return LessonIntervalDao object");
		return lessonIntervalDao;
	}
	public ScheduleDao getScheduleDao() {
		log.info("Return ScheduleDao object");
		return scheduleDao;
	}
	public ScheduleItemDao getScheduleItemDao() {
		log.info("Return ScheduleItemDao object");
		return scheduleItemDao;
	}
	public StudentDao getStudentDao() {
		log.info("Return StudentDao object");
		return studentDao;
	}
	public SubjectDao getSubjectDao(){
		log.info("Return SubjectDao object");
		return subjectDao;		
	}
}