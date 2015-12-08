<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ include file="menu.jsp" flush="true"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="../css/style.css">
	<title>Lecturer administration</title>
</head>
<body>
	<jsp:include page="menu.jsp" flush="true" /><br><br>
	<h3>Page for lecturer administration</h3>
	<p>1. Create new lecturer</p>
	<form action="lecturerResult" method="post">
		<table>
			<tr>
				<td>Lecturer name</td>
				<td><input type="text" name="lecturerName" value="${lecturer.lecturerName}"></td>
			</tr>
			<tr>
				<td>
					<input type="submit" name="action" value="Add">
				</td>
			</tr>
		</table>
	</form>
	<br>
	<p>2. Read lecturer</p>
	<form action="lecturerResult" method="post">
		<table>
			<tr>
				<td>Lecturer ID</td>
				<td>
					<input type="text" name="lecturerId" value="${lecturer.lecturerId}">
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" name="action" value="Select">
				</td>
			</tr>
		</table>
	</form>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Lecturer name</th>
		</tr>
		<c:if test="${resultLecturer != null}">
			<tr>
				<td><c:out value="${resultLecturer.lecturerId}"/></td>
				<td><c:out value="${resultLecturer.lecturerName}"/></td>
			</tr>
		</c:if>
	</table>
	<br>
	<p>3. Update lecturer</p>
	<form action="lecturerResult" method="post">
		<table>
			<tr>
				<td>Lecturer ID</td>
				<td>
					<input type="text" name="lecturerId" value="${lecturer.lecturerId}">
				</td>
			</tr>
			<tr>
				<td>New lecturer name</td>
				<td>
					<input type="text" name="lecturerName" value="${lecturer.lecturerName}">
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" name="action" value="Edit">
				</td>
			</tr>
		</table>
	</form>
	<br>
	<p>4. Delete lecturer</p>
	<form action="lecturerResult" method="post">
		<table>
			<tr>
				<td>Lecturer ID</td>
				<td>
					<input type="text" name="lecturerId" value="${lecturer.lecturerId}">
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" name="action" value="Delete">
				</td>
			</tr>
		</table>
	</form>
	<br>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Lecturer name</th>
		</tr>		
		<c:forEach items="${allLecturers}" var="teach">
			<tr>
				<td>${teach.lecturerId}</td>
				<td>${teach.lecturerName}</td>
			</tr>
		</c:forEach>
	</table>
	<a href="lecturerResult">Show all the lecturers</a>
</body>
</html>