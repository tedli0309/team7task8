<jsp:include page="template-top.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!--<%@ page import="databean.TransactionBean" %>-->
<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">
		<div class="row">
			<legend>Transaction History</legend>
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
											<td style="text-align: right"><fmt:setLocale value="en_US" /> <fmt:formatNumber
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