<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="Util.JDBCUtils"%>
<%@ page import="Models.NhanVien"%>
<%@ page import="Models.LoginBean"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<%=request.getContextPath()%>/static/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">

<%
String duongDanIndex = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
%>
<%
String nonce = (String) request.getAttribute("nonce");
%>


<link href="<%=duongDanIndex%>/css/sidebar.css" rel="stylesheet">
<link href="<%=duongDanIndex%>/css/profile.css" rel="stylesheet">
<title>Quản lý nhân viên</title>
<style nonce="<%= nonce %>">
    .col-2 {
        padding-left: 0px;
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
	<div class="container-fluid">
		<div class="row" >
			<div class="col-2" >
				<jsp:include page="../layout/sidebar.jsp"></jsp:include>
			</div>
			<div class="col-10" id="content">
				<jsp:include page="../layout/navbar.jsp"></jsp:include>
			</div>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/static/jquery/jquery-3.6.4.min.js" ></script>
	<script src="<%=request.getContextPath()%>/static/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript" nonce="<%= nonce %>" src="../js/main.js"></script>
<script type="text/javascript" nonce="<%= nonce %>">
		$(document).ready(function() {
			$('#sidebarCollapse').on('click', function() {
				$('#sidebar').toggleClass('active');
				$('#content').toggleClass('active');
			});
		});
		
	</script>
</body>
</html>