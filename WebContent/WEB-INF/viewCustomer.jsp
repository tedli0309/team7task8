<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">
		<%--Customer Portfolio --%>
		<div class="row">
			<h2>Customer Portfolio</h2>
			<form action="account.do" method="POST">
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
								value="${customer.cash}" /></td>
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
			</form>
		</div>

		<%--Customer Funds Owned --%>
		<div class="row">
			<h2>Funds Owned</h2>
			<c:choose>
				<c:when test="${not empty fundList}">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Fund Symbol</th>
								<th>Fund Name</th>
								<th style="text-align: right"># Shares</th>
								<th style="text-align: right">Amount</th>
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
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					<div class="alert alert-info">No fund found.</div>
				</c:otherwise>
			</c:choose>
		</div>

		<%--Customer Transaction History --%>
		<div class="row">
			<h2>Transaction History</h2>
			<c:choose>
				<c:when test="${not empty transactionList}">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Date</th>
								<th>Operation</th>
								<th>Fund Name</th>
								<th style="text-align: right"># Shares</th>
								<th style="text-align: right">Share Price</th>
								<th style="text-align: right">Amount</th>

							</tr>
						</thead>
						<c:forEach var="transaction" items="${transactionList}">
							<tr>
								<c:if test="${empty transaction.executeDate}">
									<td>Pending</td>
								</c:if>
								
								<c:choose>
									<c:when test="${empty transaction.executeDate}">
										<c:if test="${transaction.transactionType == 'request'}">
											<td>${transaction.transactionType}</td>
											<td></td>
											<td></td>
											<td></td>
											<td style="text-align: right"><fmt:setLocale value="en_US" /> <fmt:formatNumber
													type="currency" maxFractionDigits="2" minFractionDigits="2"
													value="${transaction.amount}" /></td>

										</c:if>
										<c:if test="${transaction.transactionType == 'deposit'}">
										    <td>${transaction.transactionType}</td>
											<td></td>
											<td></td>
											<td></td>
											<td><fmt:setLocale value="en_US" /> <fmt:formatNumber
													type="currency" maxFractionDigits="2" minFractionDigits="2"
													value="${transaction.amount}" /></td>
										</c:if>
										<c:if test="${transaction.transactionType == 'sell'}">
											<td>${transaction.transactionType}</td>
											<td>${transaction.name}</td>
											<td style="text-align: right"><fmt:formatNumber type="number"
													maxFractionDigits="3" minFractionDigits="3"
													value="${transaction.shares}" /></td>
											<td></td>
											<td></td>

										</c:if>
										<c:if test="${transaction.transactionType == 'buy'}">
											<td>${transaction.transactionType}</td>
											<td>${transaction.name}</td>
											<td></td>
											<td></td>
											<td style="text-align: right"><fmt:setLocale value="en_US" /> <fmt:formatNumber
													type="currency" maxFractionDigits="2" minFractionDigits="2"
													value="${transaction.amount}" /></td>

										</c:if>
									</c:when>
									<c:otherwise>
									    <td>${transaction.executeDate}</td>
									    <td>${transaction.transactionType}</td>
									    <c:choose>
									    <c:when test="${(transaction.transactionType == 'deposit') or (transaction.transactionType == 'request')}">
									    <td></td>
										<td></td>
										<td></td>
										<td style="text-align: right"><fmt:setLocale value="en_US" /> <fmt:formatNumber
													type="currency" maxFractionDigits="2" minFractionDigits="2"
													value="${transaction.amount}" /></td>
									    </c:when>
									    
									    <c:otherwise>
									    <td>${transaction.name}</td>
										<td style="text-align: right"><fmt:formatNumber type="number" maxFractionDigits="3"
												minFractionDigits="3" value="${transaction.shares}" /></td>
										<td style="text-align: right"><fmt:setLocale value="en_US" /> <fmt:formatNumber
												type="currency" maxFractionDigits="2" minFractionDigits="2"
												value="${transaction.toPrice()}" /></td>
										<td style="text-align: right"><fmt:setLocale value="en_US" /> <fmt:formatNumber
												type="currency" maxFractionDigits="2" minFractionDigits="2"
												value="${transaction.amount}" /></td>
									 	</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>



							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					
					<div class="alert alert-info">No transaction found.</div>
				</c:otherwise>
			</c:choose>
		</div>
		<%--Error Message --%>
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