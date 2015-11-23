<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>insert new title</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="/static/css/layout2.css" />
	<style type="text/css">
		.table th, .table td {
    		text-align: center;
		}
	</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="main" name="active"/>
</jsp:include>
<div class="container udMargin">
	<div class="loginwarpper">
		<a class="btn btn-primary" href="/admin/dept/add.do">添加部分</a>
		<a class="btn btn-warning" href="/admin/dept/change.do">修改部门</a>
	</div>
</div>
<div class="container udMargin">
	
	   <form id="change" class="change">
		 <input class="dept" type="text" autofocus name="deptname">
		 <input class="dept" value="${param.id}" type="hidden" name="id">
		 
		 <button class="changebtn" type="button" name="" data-id="">提交</button>
	   </form>
</div>
   
    <script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
    <script type="text/javascript">
     	
    	$(document).ready(function(){
    		var $form = $("#change");
        	
        		$(".changebtn").on("click", function(){
        			$.ajax({
    					url:"/admin/dept/change.do",
    					type:"post",
    					data:$form.serialize(),
    					success:function(json){
    						if(json.result == "success"){
    							alert("修改成功");
    						   	window.location.href="/admin/dept/list.do";
    						}else{
    					   	alert("system message: " + json.message);
    						}
    					}
    			});
        	});
    	});
       
        
        
    </script>
</body>
</html>