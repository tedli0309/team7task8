<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="template-top.jsp" />

<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">
		<div class="row">

			<h2>Customer Portfolio</h2>
			<!--  <form action="account.do" method="POST">-->
			<table class="table table-striped">

				<tr>
					<th>Name</th>
					<td colspan="5">${customer.toString()}</td>
				</tr>
				<tr>
					<th>Address</th>
					<td colspan="2">${customer.toAddressString()}</td>
				</tr>
				<tr>
					<th>Cash Balance</th>
					<td colspan="2"><fmt:setLocale value="en_US" /> <fmt:formatNumber
							type="currency" maxFractionDigits="2" minFractionDigits="2"
							value="${customer.cash}" /> <a href="requestCheck.do">
							<button id="requestCheck" name="requestButton"
								class="btn btn-info">Request Check</button>
					</a></td>
				</tr>
				<tr>
					<th>Available Cash Balance</th>
					<td colspan="2"><fmt:setLocale value="en_US" /> <fmt:formatNumber
							type="currency" maxFractionDigits="2" minFractionDigits="2"
							value="${availableCash}" />
					</td>
				</tr>
				<tr>
					<th>Last Transaction Date</th>
					<td colspan="2">${latestTransaction}</td>
				</tr>


			</table>
			<!--  </form>-->
		</div>


		<div class="row">
			<h2>Funds Owned</h2>
			<c:choose>
				<c:when test="${not empty fundList}">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Fund Symbol
								<th>Fund Name</th>
								<th style="text-align: right"># Shares</th>
								<th style="text-align: right">Amount</th>
								<th style="text-align: center">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="fund" items="${fundList}">
								<tr>
									<td>${fund.symbol}</td>
									<td>${fund.name}</td>
									<td style="text-align: right"><fmt:formatNumber type="number" maxFractionDigits="3"
											minFractionDigits="3" value="${fund.shares}" /></td>
									<td style="text-align: right"><fmt:setLocale value="en_US" /> <fmt:formatNumber
											type="currency" maxFractionDigits="2" minFractionDigits="2"
											value="${fund.toAmount()}" /></td>
									<td style="text-align: center"><a href="order.do?symbol=${fund.symbol} ">
											<button id="orderButton" name="orderButton"
												class="btn btn-info">Buy or Sell</button>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					</br>
					<div class="alert alert-info">No fund found.</div>
		</div>
	</div>
	</c:otherwise>
	</c:choose>


</div>

<jsp:include page="template-bottom.jsp" />