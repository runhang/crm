<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
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
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
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
	<div><a class="btn btn-primary" href="/schedule/add.do?chanceid=${requestScope.chance.id}">添加日程</a></div>
	<!-- method="post" action="/chance/event/add.do" -->
		<div id="btngroup" class="btn-group">
		<c:choose>
		<c:when test="${requestScope.chance.chstate == 1}">
  		   <button id="success" class="btn btn-success">成功</button>
  		</c:when>
  		<c:when test="${requestScope.chance.chstate == 3}">
  			<button id="success" class="btn" disabled="disabled" >成功</button>
  		</c:when>
  		<c:otherwise>
  			<button id="success" class="btn">成功</button>
  		</c:otherwise>
  		</c:choose>
  		<c:choose>
		<c:when test="${requestScope.chance.chstate == 0}">
  	 	   <button id="doing" class="btn btn btn-primary">跟进</button>
  	 	</c:when>
  	 	<c:when test="${requestScope.chance.chstate == 3}">
  			<button id="doing" class="btn" disabled="disabled" >跟进</button>
  		</c:when>
  	 	<c:otherwise>
  	 		<button id="doing" class="btn">跟进</button>
  	 	</c:otherwise>
  		</c:choose>
  		<c:choose>
  		<c:when test="${requestScope.chance.chstate == 2}">
  		   <button id="fail" class="btn btn-warning">失败</button>
  		</c:when>
  		<c:when test="${requestScope.chance.chstate == 3}">
  			<button id="fail" class="btn" disabled="disabled" >失败</button>
  		</c:when>
  		<c:otherwise>
  		   <button id="fail" class="btn">失败</button>
  		</c:otherwise>
  		</c:choose>
  		<c:choose>
  		<c:when test="${requestScope.chance.chstate == 3}">
  		   <button id="over" class="btn btn-inverse" disabled="disabled">终结</button>
  		</c:when>
  		<c:otherwise>
  		   <button id="over" class="btn">终结</button>
  		</c:otherwise>
  		</c:choose>
		</div>
		<form  id="subform" class="form">
		<input id="chid" name="chanceid" value="${chance.id}" type="hidden"/>
		<div class="control-group">
			<label  class="control-label">机会名称：${chance.chtitle}</label>
		</div>
		<div class="control-group">
			<label  class="control-label">机会描述或背景信息:</label>
			<div class="controls">
				<textarea name="evcontent" id="editor"></textarea>
			</div>
		</div>
		
		<div class="control-group">
			<c:choose>
			<c:when test="${requestScope.chance.chstate == 3}">
				<div class="controls">
				<button class="btn" disabled="disabled" id="subtn" type="button">提交</button>
				<button class="btn" disabled="disabled" type="button">保存</button>
			</div>
			</c:when>
			<c:otherwise>
				<div class="controls">
				<button  class="btn btn-primary" id="subtn" type="button">提交</button>
				<button  class="btn btn-primary" type="reset">重置</button>
			</div>
			</c:otherwise>
			</c:choose>
			
		</div>
	</form>
	<div>
		<p style="font-size:2em;">事件
			<span style=" background-color:#f00; margin-left:20px;">
			${chance.chtitle}
			</span>
		</p>
		<table class="table">
			
			<thead class="udMargin">
				<tr>
					<td>
						事件创建人
					</td>
					<td>
						所属机会名称
					</td>
					<td style="width:200px;">
						事件内容
					</td>
					<td>
						创建时间
					</td>
					<td>
						状态
					</td>
				</tr>
			</thead>
			<tbody id="tbody">
			<c:forEach items="${requestScope.page}" var="event">
				<tr>
					<td>${event.account.username}</td>
				
					<td>${event.chance.chtitle}</td>
				
					<td style="width:200px;">${event.evcontent }</td>
				
					<td>${event.evcreatetime}</td>
					<c:choose>
					<c:when test="${event.evstate==0}">
					<td style="color:#00f">跟进</td>
					</c:when>
					<c:when test="${event.evstate==1 }">
					<td style="color:#f00">成功</td>
					</c:when>
					<c:when test="${event.evstate==2 }">
					<td style="color:#0f0">失败</td>
					</c:when>
					<c:when test="${event.evstate==3 }">
					<td>终结</td>
					</c:when>
					</c:choose>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
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
	<script src="/static/js/handlebars-v3.0.3.js"></script>
	<script src="/static/js/moment-with-locales.js"></script>
	<script type="text/x-handlebars-template" id="template">
	<tr>
	<td>{{username}}</td>

	<td>{{chtitle}}</td>

	<td style="width:200px;">{{{evcontent}}}</td>

	<td>{{evcreatetime}}</td>
	{{#states evstate "成功"}}
		<td style="color:#f00">成功</td>
	{{/states}}
	{{#states evstate "跟进"}}
		<td style="color:#00f">跟进</td>
	{{/states}}
	{{#states evstate "失败"}}
		<td style="color:#0f0">失败</td>
	{{/states}}
	{{#states evstate "终结"}}
		<td style="color:#f00">终结</td>
	{{/states}}
	
</tr>
	</script>
	
	
	<script>
		$(document).ready(function(){
			var $subtn = $("#subtn");
			var $subform = $("#subform");
			var $success = $("#success");
			var $fail = $("#fail");
			var $doing = $("#doing");
			var $over = $("#over");
			
			Handlebars.registerHelper('states',function(a, b, options){
				if(a == b){
					return options.fn(this);
				}else if(a != b){
					return options.inverse(this);
				}
			});
			
			$success.bind("click", function(){
				$.ajax({
					url:"/chance/changestate.do",
					type:"post",
					data:{'type':'success','id':'${chance.id}'},
					success:function(json){
						if(json.result == "success"){
							  //window.location.href="/admin/user/list.do";
							$success.parent().children().attr("class", "btn");
							$success.attr("class","btn btn-success");
							//window.location.href="";
							var source = $("#template").html();
					        var template = Handlebars.compile(source);
							var data={
									'username':'${sessionScope.account.username}',
									'chtitle':'${chance.chtitle}',
									'evcontent':'处理为成功事件',
									'evcreatetime':moment().format('YYYY-MM-DD HH:MM:SS'),
									'evstate':'成功'
							};
					        var html = template(data);
					        $("#tbody").prepend(html);
						}else{
							alert("error: " + json.message);
						}
					}
				});
			});
			
			$fail.bind("click", function(){
				$.ajax({
					url:"/chance/changestate.do",
					type:"post",
					data:{'type':'fail','id':'${chance.id}'},
					success:function(json){
						if(json.result == "success"){
							  //window.location.href="/admin/user/list.do";
							//$("#btngroup").children().addClass("btn");
							$fail.parent().children().attr("class", "btn");
							$fail.attr("class", "btn btn-warning");
							
							//window.location.href="";
							var source = $("#template").html();
					        var template = Handlebars.compile(source);
							var data={
									'username':'${sessionScope.account.username}',
									'chtitle':'${chance.chtitle}',
									'evcontent':'处理为失败事件',
									'evcreatetime':moment().format('YYYY-MM-DD HH:MM:SS'),
									'evstate':'失败'
							};
					        var html = template(data);
					        $("#tbody").prepend(html);
						}else{
							alert("error: " + json.message);
						}
					}
				});
			});
			
			$over.bind("click", function(){
				$.ajax({
					url:"/chance/changestate.do",
					type:"post",
					data:{'type':'over','id':'${chance.id}'},
					success:function(json){
						if(json.result == "success"){
							  
							$over.parent().children().attr("class", "btn");
							$over.parent().children().attr("disabled", "disabled");		
							$over.attr("class","btn btn-inverse");
							$("#subtn").attr("disabled", "disabled");
							$("#subtn").next().attr("disabled", "disabled");
							//window.location.href="";
							var source = $("#template").html();
					        var template = Handlebars.compile(source);
							var data={
									'username':'${sessionScope.account.username}',
									'chtitle':'${chance.chtitle}',
									'evcontent':'处理为终结事件',
									'evcreatetime':moment().format('YYYY-MM-DD HH:MM:SS'),
									'evstate':'终结'
							};
					        var html = template(data);
					        $("#tbody").prepend(html);
						}else{
							alert("error: " + json.message);
						}
					}
				});
			});
			
			$doing.bind("click", function(){
				$.ajax({
					url:"/chance/changestate.do",
					type:"post",
					data:{'type':'doing','id':'${chance.id}'},
					success:function(json){
						if(json.result == "success"){
							  //window.location.href="/admin/user/list.do";
							$doing.parent().children().attr("class", "btn");
							$doing.attr("class","btn btn-primary");
							//window.location.href="";
							var source = $("#template").html();
					        var template = Handlebars.compile(source);
							var data={
									'username':'${sessionScope.account.username}',
									'chtitle':'${chance.chtitle}',
									'evcontent':'处理为跟进事件',
									'evcreatetime':moment().format('YYYY-MM-DD HH:MM:SS'),
									'evstate':'跟进'
							};
					        var html = template(data);
					        $("#tbody").prepend(html);
						}else{
							alert("error: " + json.message);
						}
					}
				});
			});
			
			
			$subtn.bind("click", function(){
				if($("#chid").val() != ''){
					$.ajax({
						url:'/chance/event/add.do',
						type:'post',
						data:{'chanceid':'${param.q_eq_evchanceid}',
							 'evcontent':$('#editor').val()
							},
						success:function(data){
							if(data.result=='success'){
								var source = $("#template").html();
						        var template = Handlebars.compile(source);
								var data={
										'username':'${sessionScope.account.username}',
										'chtitle':'${chance.chtitle}',
										'evcontent':$("#editor").val(),
										'evcreatetime':moment().format('YYYY-MM-DD HH:MM:SS'),
										'evstate':'跟进'
								};
						        var html = template(data);
						        $("#tbody").prepend(html);
							}else{
								alert("添加失败");
							}
							},
							error:function(){
								alert("与服务器断开连接")
							}
						
					});
				}
			
			});
			
			
			$subform.validate({
				rules:{
					
					evcontent:{
						required:true
					}
				},
				messages:{
					
					evcontent:{
						required:"必填项"
					}
				},
				submitHandler:function(form){
					$.ajax({
						url:"/chance/event/add.do",
						type:"post",
						data:$subform.serialize(),
						success:function(json){
							if(json.result == "success"){
								  window.location.href="/chance/event/add.do";
							}else{
								alert("error message: " + json.message);
							}
						}
					});
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