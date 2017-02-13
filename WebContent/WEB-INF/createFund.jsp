<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-top.jsp" />


<div class = "row">
<div class="col-md-8" style = "margin-left:10px;">

<form class="form-horizontal" action="createFund.do"
	method="POST">
	<fieldset>
	<legend>Create Fund</legend>
	
	<div class="col-md-6">
	<div class="form-group">
			<label for="fundName" class="col-md-3 control-label" style="text-align:left">FundName</label>
			<div class="col-md-6">
				<input type="text" class="form-control input-md" id="fundName"
					name="fundName" placeholder="Enter FundName"
					required="">
			</div>
		</div>
		<div class="form-group">
			<label for="fundSymbol" class="col-sm-3 control-label" style="text-align:left">FundSymbol</label>
			<div class="col-sm-6">
				<input type="text" class="form-control input-md" id="fundSymbol"
					name="fundSymbol" placeholder="Enter FundSymbol"
					required="">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-3 col-sm-10">
				<input type="submit" name="action" value="Create Fund"
					class="btn btn-primary" style="width: 180px; height: 35px" />
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