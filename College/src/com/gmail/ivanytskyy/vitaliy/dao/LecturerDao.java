package com.gmail.ivanytskyy.vitaliy.dao;
import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * LecturerDao interface
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public interface LecturerDao {
	public Lecturer createLecturer(String lecturerName) throws DAOException;
	public Lecturer findLecturerById(long lecturerId) throws DAOException;
	public List<Lecturer> findLecturersByName(String lecturerName) throws DAOException;
	public List<Lecturer> findAllLecturers() throws DAOException;
	public void updateLecturer(long lecturerId, String newLecturerName) throws DAOException;
	public void deleteLecturerById(long lecturerId) throws DAOException;
	public void deleteAllLecturers() throws DAOException;
}