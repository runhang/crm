<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>add cust</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="/static/js/edit/styles/simditor.css" />
	<link rel="stylesheet" href="/static/css/layout3.css" />
	
	<style>
	  .simditor-body{
		max-height:260px;
		width:300px;
	}
	 .simditor{
		width:300px;	 
	 }
	 .simditor .simditor-body {
  		padding: 0px;
  		width:300px;
 		min-height: 260px;
	}
	</style>
</head>
<body>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="addcust" name="active"/>
</jsp:include>

<div class="container">
	<form method="post" action="/cust/addcust.do" id="subform" class="form-horizontal">
		<input name="account" type="hidden" value="${sessionScope.account.id}" />
		<div class="control-group">
			<label  class="control-label">客户姓名：</label>
			<div class="controls">
				<input name="custname" placeholder="客户名" type="text"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">客户职位：</label>
			<div class="controls">
				<input name="jobs" placeholder="客户职位" type="text"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">所属公司：</label>
			<div class="controls">
				<input name="company" placeholder="所属公司" type="text" />
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
				<input name="email" placeholder=邮箱" type="text"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">address</label>
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
				<select name="view" >
					<option value="yes">公开</option>
					<option value="no">私有</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">备注:</label>
			<div class="controls">
				<textarea  name="content" id="editor"placeholder="请输入你想输入的内容" ></textarea>
			</div>
		</div>
		<div class="control-group">
			
			<div class="controls">
				<button class="btn btn-primary" id="subtn" type="button">提交</button>
				<button class="btn btn-primary" type="button">保存</button>
			</div>
		</div>
	</form>
	
	<script src="/static/js/jquery.validate.min.js"></script>

	<script type="text/javascript" src="/static/js/edit/scripts/module.js"></script>
	<script type="text/javascript" src="/static/js/edit/scripts/hotkeys.js"></script>
	<script type="text/javascript" src="/static/js/edit/scripts/uploader.js"></script>
	<script type="text/javascript" src="/static/js/edit/scripts/simditor.js"></script>
	
	
	<script>
	
		$(document).ready(function(){
			$subtn = $("#subtn");
			$subform = $("#subform");
			
			var editor = new Simditor({
				textarea: $('#editor'),
				toolbar:false
				/*toolbar:[
  					'title',
  					'bold',
  					'italic',
  					'underline',
  					'strikethrough',
  					'color',
  					'ol',
  					'ul',
  					'blockquote',
  					'code', 
  					'table',
  					'link',
  					'hr',
  					'indent',
  					'outdent', 
  					'alignment'
				]*/
			});
			
			
			
			$subtn.bind("click", function(){
				
				$subform.submit();
			});
			
			$subform.validate({
				rules:{
					custname:{
						required:true
					},
					jobs:{
						required:true
					},
					company:{
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
					jobs:{
						required:"必填项"
					},
					company:{
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