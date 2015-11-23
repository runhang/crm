<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>insert new title</title>
	<link rel="stylesheet" href="/static/css/bootstrap.css" />
	<link rel="stylesheet" href="/static/css/layout2.css "/>
	
</head>
<body>

<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="login" name="active"/>
</jsp:include>
<div class="container">
	<!-- action="/user/login.do" method="post" -->
	<form  id="loginform" class="form-horizontal widthSize600 loginwarpper">
		<div class="hide" style="width:600px; text-align:center;">
			<span id="help" class="errorSpan"></span>
		</div>
		<div class="control-group">
			<label class="control-label" for="username">用户名：</label>
			<div class="controls">
				<input id="username" name="username" type="text" placeholder="请输入用户名">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="password">密码：</label>
			<div class="controls">
				<input id="password" name="password" type="password" placeholder="请输入密码">
			</div>
		</div>
		<div class="control-group">
    		<div class="controls">
     		 <label class="checkbox">
        		<input name="remember" type="checkbox" value="yes"> 记住我 
     		 </label>
      		<button id="loginbtn" type="button" class="btn btn-primary">登陆</button>
    		<button type="reset" class="btn">重置</button>
    	</div>
  	</div>
	</form>
</div>




<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/md5.js"></script>
<script src="/static/js/js.cookie.js"></script>
<script>
	$(document).ready(function(){
		var $loginform = $("#loginform");
		var $username = $("#username");
		var $pwd= $("#password");
		var $loginbtn = $("#loginbtn");
		
		if(Cookies.get("ur")){
			$username.val(Cookies.get("ur"));
		}
		
		$loginbtn.bind("click", function(){
			var $rval = $('input[name="remember"]:checked').val();
			if($rval){
				Cookies.set("ur", $username.val(), 0.3);
			}
			if($pwd.val() && $pwd.val().length >= 6){
				$pwd.val(CryptoJS.MD5($pwd.val()));
			}
			
			$loginform.submit();
		});
		
		$loginform.validate({
			rules:{
				username:{
					required:true,
					minlength:3
				},
				password:{
					required:true,
					minlength:6,
					maxlength:50
				}
			},
			messages:{
				username:{
					required:"请输入用户名",
					minlength:"用户名长度不小于3"
				},
				password:{
					required:"请输入密码",
					minlength:"用户名长度不小于6",
					maxlength:"用户名长度不大于50"
				}
			},
			submitHandler:function(form){
				$.ajax({
					url:"/user/login.do",
					type:"post",
					data:$loginform.serialize(),
					success:function(json){
						if(json.result == "success"){
						   if(json.value == 0){
							  window.location.href="/admin/user/list.do";
							}else if(json.value == 2){
							  window.location.href = "/index.do";
							}
						}else{
							$("#help").parent().show();
							$("#help").show();
							$("#help").html(json.message);
							////////////////////////////////
							$pwd.val("");
						}
					}
				});
			},
			
			errorElement:"span",
			errorClass:"errorSpan",
			errorPlacement: function(error, element){
				error.appendTo(element.parent());	
			}
		});
		
		$(document).keydown(function(event){
	
			if(event.keyCode == 13){
				var $rval = $('input[name="remember"]:checked').val();
				if($rval){
					Cookies.set("ur", $username.val(), 0.3);
				}
				if($pwd.val() && $pwd.val().length >= 6){
					$pwd.val(CryptoJS.MD5($pwd.val()));
				}
				
				$loginform.submit();
				
			}
			
		});
	});
</script>
</body>
</html>