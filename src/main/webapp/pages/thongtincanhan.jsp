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
String nonce = (String) request.getAttribute("nonce");
%>

<%
String duongDanIndex = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
%>
<link href="<%=duongDanIndex%>/css/sidebar.css" rel="stylesheet">
<title>Quản lý nhân viên</title>
<style nonce="<%= nonce %>">
 .col-2 {
        padding-left: 0px;
    }
	#employeeTable th:nth-child(5) {
    width: 150px;
	}
	.formpopup {
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    }
	.card-body {
	    text-align: center;
	}
	#inputDuongDanAnh {
	    display: none;
	}
	
	#previewImage {
	    max-width: 100%;
	    max-height: 200px;
	}

</style>
</head>
<body>
	<%
	String errMsg = (String) request.getAttribute("errMsg");
	LoginBean acc = (LoginBean) session.getAttribute("accLogin");
	%>
	<%
	if (acc == null) {
	%>
	<% 
		response.sendRedirect(duongDanIndex + "/pages/login.jsp");
	%>
	<%
	}
	%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-2" >
				<jsp:include page="../layout/sidebar.jsp"></jsp:include>

			</div>
			<div class="col-10" id="content">
				<jsp:include page="../layout/navbar.jsp"></jsp:include>
				<nav>
						<div class="nav nav-tabs" id="nav-tab" role="tablist">
							<button class="nav-link active" id="nav-home-tab"
								data-bs-toggle="tab" data-bs-target="#nav-home" type="button"
								role="tab" aria-controls="nav-home" aria-selected="true">Thông
								tin cơ bản</button>
							<button class="nav-link" id="nav-profile-tab"
								data-bs-toggle="tab" data-bs-target="#nav-profile" type="button"
								role="tab" aria-controls="nav-profile" aria-selected="false">Hồ
								sơ</button>
							<button class="nav-link" id="nav-contact-tab"
								data-bs-toggle="tab" data-bs-target="#nav-contact" type="button"
								role="tab" aria-controls="nav-contact" aria-selected="false">Hợp
								đồng</button>
							<button class="nav-link" id="nav-new-tab" data-bs-toggle="tab"
								data-bs-target="#nav-new" type="button" role="tab"
								aria-controls="nav-new" aria-selected="false">Quá trình
								công tác</button>
						</div>
					</nav>
					<div class="tab-content" id="nav-tabContent">
						<div class="tab-pane fade show active" id="nav-home"
							role="tabpanel" aria-labelledby="nav-home-tab"><jsp:include
								page="./nhanvien_thongtincoban.jsp"></jsp:include>
						</div>
						<div class="tab-pane fade" id="nav-profile" role="tabpanel"
							aria-labelledby="nav-profile-tab"><jsp:include
								page="./nhanvien_hoso.jsp"></jsp:include>
						</div>
						<div class="tab-pane fade" id="nav-contact" role="tabpanel"
							aria-labelledby="nav-contact-tab">
							<jsp:include page="./nhanvien_hopdong.jsp"></jsp:include>
						</div>
						<div class="tab-pane fade" id="nav-new" role="tabpanel"
							aria-labelledby="nav-new-tab">
							<jsp:include page="./nhanvien_quatrinhcongtac.jsp"></jsp:include>
						</div>
						
					</div>													
			</div>

		</div>
	</div>

	<script
		src="<%=request.getContextPath()%>/static/jquery/jquery-3.6.0.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/static/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	
	<script type="text/javascript" src="../js/main.js"></script>
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