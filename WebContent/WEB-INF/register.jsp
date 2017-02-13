<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="template-top.jsp" />
<%-- 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register For FavoriteList!</title>
</head>
<body>
--%>
		<h2> Register</h2>
		
		<c:forEach var="error" items="${errors}">
			<h3 style="color:red"> ${error} </h3>
		</c:forEach>
	
		<form action = "register.do" method = "POST">
			<table>
				<tr>
					<td style = "font-size: x-large"> Email :</td>
					<td>
						<input type = "text" name = "userName" value = "${form.email}"/>
					</td>
				</tr>
				<tr>
					<td style = "font-size: x-large"> First Name :</td>
					<td>
						<input type = "text" name = "firstName" value = "${form.firstName}" />
					</td>
				</tr>
				<tr>
					<td style = "font-size: x-large"> Last Name :</td>
					<td>
						<input type = "text" name = "lastName" value = "${form.lastName}" />
					</td>
				</tr>
				<tr>
					<td style = "font-size: x-large"> Password :</td>
					<td>
						<input type = "password" name = "password" value = "${form.firstName}" />
					</td>
				</tr>
				<tr>
		            <td colspan="2" align="center">
		                <input type="submit" name="action" value="Register" />
		            </td>
		        </tr>
			</table>
		</form>
		
<jsp:include page="template-bottom.jsp" />