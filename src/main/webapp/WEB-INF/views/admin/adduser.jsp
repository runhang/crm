<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>insert new title</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/static/css/layout2.css" />
</head>
<body>
	<script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="add" name="active"/>
</jsp:include>
<div style="height:60px;">

</div>
<div class="container">
	<form id="regform" class="form-horizontal" action="/admin/adduser.do" method="post">
		<div class="control-group">
    		<label class="control-label" for="inputEmail">账号：</label>
    		<div class="controls">
      			<input name="username" type="text" id="username" placeholder="账号">
    		</div>
  		</div>
  		<div class="control-group">
    		<label class="control-label" for="pass">密码：(初始值：123456)</label>
    		<div class="controls">
      			<input name="pass" type="password" id="pass" value="123456" placeholder="123456">
    		</div>
  		</div>
  		<div class="control-group">
    		<label class="control-label" for="inputEmail">所属组别</label>
    		<div class="controls">
      			<select name="dept">
      				<option value="">请输入所属单位</option>
      				<c:forEach items="${requestScope.deptList}" var="dept">
      					<option value="${dept.id}">${dept.deptname}</option>
      				</c:forEach>
      			</select>
    		</div>
  		</div>
  		<div class="control-group">
    		<div class="controls">
      			<button id="regbtn" class="btn btn-priamry" type="button">注册</button>
    		</div>
  		</div>
	</form>
</div>



<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/md5.js"></script>
<script>
	$(document).ready(function(){
		var $regform = $("#regform");
		var $regbtn = $("#regbtn");
		var $pwd = $("#pass");
		
		$regbtn.bind("click", function(){
			
			if($pwd.val() && $pwd.val().length >= 6){
				$pwd.val(CryptoJS.MD5($pwd.val()));
			}
			
			$regform.submit();
		});
		
		$regform.validate({
			rules:{
				username:{
					required:true,
					minlength:3,
					remote:"/user/validate.do"
				},
				password:{
					required:true,
					minlength:6,
					maxlength:50
				},
				dept:{
					required:true,
					min:1
				}
			},
			messages:{
				username:{
					required:"请输入用户名",
					minlength:"用户名长度不小于3",
					remote:"该账号已存在"
				},
				password:{
					required:"请输入密码",
					minlength:"用户名长度不小于6",
					maxlength:"用户名长度不大于50"
				},
				dept:{
					required:"请选择组",
					min:"请选择组"
				}
			},
			errorElement:"span",
			errorClass:"errorSpan",
			errorPlacement: function(error, element){
				error.appendTo(element.parent());	
			}
		});
	})
</script>

</body>
</html>