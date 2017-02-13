<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="template-top.jsp" />

<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">
		<div class="row">
			<h2>Employee Page</h2>
			<table class="table table-striped">
				<tr>
					<th>Name:</th>
					<td colspan="5">${employee.userName}</td>
				</tr>

			</table>
		</div>


		<div class="row">
			<h2>Customer List</h2>
			<form class="form-horizontal" action="employeeAccount.do"
				method="POST">
				<legend>Search Customer by UserName</legend>
				<div class="form-group">
					<%-- <label class="col-md-1 control-label" for="CustomerName">Enter Customer Name</label>  --%>
					<div class="col-md-4">
						<input id="customerName" name="customerName"
							placeholder="Search by customer username"
							class="form-control input-md" required="" type="text">
					</div>
					<div class="col-md-6">
						<input type="submit" name="searchCustomer" class="btn btn-primary"
							value="Search">
						<a href="employeeAccount.do" class="btn btn-primary" role="button">See All Customers</a>
					</div>
				</div>
			</form>
		</div>

		<div class="row">
			<c:choose>
				<c:when test="${not empty customerList}">
					<table class="table table-striped">
						<thead>
							<tr>
								<!--  <td>Customer Id</td>-->
								<th>Customer Name</th>
								<th>UserName</th>
								<th>Action</th>

							</tr>
						</thead>
						<c:forEach var="customer" items="${customerList}">
							<tr>
								<!-- <td>${customer.userId}</td> -->
								<td>${customer.toString()}</td>
								<td>${customer.userName}</td>
								
								<td><a href="viewCustomer.do?username=${customer.userName}">
										<button id="viewCustomer" name="viewCustomer"
											class="btn btn-info">View Detail</button>
								</a> <a href="depositCheck.do?username=${customer.userName}">
										<button id="depositCheck" name="depositCheck"
											class="btn btn-info">Deposit Check</button>
								</a> <a href="reset-pwd.do?userName=${customer.userName}">
										<button id="resetPwd" name="resetPwd" class="btn btn-info">Reset
											Password</button>
								</a ></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					</br>
					<div class="alert alert-info">No Customer found.</div>
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