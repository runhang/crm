<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>message ok</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/static/css/font-awesome.min.css" />

</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="" name="active"/>
</jsp:include>
<div class="container">
	<h2>${param.username} operator ok</h2>
</div>
</body>
</html>