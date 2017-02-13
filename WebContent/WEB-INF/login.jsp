<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="template-top.jsp" />
<html>
  <%--   <head>
        <title>Login for FavoriteList</title>
    </head>
    
	<body>
	--%>
	<div class="row">
		<form class="form-horizontal" action="login.do" method="POST">
		    <div class="col-md-6">
  			<div class="form-group">
    			<label for="userName" class="col-sm-2 control-label">UserName:</label>
					<div class="col-sm-6">
    				<input type="text" class="form-control input-md" id="userName" name="userName" placeholder="UserName" required="">
					</div>
  			</div>
				<br>
  			<div class="form-group">
    			<label for="Password" class="col-sm-2 control-label">Password:</label>
					<div class="col-sm-6">
    				<input type="password" class="form-control input-md" id="password" name="password" placeholder="Password" required="">
					</div>
  			</div>
				<br>
				<div class="form-group">
    			<div class="col-sm-offset-3 col-sm-10">
      			 <input type="submit" name="action" value="Login" class="btn btn-primary" style="width:180px;height:35px"/>
    			</div>
    			</div>
    			<div class="form-group">
    			<div class="col-sm-offset-3 col-sm-10">
    			<input type="submit" name="action" value="Login as Employee" class="btn btn-primary" style="width:180px;height:35px"/>
    			</div>
  			</div>
			</div>
		</form>
		
		<div class="row" style="margin-left: 10px;">
			<c:forEach var="error" items="${errors}">
				<div class="alert alert-danger col-md-8">
					<strong>Error</strong> ${error}
				</div>
			</c:forEach>
		</div>

		<c:choose>
			<c:when test="${not empty message}">
				<div class="row" style="margin-left: 10px;">
					<div class="alert alert-success col-md-8">${message}</div>
				</div>
			</c:when>
		</c:choose>
		</div>

<jsp:include page="template-bottom.jsp" />