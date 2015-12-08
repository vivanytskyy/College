package com.gmail.ivanytskyy.vitaliy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.LinkedList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.LecturerDao;
import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
public class LecturerTest {
	static Lecturer lecturer;
	static LecturerDao lecturerDao;
	@BeforeClass
	public static void setUp(){
		lecturerDao = DaoManager.getInstance().getLecturerDao();
		try {
			lecturer = lecturerDao.createLecturer("Sidorov");
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetName() {
		String result = lecturer.getLecturerName();
		assertEquals("Sidorov", result);
	}
	@Test
	public void testCreateLecturer(){
		Lecturer lecturer = null;
		try {
			lecturer = Lecturer.createLecturer("LecturerTest");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Lecturer result = null;
		try {
			result = lecturerDao.findLecturerById(lecturer.getLecturerId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(lecturer.getLecturerId(), result.getLecturerId());
		try {
			lecturerDao.deleteLecturerById(lecturer.getLecturerId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetLecturerById(){
		Lecturer lecturer = null;
		try {
			lecturer = lecturerDao.createLecturer("GetLecturer test");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long lecturerId = lecturer.getLecturerId();
		Lecturer result = null;
		try {
			result = Lecturer.getLecturerById(lecturerId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(lecturerId, result.getLecturerId());
		try {
			lecturerDao.deleteLecturerById(lecturerId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetLecturersByName(){
		String lecturerName = "SpesialNameForLecturerIdentification192837465564738291#$&";
		Lecturer lecturer1 = null;
		Lecturer lecturer2 = null;
		try {
			lecturer1 = lecturerDao.createLecturer(lecturerName);
			lecturer2 = lecturerDao.createLecturer(lecturerName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long lecturer1Id = lecturer1.getLecturerId();
		long lecturer2Id = lecturer2.getLecturerId();		
		List<Lecturer> result = new LinkedList<Lecturer>();
		try {
			result = Lecturer.getLecturersByName(lecturerName);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		long lecturer1IdResult = result.get(0).getLecturerId();
		long lecturer2IdResult = result.get(1).getLecturerId();
		assertEquals(lecturer1Id, lecturer1IdResult);
		assertEquals(lecturer2Id, lecturer2IdResult);
		try {
			lecturerDao.deleteLecturerById(lecturer1Id);
			lecturerDao.deleteLecturerById(lecturer2Id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetAllLecturers(){		
		List<Lecturer> result = new LinkedList<Lecturer>();
		try {
			result = Lecturer.getAllLecturers();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Lecturer> allLecturers = new LinkedList<Lecturer>();
		try {
			allLecturers = lecturerDao.findAllLecturers();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(allLecturers.size(), result.size());
		for (int i = 0; i < allLecturers.size(); i++) {
			assertEquals(allLecturers.get(i).getLecturerId(), result.get(i).getLecturerId());
		}
	}
	@Test
	public void testUpdateLecturer(){
		Lecturer lecturer = null;
		String initialLecturerName = "Initial lecturer";
		String updatedLecturerName = "Renamed lecturer";
		try {
			lecturer = Lecturer.createLecturer(initialLecturerName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Lecturer result = null;
		try {
			result = lecturerDao.findLecturerById(lecturer.getLecturerId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(initialLecturerName, result.getLecturerName());		
		try {
			Lecturer.updateLecturer(lecturer.getLecturerId(), updatedLecturerName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			result = lecturerDao.findLecturerById(lecturer.getLecturerId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(updatedLecturerName, result.getLecturerName());
		try {
			lecturerDao.deleteLecturerById(lecturer.getLecturerId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRemoveLecturerById(){
		Lecturer lecturer = null;
		try {
			lecturer = lecturerDao.createLecturer("For lecturer removing");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long lecturerId = lecturer.getLecturerId();
		try {
			Lecturer.removeLecturerById(lecturerId);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		Lecturer result = null;
		try {
			result = lecturerDao.findLecturerById(lecturerId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertNull(result);
		try {
			lecturerDao.deleteLecturerById(lecturerId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@AfterClass
	public static void cleanUp(){
		try {
			lecturerDao.deleteLecturerById(lecturer.getLecturerId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}