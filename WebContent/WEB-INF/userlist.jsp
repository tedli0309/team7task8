<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="template-top.jsp" />

<p>
<table>
	<c:forEach var="u" items="${userList}">
		<span class="menu-item"> <a
			href="userlist.do?userName=${u.userName}"> ${u.firstName}
				${u.lastName} </a>
		</span>
		<br />
	</c:forEach>
</table>
</p>

<jsp:include page="template-bottom.jsp" />
