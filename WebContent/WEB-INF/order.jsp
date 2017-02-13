<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">

		<form class="form-horizontal" action="order.do" method="POST">
			<fieldset>

				<!-- Form Name -->
				<legend>Buy or Sell Funds</legend>
				<h4>
					Available Cash Balance:
					<fmt:setLocale value="en_US" />
					<fmt:formatNumber type="currency" maxFractionDigits="2"
						minFractionDigits="2" value="${availableCash}" />
				</h4>

				<!-- Select Basic -->
				<div class="form-group">
					<label class="col-md-1 control-label" for="selectb">Action</label>
					<div class="col-md-4">
						<select id="selectb" name="selectb" class="form-control"
							onchange="changeText()">
							<option value="buy">Buy</option>
							<option value="sell">Sell</option>
						</select>
					</div>
				</div>

				<!-- Text input-->
				<div class="form-group">
					<label class="col-md-1 control-label" id="amountType">Amount</label>
					<div class="col-md-4">
						<input id="orderAmount" name="orderAmount"
							placeholder="Enter Amount" class="form-control input-md"
							required="" type="text">
					</div>
				</div>
				<script>
					function changeText() {
						var x = document.getElementById("selectb").value;
						if (document.getElementById("selectb").value == "sell") {
							document.getElementById("amountType").innerHTML = "# of shares";
							document.getElementById("orderAmount").placeholder = "Enter number of shares";
						} else {
							document.getElementById("amountType").innerHTML = "Amount";
							document.getElementById("orderAmount").placeholder = "Enter amount";
						}
					}
				</script>

				<!-- Text input-->
				<div class="form-group">
					<label class="col-md-1 control-label" for="Symbol">Symbol</label>
					<div class="col-md-4">
						<c:if test="${param.symbol != null}">
							<input id="orderSymbol" name="orderSymbol" placeholder="Symbol"
								class="form-control input-md"
								value="<c:out value="${param.symbol}"/>" required="" type="text">
						</c:if>
						<c:if test="${param.symbol == null}">
							<input id="orderSymbol" name="orderSymbol" placeholder="Symbol"
								class="form-control input-md" value="" required="" type="text">
						</c:if>

					</div>
				</div>

				<!--  Button -->
				<div class="form-group">
					<div class="col-md-2"></div>
					<div class="col-md-4">
						<input type="submit" id="confirmOrder" name="confirmOrder"
							class="btn btn-primary" value="confirm">
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
