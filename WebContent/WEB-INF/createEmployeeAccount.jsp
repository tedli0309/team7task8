<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-top.jsp" />

<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">

		<form class="form-horizontal" action="createEmployeeAccount.do"
			method="POST">
			<fieldset>
				<legend>Create Employee Account</legend>

				<div class="col-md-6">
					<div class="form-group">
						<label for="userName" class="col-sm-4 control-label" style="text-align:left">UserName</label>
						<div class="col-sm-6">
							<input type="text" class="form-control input-md" id="userName"
								name="userName" placeholder="Enter UserName" value="${form.userName}"
								required="">
						</div>
					</div>

					<div class="form-group">
						<label for="firstName" class="col-sm-4 control-label" style="text-align:left">FirstName</label>
						<div class="col-sm-6">
							<input type="text" class="form-control input-md" id="firstName"
								name="firstName" placeholder="Enter FirstName"
								value="${form.firstName}" required="">
						</div>
					</div>

					<div class="form-group">
						<label for="lastName" class="col-sm-4 control-label" style="text-align:left">LastName</label>
						<div class="col-sm-6">
							<input type="text" class="form-control input-md" id="lastName"
								name="lastName" placeholder="Enter LastName" value="${form.lastName}"
								required="">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-4 control-label" style="text-align:left">Password</label>
						<div class="col-sm-6">
							<input type="password" class="form-control input-md"
								id="password" name="password" placeholder="Enter Password" required="">
						</div>
					</div>

					<div class="form-group">
						<label for="confirmPassword" class="col-sm-4 control-label" style="text-align:left">Confirm
							Password</label>
						<div class="col-sm-6">
							<input type="password" class="form-control input-md"
								id="confirmPassword" name="confirmPassword"
								placeholder="Enter ConfirmPassword" required="">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-10">
							<input type="submit" name="action"
								value="Create Employee Account" class="btn btn-primary"
								style="width: 180px; height: 35px" />
						</div>
					</div>
				</div>
			</fieldset>
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