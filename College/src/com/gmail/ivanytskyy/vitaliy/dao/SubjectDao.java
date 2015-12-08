package com.gmail.ivanytskyy.vitaliy.dao;
import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.Subject;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * SubjectDao interface
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public interface SubjectDao {
	public Subject createSubject(String subjectName) throws DAOException;
	public Subject findSubjectById(long subjectId) throws DAOException;
	public List<Subject> findSubjectsByName(String subjectName) throws DAOException;
	public List<Subject> findAllSubjects() throws DAOException;
	public void updateSubject(long subjectId, String newSubjectName) throws DAOException;
	public void deleteSubjectById(long subjectId) throws DAOException;
	public void deleteAllSubjects() throws DAOException;
}