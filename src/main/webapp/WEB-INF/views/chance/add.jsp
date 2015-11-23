<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>add customer</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/static/css/layout2.css" />
	<link rel="stylesheet" href="/static/css/layout3.css" />
	<link rel="stylesheet" type="text/css" href="/static/js/edit/styles/simditor.css" />
 
 	<style>
	  .simditor-body{
		max-height:102px;
		width:600px;
	}
	 .simditor{
		width:600px;	 
	 }
	 .simditor .simditor-body {
  		padding: 0px;
  		width:600px;
 		min-height: 102px;
	}
	</style>
 
</head>
<body style="background-color:#cddecf">
<script type="text/javascript" src="/static/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.js"></script>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="chance" name="active"/>
</jsp:include>
<div class="row">
	<div class="span3 leftMargin120">
		<ul class="ul">
			<li><a href="#">最新动态</a></li>
			<li><a href="#">联系人</a></li>
			<li><a href="#">日程安排</a></li>
			<li><a href="#">业务机会</a></li>
			<li><a href="#">搜索</a></li>
		</ul>
	</div>
	<div style="width:738" class="span8">
	<h3  style="background-color:#F0F5F8">添加机会</h3>
		<form method="post" action="/chance/add.do" id="subform" class="form">
		
		<div class="control-group">
			<label  class="control-label">联系人：</label>
			<div class="controls">
				<input id="autocomplete"  placeholder="联系人" type="text"/>
				<input id="custid" name="custid" value="" type="hidden"/>
			</div>
		</div>
		<input id="custid" name="custid" value="" type="hidden"/>
		<div class="control-group">
			<label  class="control-label">机会名称：</label>
			<div class="controls">
				<input name="chtitle" placeholder="机会名称" type="text"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">机会描述或背景信息:</label>
			<div class="controls">
				<textarea name="chcontent" id="editor" cols="60" rows="10"></textarea>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">价格：</label>
			<div class="controls">
				<input name="chmoney" placeholder="价格" type="text"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">是否公开</label>
			<div class="controls">
				<select name="chviews" >
					<option value="yes">公开</option>
					<option value="no">私有</option>
				</select>
			</div>
		</div>
		
		<div class="control-group">
			
			<div class="controls">
				<button class="btn btn-primary" id="subtn" type="button">提交</button>
				<button class="btn btn-primary" type="button">保存</button>
			</div>
		</div>
		
		
		
	</form>
  </div> 
  <div class="span3">
  <div class="box" style="background-color:#eeeeee">
  	<h3  style="background-color:#D3DBFF">什么是机会</h3>
  	<p>机会即业务机会或商业机会，比如一笔可能成交的业务或项目。</p>
  	<p>机会通常跟一个联系人相关，您需要先将这个联系人添加到系统。</p>
  	<p>在输入联系人时，系统会根据已输入的内容自动查找联系人，并以下拉列表的形式显示出来，您可以从中选取一个。</p>
  	<p>您可以根据自己的业务情况给机会添加分类。比如按产品分类、按重要程度或行业分类。</p>
  
  </div>
  </div>
</div>

	<script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/jquery.validate.min.js"></script>

	<script type="text/javascript" src="/static/js/edit/scripts/module.js"></script>
	<script type="text/javascript" src="/static/js/edit/scripts/hotkeys.js"></script>
	<script type="text/javascript" src="/static/js/edit/scripts/uploader.js"></script>
	<script type="text/javascript" src="/static/js/edit/scripts/simditor.js"></script>
	
	<script src="/static/js/jquery.autocomplete.min.js"></script>
	<script>
		$(document).ready(function(){
			var $subtn = $("#subtn");
			var $subform = $("#subform");
			var tempid=null;
			var tempname=null;
			
			$('#autocomplete').autocomplete({
			    serviceUrl: "/api/cust.json",
			    onSelect: function (suggestion) {
			        //alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
			        $("#custid").val(suggestion.data);
			        tempid = suggestion.data;
			        tempname = suggestion.value;
			        
			    },
			   
			    
			});
			
			
			$subtn.bind("click", function(){
				$("#autocomplete").next().remove();
				if(tempid==$("#custid").val()
						&& tempname == $("#autocomplete").val()){
					$subform.submit();
				}else{
					//if($("#custid").val() == ""){
					$('#autocomplete').after("<span class='errorSpan'>没有该用户</span>");	
					//}else 
				}
			});
			
			$("#autocomplete").focus(function(){
				$(this).next().remove();
			});
			
			$subform.validate({
				rules:{
					custname:{
						required:true
					},
					chtitle:{
						required:true
					},
					chcontent:{
						required:true
					},
					chviews:{
						required:true
					},
					chmoney:{
						required:true,
						number:true
					}
				},
				messages:{
					custname:{
						required:"必填项"
					},
					chtitle:{
						required:"必填项"
					},
					chcontent:{
						required:"必填项"
					},
					chviews:{
						required:"必填项"
					},
					chmoney:{
						required:"必填项",
						number:"数字"
					}
				},
				errorElement:"span",
				errorClass:"errorSpan",
				errorPlacement:function(error, element){
					error.appendTo(element.parent());
				}
			});
			
			//在线编辑框
			var $editer = new Simditor({
				textarea: $('#editor'),
				toolbar:false
			});
		});
	</script>


</body>
</html>