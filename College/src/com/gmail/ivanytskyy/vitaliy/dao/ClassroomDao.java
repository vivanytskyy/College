package com.gmail.ivanytskyy.vitaliy.dao;
import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.Classroom;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * ClassroomDao interface
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public interface ClassroomDao {
	public Classroom createClassroom(String classroomName) throws DAOException;
	public Classroom findClassroomById(long classroomId) throws DAOException;
	public List<Classroom> findClassroomsByName(String classroomName) throws DAOException;
	public List<Classroom> findAllClassrooms() throws DAOException;
	public void updateClassroom(long classroomId, String newClassroomName) throws DAOException;
	public void deleteClassroomById(long classroomId) throws DAOException;
	public void deleteAllClassrooms() throws DAOException;
}