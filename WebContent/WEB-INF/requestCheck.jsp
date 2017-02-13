<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="template-top.jsp" />
<html>
<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">

		<form class="form-horizontal" action="requestCheck.do" method="POST">

			<legend>Request Check</legend>

			<h4>
				Available Cash Balance:
				<fmt:setLocale value="en_US" />
				<fmt:formatNumber type="currency" maxFractionDigits="2"
					minFractionDigits="2" value="${availableCash}" />
			</h4>

			<div class="form-group">
				<label class="col-md-3 control-label" for="Amount">Request
					Check Amount</label>
				<div class="col-md-3">
					<input id="checkAmount" name="checkAmount"
						placeholder="Type Amount" class="form-control input-md"
						required="" type="text">
				</div>
				<div class="col-md-2">
					<input type="submit" name="requestCheck" class="btn btn-primary"
						value="Request">
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