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
import com.gmail.ivanytskyy.vitaliy.dao.GroupDao;
import com.gmail.ivanytskyy.vitaliy.dao.StudentDao;
import com.gmail.ivanytskyy.vitaliy.domain.Group;
import com.gmail.ivanytskyy.vitaliy.domain.Student;
public class GroupTest {	
	static GroupDao groupDao;
	static Group group1;
	static Group group2;
	static StudentDao studentDao;
	@BeforeClass
	public static void setUp() {
		groupDao = DaoManager.getInstance().getGroupDao();
		try {
			group1 = groupDao.createGroup("Group 15");
			group2 = groupDao.createGroup("Group 16");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		studentDao = DaoManager.getInstance().getStudentDao();		
	}
	@Test
	public void testGetName() {
		String result = group1.getGroupName();
		assertEquals("Group 15", result);
	}
	@Test
	public void testCreateStudent() {
		Student student = null;
		try {
			student = group1.createStudent("Petrov");
		} catch (DAOException e2) {			
			e2.printStackTrace();
		}
		try {
			assertEquals(student, studentDao.findStudentById(student.getStudentId()));
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			studentDao.deleteStudentById(student.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
	@Test
	public void testMoveStudent() {
		Student student = null;
		try {
			student = group1.createStudent("Petrov");
		} catch (DAOException e2) {
			e2.printStackTrace();
		}
		
		Student studentOfGroup1 = null;
		try {
			studentOfGroup1 = studentDao.findStudentsByGroupId(group1.getGroupId()).get(0);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		assertEquals(student.getStudentId(), studentOfGroup1.getStudentId());		
		try {
			group2.moveStudent(student.getStudentId());
		} catch (DAOException e2) {
			e2.printStackTrace();
		}
		Student studentsOfGroup2 = null;
		try {
			studentsOfGroup2 = studentDao.findStudentsByGroupId(group2.getGroupId()).get(0);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		assertEquals(student.getStudentId(), studentsOfGroup2.getStudentId());
		
		try {
			assertEquals(0, group1.getStudents().size());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		
		try {
			studentDao.deleteStudentById(student.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
	@Test
	public void testSetStudents() {
		List<Student> students = new LinkedList<Student>();
		try {
			students.add(group1.createStudent("Teterev"));
			students.add(group1.createStudent("Sidorov"));
			students.add(group1.createStudent("Vasechkin"));
		} catch (DAOException e1) {
			e1.printStackTrace();
		}	
		
		List<Student> studentsOfGroup1 = new LinkedList<Student>();
		try {
			studentsOfGroup1 = group1.getStudents();
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		assertEquals(students.get(0).getStudentId(), studentsOfGroup1.get(0).getStudentId());
		assertEquals(students.get(1).getStudentId(), studentsOfGroup1.get(1).getStudentId());
		assertEquals(students.get(2).getStudentId(), studentsOfGroup1.get(2).getStudentId());
		
		try {
			group2.setStudents(students);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		List<Student> studentsOfGroup2 = new LinkedList<Student>();
		try {
			studentsOfGroup2 = group2.getStudents();
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		assertEquals(students.get(0).getStudentId(), studentsOfGroup2.get(0).getStudentId());
		assertEquals(students.get(1).getStudentId(), studentsOfGroup2.get(1).getStudentId());
		assertEquals(students.get(2).getStudentId(), studentsOfGroup2.get(2).getStudentId());
		
		try {
			studentsOfGroup1 = group1.getStudents();
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		assertEquals(0, studentsOfGroup1.size());
		
		try {
			for (Student student : students) {
				studentDao.deleteStudentById(student.getStudentId());
			}
			for (Student student : studentsOfGroup2) {
				studentDao.deleteStudentById(student.getStudentId());
			}			
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
	@Test
	public void testGetStudents() {
		Student student1 = null;
		Student student2 = null;
		Student student3 = null;
		try {
			student1 = group1.createStudent("Teterev");
			student2 = group1.createStudent("Sidorov");
			student3 = group1.createStudent("Vasechkin");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		
		List<Student> students = new LinkedList<Student>();
		try {
			students = group1.getStudents();
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		assertEquals(student1, students.get(0));
		assertEquals(student2, students.get(1));
		assertEquals(student3, students.get(2));
		try {
			studentDao.deleteStudentById(student1.getStudentId());
			studentDao.deleteStudentById(student2.getStudentId());
			studentDao.deleteStudentById(student3.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
	@Test
	public void testDeleteStudent() {
		Student student = null;
		try {
			student = group1.createStudent("Lepetov");
		} catch (DAOException e2) {
			e2.printStackTrace();
		}
		try {
			assertEquals(student, studentDao.findStudentById(student.getStudentId()));
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			studentDao.deleteStudentById(student.getStudentId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			assertEquals(null, studentDao.findStudentById(student.getStudentId()));
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCreateGroup(){
		Group group = null;
		try {
			group = Group.createGroup("GroupTest");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Group result = null;
		try {
			result = groupDao.findGroupById(group.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(group.getGroupId(), result.getGroupId());
		try {
			groupDao.deleteGroupById(group.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetGroupById(){
		Group group = null;
		try {
			group = groupDao.createGroup("GetGroup test");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long groupId = group.getGroupId();
		Group result = null;
		try {
			result = Group.getGroupById(groupId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(groupId, result.getGroupId());
		try {
			groupDao.deleteGroupById(groupId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetGroupsByName(){
		String groupName = "SpesialNameForGroupsIdentification192837465564738291#$&";
		Group group1 = null;
		Group group2 = null;
		try {
			group1 = groupDao.createGroup(groupName);
			group2 = groupDao.createGroup(groupName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long group1Id = group1.getGroupId();
		long group2Id = group2.getGroupId();		
		List<Group> result = new LinkedList<Group>();
		try {
			result = Group.getGroupsByName(groupName);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		long group1IdResult = result.get(0).getGroupId();
		long group2IdResult = result.get(1).getGroupId();
		assertEquals(group1Id, group1IdResult);
		assertEquals(group2Id, group2IdResult);
		try {
			groupDao.deleteGroupById(group1Id);
			groupDao.deleteGroupById(group2Id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetAllGroups(){		
		List<Group> result = new LinkedList<Group>();
		try {
			result = Group.getAllGroups();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Group> allGroups = new LinkedList<Group>();
		try {
			allGroups = groupDao.findAllGroups();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(allGroups.size(), result.size());
		for (int i = 0; i < allGroups.size(); i++) {
			assertEquals(allGroups.get(i).getGroupId(), result.get(i).getGroupId());
		}
	}
	@Test
	public void testUpdateGroup(){
		Group group = null;
		String initialGroupName = "Group name before";
		String updatedGroupName = "Group name after";
		try {
			group = Group.createGroup(initialGroupName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		Group result = null;
		try {
			result = groupDao.findGroupById(group.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(initialGroupName, result.getGroupName());		
		try {
			Group.updateGroup(group.getGroupId(), updatedGroupName);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		try {
			result = groupDao.findGroupById(group.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(updatedGroupName, result.getGroupName());
		try {
			groupDao.deleteGroupById(group.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRemoveGroupById(){
		Group group = null;
		try {
			group = groupDao.createGroup("For group removing");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		long groupId = group.getGroupId();
		try {
			Group.removeGroupById(groupId);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
		Group result = null;
		try {
			result = groupDao.findGroupById(groupId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertNull(result);
		try {
			groupDao.deleteGroupById(groupId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	@AfterClass
	public static void cleanUp() {
		try {
			groupDao.deleteGroupById(group1.getGroupId());
			groupDao.deleteGroupById(group2.getGroupId());
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}
}