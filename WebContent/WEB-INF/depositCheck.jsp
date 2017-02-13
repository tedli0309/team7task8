<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-top.jsp" />
<html>

<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">
		<form class="form-horizontal" action="depositCheck.do" method="POST">
				<legend>Deposit Check</legend>

			<div class="col-md-6">
				<div class="form-group">
					<label for="userName" class="col-sm-4 control-label" style="text-align:left">UserName</label>
					<div class="col-sm-6">
						<input type="text" class="form-control input-md" id="userName"
							name="userName" placeholder="Type customer's username"
							value="<c:out value="${param.username}"/>" required="">
					</div>
				</div>
				<div class="form-group">
					<label for="checkAmount" class="col-sm-4 control-label" style="text-align:left">Check
						Amount</label>
					<div class="col-sm-6">
						<input type="text" class="form-control input-md" id="checkAmount"
							name="checkAmount" placeholder="Enter CheckAmount" required="">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-4 col-sm-10">
						<input type="submit" name="action" value="Deposit"
							class="btn btn-primary" style="width: 180px; height: 35px" />
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
	
</div>


<jsp:include page="template-bottom.jsp" />