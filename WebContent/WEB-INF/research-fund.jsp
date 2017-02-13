<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="databean.FundBean"%>
<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">
		<form class="form-horizontal" action="researchFunds.do" method="POST">
			<legend>Search fund by symbol</legend>
			<div class="form-group">
				<label class="col-md-1 control-label" for="symbol">Symbol</label>
				<div class="col-md-4">
					<input id="symbol" name="symbol" placeholder="Search by Symbol"
						class="form-control input-md" required="" type="text">
				</div>
				<div class="col-md-6">
					<input type="submit" name="searchFund" class="btn btn-primary"
						value="Search">
				    <a href="researchFunds.do" class="btn btn-primary" role="button">See All Funds</a>
					
				</div>
			</div>
		</form>
		<c:choose>
			<c:when test="${not empty fundList}">
				<table class="table table-striped">
					<thead>

						<th>Fund Name</th>
						<th>Fund Symbol</th>
						<th>Action</th>

						</tr>
					</thead>
					<c:forEach var="fund" items="${fundList}">
						<tr>

							<td>${fund.name}</td>
							<td>${fund.symbol}</td>
							<td><a href="fund-detail.do?symbol=${fund.symbol}">
									<button id="fundInfo" name="fundInfo" class="btn btn-info">Detail</button>
							</a> &nbsp <a href="order.do?symbol=${fund.symbol}">
									<button id="orderButton" name="orderButton"
										class="btn btn-info">Buy or Sell</button>
							</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<br>

				<div class="alert alert-info">No fund found.</div>
			</c:otherwise>
		</c:choose>
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
