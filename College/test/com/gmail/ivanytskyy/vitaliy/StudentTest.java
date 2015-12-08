package com.gmail.ivanytskyy.vitaliy;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.GroupDao;
import com.gmail.ivanytskyy.vitaliy.dao.StudentDao;
import com.gmail.ivanytskyy.vitaliy.domain.Group;
import com.gmail.ivanytskyy.vitaliy.domain.Student;
public class StudentTest {
	static Student student;
	static StudentDao studentDao;
	static Group group;
	static GroupDao groupDao;
	@BeforeClass
	public static void setUp() {
		groupDao = DaoManager.getInstance().getGroupDao();
		try {
			group = groupDao.createGroup("Group ##22");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		studentDao = DaoManager.getInstance().getStudentDao();
		try {
			student = studentDao.createStudent("Petrov", group.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetName() {
		String result = student.getStudentName();
		assertEquals("Petrov", result);
	}
	@Test
	public void testCreateStudent(){
		Student student = null;
		try {
			student = Student.createStudent("StudentTest", group.getGroupId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Student result = null;
		try {
			result = studentDao.findStudentById(student.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(student.getStudentId(), result.getStudentId());
		try {
			studentDao.deleteStudentById(student.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetStudentById(){
		Student student = null;
		try {
			student = studentDao.createStudent("GetStudent test", group.getGroupId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long studentId = student.getStudentId();
		Student result = null;
		try {
			result = Student.getStudentById(studentId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(studentId, result.getStudentId());
		try {
			studentDao.deleteStudentById(studentId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetStudentsByName(){
		String studentName = "SpesialNameForStudentIdentification192837465564738291#$&";
		Student student1 = null;
		Student student2 = null;
		try {
			student1 = studentDao.createStudent(studentName, group.getGroupId());
			student2 = studentDao.createStudent(studentName, group.getGroupId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long student1Id = student1.getStudentId();
		long student2Id = student2.getStudentId();		
		List<Student> result = new LinkedList<Student>();
		try {
			result = Student.getStudentsByName(studentName);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		long student1IdResult = result.get(0).getStudentId();
		long student2IdResult = result.get(1).getStudentId();
		assertEquals(student1Id, student1IdResult);
		assertEquals(student2Id, student2IdResult);
		try {
			studentDao.deleteStudentById(student1Id);
			studentDao.deleteStudentById(student2Id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetStudentsByGroupId(){
		Group currentGroup = null;
		try {
			currentGroup = groupDao.createGroup("SpesialNameForGroupAndStudentIdentification192837465564738291#$&");
		} catch (DAOException e2) {
			e2.printStackTrace();
		}
		long numberOfStudents = 2;
		Student student1 = null;
		Student student2 = null;
		try {
			student1 = studentDao.createStudent("student1", currentGroup.getGroupId());
			student2 = studentDao.createStudent("student2", currentGroup.getGroupId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long student1Id = student1.getStudentId();
		long student2Id = student2.getStudentId();		
		List<Student> result = new LinkedList<Student>();
		try {
			result = Student.getStudentsByGroupId(currentGroup.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(numberOfStudents, result.size());
		long student1IdResult = result.get(0).getStudentId();
		long student2IdResult = result.get(1).getStudentId();
		assertEquals(student1Id, student1IdResult);
		assertEquals(student2Id, student2IdResult);
		try {
			studentDao.deleteStudentById(student1Id);
			studentDao.deleteStudentById(student2Id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			groupDao.deleteGroupById(currentGroup.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetAllStudents(){		
		List<Student> result = new LinkedList<Student>();
		try {
			result = Student.getAllStudents();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Student> allStudents = new LinkedList<Student>();
		try {
			allStudents = studentDao.findAllStudents();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(allStudents.size(), result.size());
		for (int i = 0; i < allStudents.size(); i++) {
			assertEquals(allStudents.get(i).getStudentId(), result.get(i).getStudentId());
		}
	}
	@Test
	public void testMoveStudentToAnotherGroup(){
		Group currentGroup1 = null;
		Group currentGroup2 = null;
		try {
			currentGroup1 = groupDao.createGroup("SpesialNameForGroup1Identification192837465564738291#$&toMoveStudent");
			currentGroup2 = groupDao.createGroup("SpesialNameForGroup2Identification192837465564738291#$&toMoveStudent");
		} catch (DAOException e2) {
			e2.printStackTrace();
		}
		Student student = null;
		try {
			student = studentDao.createStudent("studentTest", currentGroup1.getGroupId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long studentId = student.getStudentId();
		long groupIdIntoStudentBeforeMoving = student.getGroupId();
		assertEquals(currentGroup1.getGroupId(), groupIdIntoStudentBeforeMoving);
		try {
			Student.moveStudentToAnotherGroup(studentId, currentGroup2.getGroupId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			student = studentDao.findStudentById(studentId);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long groupIdIntoStudentAfterMoving = student.getGroupId();
		assertNotEquals(groupIdIntoStudentBeforeMoving, groupIdIntoStudentAfterMoving);
		assertEquals(currentGroup2.getGroupId(), groupIdIntoStudentAfterMoving);
		try {
			studentDao.deleteStudentById(studentId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			groupDao.deleteGroupById(currentGroup1.getGroupId());
			groupDao.deleteGroupById(currentGroup2.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testUpdateStudent(){
		Student student = null;
		String initialStudentName = "Student before marriage";
		String updatedStudentName = "Student after marriage";
		try {
			student = Student.createStudent(initialStudentName, group.getGroupId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Student result = null;
		try {
			result = studentDao.findStudentById(student.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(initialStudentName, result.getStudentName());		
		try {
			Student.updateStudent(student.getStudentId(), updatedStudentName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			result = studentDao.findStudentById(student.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(updatedStudentName, result.getStudentName());
		try {
			studentDao.deleteStudentById(student.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRemoveStudentById(){
		Student student = null;
		try {
			student = studentDao.createStudent("For student removing", group.getGroupId());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long studentId = student.getStudentId();
		try {
			Student.removeStudentById(studentId);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		Student result = null;
		try {
			result = studentDao.findStudentById(studentId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertNull(result);
		try {
			studentDao.deleteStudentById(studentId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@AfterClass
	public static void cleanUp(){
		try {
			studentDao.deleteStudentById(student.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			groupDao.deleteGroupById(group.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}