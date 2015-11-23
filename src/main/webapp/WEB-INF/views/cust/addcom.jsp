<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>add customer</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	
</head>
<body>

<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="addcom" name="active"/>
</jsp:include>

<div class="container">
	<form method="post" action="/cust/addcom.do" id="subform" class="form-horizontal">
		<div class="control-group">
			<label  class="control-label">公司：</label>
			<div class="controls">
				<input name="custname" placeholder="公司名" type="text"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">tel：</label>
			<div class="controls">
				<input name="tel" placeholder="电话" type="text"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">email:</label>
			<div class="controls">
				<input name="email" placeholder="邮箱" type="text"/>
			</div>
		</div>
		<div class="control-group">
			<label for="name" class="control-label">address</label>
			<div class="controls">
				<input name="address" placeholder="地址" type="text" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">im：</label>
			<div class="controls">
				<input name="im" placeholder="即时通信工具" type="text" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">website:</label>
			<div class="controls">
				<input name="website" placeholder="网址" type="text"  />
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">是否公开</label>
			<div class="controls">
				<input name="view" value="yes" readonly="readonly" placeholder="website" type="text"  />	
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">备注:</label>
			<div class="controls">
				<textarea name="content" id="" cols="60" rows="10"></textarea>
			</div>
		</div>
		<div class="control-group">
			
			<div class="controls">
				<button class="btn btn-primary" id="subtn" type="button">提交</button>
				<button class="btn btn-primary" type="button">保存</button>
			</div>
		</div>
	</form>
	
	<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
	<script src="/static/js/jquery.validate.min.js"></script>
	<script>
		$(document).ready(function(){
			$subtn = $("#subtn");
			$subform = $("#subform");
			
			$subtn.bind("click", function(){
				
				$subform.submit();
			});
			
			$subform.validate({
				rules:{
					custname:{
						required:true
					},
					tel:{
						required:true
					},
					email:{
						email:true
					},
					website:{
						url:true
					}
				},
				messages:{
					custname:{
						required:"必填项"
					},
					tel:{
						required:"必填项"
					},
					email:{
						email:"请输入正确的格式"
					},
					website:{
						url:"请输入正确的格式"
					}
				},
				errorElement:"span",
				errorClass:"errorSpan",
				errorPlacement:function(error, element){
					error.appendTo(element.parent());
				}
			});
		});
	</script>
</div>

</body>
</html>