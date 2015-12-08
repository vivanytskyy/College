package com.gmail.ivanytskyy.vitaliy.dao;
import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.Student;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * StudentDao interface
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public interface StudentDao {
	public Student createStudent(String studentName, long groupId) throws DAOException;
	public Student findStudentById(long studentId) throws DAOException;
	public List<Student> findStudentsByGroupId(long groupId) throws DAOException;
	public List<Student> findStudentsByName(String studentName) throws DAOException;
	public List<Student> findAllStudents() throws DAOException;
	public void moveStudentToAnotherGroup(long studentId, long groupId) throws DAOException;
	public void updateStudent(long studentId, String newStudentName) throws DAOException;
	public void deleteStudentById(long studentId) throws DAOException;
	public void deleteAllStudents() throws DAOException;
}