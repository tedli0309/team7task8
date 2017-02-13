<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<link href="http://cdn.oesmith.co.uk/morris-0.4.3.min.css" rel="stylesheet" />
<script src="http://cdn.oesmith.co.uk/morris-0.5.0.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>

<div class="row">
	<div class="col-md-8" style="margin-left: 10px;">
		<div class= "row">
		<h2>Fund Info</h2>
		<table class="table table-striped">
			<tr>
				<th style="font-size: large">Name</th>
				<td colspan="2">${fund.name}</td>
			</tr>
			<tr>
				<th style="font-size: large">Symbol</th>
				<td colspan="2">${fund.symbol}</td>
			</tr>
		</table>
		</div>
		<c:if test="${not empty history}">
		 <div class="row">
		 <h2>Fund Price History Line Chart</h2>
        <div id="line-example" style="height: 500px;"></div>
        </div>
        </c:if>

<script>

$.getScript('http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js',function(){
	$.getScript('http://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.0/morris.min.js',function(){
		 var arrayMy = [];
		 <c:forEach items="${history}" var="hist" varStatus="loop">
         arrayMy.push({'Date':'${hist.pricedate}','value':${hist.price}})        
     	</c:forEach>
		var myarray = arrayMy

	  Morris.Line({
	        element: 'line-example',
	        data: myarray,
	        xkey: 'Date',
	        ykeys: ['value'],
	        labels: ['Price']
	      });
	  
	});
	});

</script>
		
		
		
		<div class = "row">
		<h2>Fund Price History</h2>
		<c:choose>
			<c:when test="${not empty history}">
				<table class="table table-striped">
					<thead>
						<tr>
							<td>Price Date</td>
							<td>Price</td>

						</tr>
					</thead>
					<c:forEach var="hist" items="${history}">
						<tr>
							<td>${hist.pricedate}</td>
							<td><fmt:setLocale value="en_US" /> <fmt:formatNumber
									type="currency" maxFractionDigits="2" minFractionDigits="2"
									value="${hist.price}" /></td>

						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<br>
				
				<div class="alert alert-info">No price history found.</div>
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
		
	</div>
</div>
<jsp:include page="template-bottom.jsp" />
