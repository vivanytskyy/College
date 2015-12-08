package com.gmail.ivanytskyy.vitaliy;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.gmail.ivanytskyy.vitaliy.dao.ClassroomDao;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.domain.Classroom;
public class ClassroomTest {
	static Classroom classroom;
	static ClassroomDao classroomDao;
	@BeforeClass
	public static void setUp(){
		DaoManager daoManager = DaoManager.getInstance();
		classroomDao = daoManager.getClassroomDao();
		try {
			classroom = classroomDao.createClassroom("Classroom #21");
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetName() {
		String result = classroom.getClassroomName();
		assertEquals("Classroom #21", result);
	}
	@Test
	public void testCreateClassroom(){
		Classroom classroom = null;
		try {
			classroom = Classroom.createClassroom("Underground classroom");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Classroom result = null;
		try {
			result = classroomDao.findClassroomById(classroom.getClassroomId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(classroom.getClassroomId(), result.getClassroomId());
		try {
			classroomDao.deleteClassroomById(classroom.getClassroomId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetClassroomById(){
		Classroom classroom = null;
		try {
			classroom = classroomDao.createClassroom("Get classroom test");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long classroomId = classroom.getClassroomId();
		Classroom result = null;
		try {
			result = Classroom.getClassroomById(classroomId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(classroomId, result.getClassroomId());
		try {
			classroomDao.deleteClassroomById(classroomId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetClassroomsByName(){
		String classroomName = "SpesialNameForClassroomIdentification192837465564738291#$&";
		Classroom classroom1 = null;
		Classroom classroom2 = null;
		try {
			classroom1 = classroomDao.createClassroom(classroomName);
			classroom2 = classroomDao.createClassroom(classroomName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long classroom1Id = classroom1.getClassroomId();
		long classroom2Id = classroom2.getClassroomId();		
		List<Classroom> result = new LinkedList<Classroom>();
		try {
			result = Classroom.getClassroomsByName(classroomName);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		long classroom1IdResult = result.get(0).getClassroomId();
		long classroom2IdResult = result.get(1).getClassroomId();
		assertEquals(classroom1Id, classroom1IdResult);
		assertEquals(classroom2Id, classroom2IdResult);
		try {
			classroomDao.deleteClassroomById(classroom1Id);
			classroomDao.deleteClassroomById(classroom2Id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetAllClassrooms(){		
		List<Classroom> result = new LinkedList<Classroom>();
		try {
			result = Classroom.getAllClassrooms();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Classroom> allClassrooms = new LinkedList<Classroom>();
		try {
			allClassrooms = classroomDao.findAllClassrooms();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(allClassrooms.size(), result.size());
		for (int i = 0; i < allClassrooms.size(); i++) {
			assertEquals(allClassrooms.get(i).getClassroomId(), result.get(i).getClassroomId());
		}
	}
	@Test
	public void testUpdateClassroom(){
		Classroom classroom = null;
		String initialClassroomName = "Penthouse classroom";
		String updatedClassroomName = "Middle level classroom";
		try {
			classroom = Classroom.createClassroom(initialClassroomName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Classroom result = null;
		try {
			result = classroomDao.findClassroomById(classroom.getClassroomId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(initialClassroomName, result.getClassroomName());		
		try {
			Classroom.updateClassroom(classroom.getClassroomId(), updatedClassroomName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			result = classroomDao.findClassroomById(classroom.getClassroomId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(updatedClassroomName, result.getClassroomName());
		try {
			classroomDao.deleteClassroomById(classroom.getClassroomId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRemoveClassroomById(){
		Classroom classroom = null;
		try {
			classroom = classroomDao.createClassroom("For classroom removing");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long classroomId = classroom.getClassroomId();
		try {
			Classroom.removeClassroomById(classroomId);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		Classroom result = null;
		try {
			result = classroomDao.findClassroomById(classroomId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertNull(result);
		try {
			classroomDao.deleteClassroomById(classroomId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@AfterClass
	public static void cleanUp(){
		try {
			classroomDao.deleteClassroomById(classroom.getClassroomId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}	
}