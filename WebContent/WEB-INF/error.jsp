<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<style>
.error {
  margin: 0 auto;
  text-align: center;
}

.error-code {
  bottom: 60%;
  color: #2d353c;
  font-size: 96px;
  line-height: 100px;
}

.error-desc {
  font-size: 12px;
  color: #647788;
}

.m-b-10 {
  margin-bottom: 10px!important;
}

.m-b-20 {
  margin-bottom: 20px!important;
}

.m-t-20 {
  margin-top: 20px!important;
}

</style>
<body>
<div class="error">
        <div class="error-code m-b-10 m-t-20">Error Page <i class="fa fa-warning"></i></div>
        <h3 class="font-bold">We couldn't find the page..</h3>

        <div class="error-desc">
           <c:forEach var="error" items="${errors}">
			<div class="alert alert-danger col-md-8">
			<strong>Error: </strong> ${error}
			</div>
			</c:forEach>
            <div>
                <a class=" login-detail-panel-button btn" href="login.do">
                        <i class="fa fa-arrow-left"></i>
                        Go back to Login                         
                    </a>
            </div>
        </div>
    </div>
 </body>
</html>
