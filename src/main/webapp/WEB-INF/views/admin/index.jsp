<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>insert new title</title>
	<link rel="stylesheet" href="/static/css/bootstrap.css" />
</head>
<body>
<script src="/static/js/bootstrap.min.js"></script>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="admin" name="active"/>
</jsp:include>
<div class="container">
	<h2>hello amdin</h2>
	<a href="/admin/adduser.do">添加用户</a>
	<a href="/admin/adddept.do">添加部门</a>
	<a href="/admin/list.do">展示栏</a>
</div>

<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>

</body>
</html>