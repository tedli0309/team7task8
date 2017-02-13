<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="template-top.jsp" />
<script src="http://momentjs.com/downloads/moment-with-locales.js"></script>
<script src="http://momentjs.com/downloads/moment-timezone-with-data.js"></script>


<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">
		<div class="row">
			<legend>Pick a transition date</legend>
			<br/>
			<div>
			<c:set var="string" value= "${lastDay}"/>
			<c:set var="datePart" value="${fn:split(string, '-')}" />
			Last transition date: ${datePart[1]}/${datePart[2]}/${datePart[0]}
			</div>
			<form class="form-horizontal col-md-6" action="transitionDay.do"
				method="post">
				<div class="form-group transition-date">
					<label class="control-label col-md-2" for="transition-date">Date</label>
					<div class="input-group transition-date-time">
						<span class="input-group-addon" id="basic-addon1"><span
							class="glyphicon glyphicon-calendar" aria-hidden="true"></span></span> <input
							class="form-control" name="date" id="transition-date" type="date" required="">
					</div>
				</div>
				<div class="row">
					<div style="margin-left: 70px;">
						<input type="submit" id="confirmTransition"
							name="confirmTransition" class="btn btn-primary" value="confirm">
					</div>
				</div>
		</div>


		<div class="row">
			<c:choose>
				<c:when test="${not empty fundList}">
					<table class="table table-striped">
						<thead>
							<tr>
								<!-- <td>Fund Id</td> -->
								<th>Fund Name</th>
								<th>Fund Symbol</th>
								<th>Closing Price</th>

							</tr>
						</thead>
						<c:forEach var="fund" items="${fundList}">
							<tr>

								<!-- <td>${fund.fundId}</td> -->
								<td>${fund.name}</td>
								<td>${fund.symbol}</td>
								<td><input style="text-align: right" type="text" name="${fund.fundId}" value = "${fund.price}" required="" /></td>

							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					</br>
					<div class="alert alert-info">No fund found.</div>
				</c:otherwise>
			</c:choose>
			</div>
		
			<div class="row" style="margin-left: 10px;">
			<c:if test = "${not empty errors}">
					<div class="alert alert-danger col-md-8">
						<strong>Error</strong> ${errors[0]}
					</div>
			</c:if>
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