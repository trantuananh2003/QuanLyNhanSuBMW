<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ page import="Models.LoginBean"%>
<%@ page import="filter.CSRFTokenGenerator" %>
<%
	String csrfToken = CSRFTokenGenerator.generateCSRFToken();
	session.setAttribute("csrfToken", csrfToken);
%>
<%
String nonce = (String) request.getAttribute("nonce");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<%=request.getContextPath()%>/static/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<script
	src="<%=request.getContextPath()%>/static/jquery/jquery-3.6.0.min.js"></script>

<title>Insert title here</title>
<style nonce="<%= nonce %>">
 .chinhstyle {
    border-radius: 1rem;
}
    
   

</style>
</head>
<body>
	<%
	String errMsg = (String) request.getAttribute("errMsg");
	LoginBean acc = (LoginBean) session.getAttribute("accLogin");
	%>
	<%
	if (acc == null && errMsg != null) {
	%>
	<div style="text-align: center; margin-top: 50px;">
		<p
			style="color: red; font-weight: bold; border: 1px solid red; padding: 10px; display: inline-block;"><%=errMsg%></p>
	</div>
	<%
	}
	%>
	<form class="login100-form validate-form"
		action="<%=request.getContextPath()%>/login" method="post">
		<input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}"/>
		<section class="vh-100 gradient-custom">
			<div class="container py-5 h-100">
				<div
					class="row d-flex justify-content-center align-items-center h-100">
					<div class="col-12 col-md-8 col-lg-6 col-xl-5">
						<div class="card bg-dark text-white chinhstyle" >
							<div class="card-body p-5 text-center">

								<div class="mb-md-5 mt-md-4 pb-5">

									<h2 class="fw-bold mb-2 text-uppercase">Login</h2>
									<p class="text-white-50 mb-5">Please enter your login and
										password!</p>

									<div class="form-outline form-white mb-4">
										<input type="text" id="typeEmailX"
											class="form-control form-control-lg" name="username" /> <label
											class="form-label" for="typeEmailX">Tài khoản</label>
									</div>

									<div class="form-outline form-white mb-4">
										<input type="password" id="typePasswordX"
											class="form-control form-control-lg" name="password" /> <label
											class="form-label" for="typePasswordX">Password</label>
									</div>
									<button class="btn btn-outline-light btn-lg px-5" type="submit">Login</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</form>
</body>
</html>