package com.gmail.ivanytskyy.vitaliy.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.DAOException;
import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
/*
 * Task #2/2015/12/08 (pet web project #2)
 * LecturerServlet
 * @version 1.01 2015.12.08
 * @author Vitaliy Ivanytskyy
 */
public class LecturerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(LecturerServlet.class);
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String lecturerIdStr = request.getParameter("lecturerId");		
		String lecturerName = request.getParameter("lecturerName");
		Lecturer lecturer = null;
		if(action != null 
				&& action.equalsIgnoreCase("Add") 
				&& lecturerName != null 
				&& !lecturerName.equals("")
				&& !lecturerName.trim().equals("")){
			try {
				log.trace("Try create lecturer with lecturerName=" + lecturerName);
				lecturer = Lecturer.createLecturer(lecturerName.trim());
				log.trace("Lecturer with lecturerName=" + lecturerName + " was created");
			} catch (DAOException e) {
				log.error("Cannot create lecturer", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)) {
			long lecturerId = Long.valueOf(lecturerIdStr);
			try {
				log.trace("Try find lecturer with lecturerId = " + lecturerId);
				request.setAttribute("resultLecturer", Lecturer.getLecturerById(lecturerId));
				log.trace("Lecturer with lecturerId=" + lecturerId + " was found");
			} catch (DAOException e) {
				log.error("Cannot find lecturer", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")
				&& lecturerName != null 
				&& !lecturerName.equals("")
				&& !lecturerName.trim().equals("")
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)) {
			long lecturerId = Long.valueOf(lecturerIdStr);
			try {
				log.trace("Try update lecturer with lecturerId = " + lecturerId
						+ " by new lecturerName = " + lecturerName);
				Lecturer.updateLecturer(lecturerId, lecturerName.trim());
				log.trace("Lecturer with lecturerId = " + lecturerId + " was updated by new lecturerName = " + lecturerName);
			} catch (DAOException e) {
				log.error("Cannot update lecturer", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Delete") 
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)) {
			long lecturerId = Long.valueOf(lecturerIdStr);	
			try {
				log.trace("Try delete lecturer with lecturerId=" + lecturerId);
				Lecturer.removeLecturerById(lecturerId);
				log.trace("Lecturer with lecturerId=" + lecturerId + " was deleted");
			} catch (DAOException e) {
				log.error("Cannot delete lecturer", e);
			}
		}		
		request.setAttribute("lecturer", lecturer);
		try {
			log.trace("Try get all lecturers for putting to request");
			request.setAttribute("allLecturers", Lecturer.getAllLecturers());
			log.trace("Lecturers were gotten");
		} catch (DAOException e) {
			log.error("Cannot get all lecturers", e);
		}
		request.getRequestDispatcher("/jsp/adminLecturer.jsp").forward(request, response);
	}
}