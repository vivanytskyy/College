package com.gmail.ivanytskyy.vitaliy.dao;
import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.LessonInterval;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * LessonIntervalDao interface
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public interface LessonIntervalDao {
	public LessonInterval createLessonInterval(String lessonStart, String lessonFinish) throws DAOException;
	public LessonInterval findLessonIntervalById(long lessonIntervalId) throws DAOException;
	public List<LessonInterval> findLessonIntervalsByLessonStart(String lessonIntervalStart) throws DAOException;
	public List<LessonInterval> findLessonIntervalsByLessonFinish(String lessonIntervalFinish) throws DAOException;
	public List<LessonInterval> findAllLessonIntervals() throws DAOException;
	public void updateLessonInterval(long lessonIntervalId, String newLessonStart, String newLessonFinish) throws DAOException;
	public void deleteLessonIntervalById(long lessonIntervalId) throws DAOException;
	public void deleteAllLessonIntervals() throws DAOException;
}