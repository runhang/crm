<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>改密码</title>
	<link rel="stylesheet" href="/static/css/bootstrap.css" />
	<link rel="stylesheet" href="/static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="/static/css/layout2.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="" name="active"/>
</jsp:include>
	<div class="container">
		<form id="changeform" > <!-- action="/user/changepass.do" method="post"-->
		<div class="form-horizontal loginwarpper">
			<div class="control-group">
				<label  class="control-label">原密码：</label>
				<div class="controls">
				   <input id="oldpass" type="password" name="oldpass" placeholder="请输入原密码"> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">新密码：</label>
				<div class="controls">
				   <input id="newpass" type="password" name="newpass" placeholder="请输入新密码"> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">确认新密码：</label>
				<div class="controls">
				   <input id="conpass" type="password" name="conpass" placeholder="请确认新密码"> 
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
				   <button id="changebtn" class="btn btn-pariary" type="button">修改密码</button>
					<button class="btn" type="reset">重置</button>
				</div>
			</div>
		</div>
		</form>
	</div>
	
	
	<script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
	<script src="/static/js/jquery.validate.min.js"></script>
	<script src="/static/js/md5.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			var $changeform = $("#changeform");
			var $changebtn = $("#changebtn");
			var $oldpass = $("#oldpass");
			var $newpass = $("#newpass");
			var $conpass = $("#conpass"); 
			
			$changebtn.bind("click", function(){
				
				
				if($oldpass.val() != '' && $oldpass.val().length >= 6 
				   && $newpass.val() != '' && $newpass.val().length >= 6){
					$oldpass.val(CryptoJS.MD5($oldpass.val()));
					$newpass.val(CryptoJS.MD5($newpass.val()));
					$conpass.val(CryptoJS.MD5($conpass.val()));
				}
					$changeform.submit();
				
			});
			
			
			$changeform.validate({
				rules:{
					oldpass:{
						required:true,
						minlength:6,
						maxlength:50
					},
					newpass:{
						required:true,
						minlength:6,
						maxlength:50
					},
					conpass:{
						required:true,
						equalTo:"#newpass"
					}
				},
				messages:{
					oldpass:{
						required:"必输字段",
						minlength:"不小于6个字符",
						maxlength:"不大于50个字符"
					},
					newpass:{
						required:"必输字段",
						minlength:"不小于6个字符",
						maxlength:"不大于50个字符"
					},
					conpass:{
						required:"必输字段",
						equalTo:"两次密码不相等"
					}
				},
				submitHandler:function(form){
					$.ajax({
						url:"/admin/user/changepass.do",
						type:"post",
						data:$changeform.serialize(),
						success:function(json){
							if(json.state == "ok"){
								window.location.href = "/admin/user/changepass.do";
							}else{
								alert("error");
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
			
		});
	</script>
	
</body>
</html>