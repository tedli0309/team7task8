<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-top.jsp" />

<p>
<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">
		<form class="form-horizontal" method="POST" action="reset-pwd.do">
			<legend>Reset Password for <c:out value="${curUserName}"/> </legend>
			<div class="col-md-6">
				<div class="form-group">
					<label for="password" class="col-sm-4 control-label" style="text-align:left">Password</label>
					<div class="col-sm-6">
						<input type="password" class="form-control input-md" id="password"
							name="password" placeholder="Enter Password" required="">
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
						<input type="submit" name="action" value="Reset Password"
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

</p>

<jsp:include page="template-bottom.jsp" />
