<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>edit jsp</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/static/js/edit/styles/simditor.css" />
	<link rel="stylesheet" href="/static/css/layout3.css" />

	<style type="text/css">
	.simditor-body{
		max-height:230px;
		width:700px;
	}
	.simditor{
		width:700px;	 
	}
	.simditor .simditor-body {
  		padding: 0px;
  		width:700px;
 		min-height: 230px;
	}
</style>

</head>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="" name="active"/>
</jsp:include>
<body>
<div class="row">
	<div class="span3">
		<ul class="ul">
			<li><a href="#"><span class="font2">添加客户</span></a></li>
			<li><a href="#"><span class="font2">添加公司</span></a></li>
			<li><a href="#"><span class="font2">客户列表</span></a></li>
			<li><a href="#"><span class="font2">客户反馈</span></a></li>
		</ul>
	</div>
	<div style="width:56%; " class="span9" >
		<form id="textget" class="hide">
			<input type="hidden" name="p" id="pageNo" value="${requestScope.page.pageNo}"/>
 			<input type="hidden" name="id" value="${param.id}"/>
		</form>
		<form class="form-horizontal" action="/cust/event.do" method="post" id="textform">
			<input type="hidden" name="id" value="${param.id}"/>
			<textarea  name="content" id="editer" autofocus placeholder="请输入你想输入的内容" ></textarea>
			<div class="form-group">
				<button class="btn btn-primary">保存</button>
			</div>
		</form>
	</div>
	<div class="span3">
	
		<c:choose>
		<c:when test="${requestScope.cust.flags}">
		
		<ul class="ul">
			<c:if test="${sessionScope.account.id == requestScope.cust.accountid}">
			<li><a class="btn btn-primary" href="/cust/editcust.do?id=${requestScope.cust.id}">修改客户</a></li>
			<li><a class="btn" href="/cust/delcust.do?id=${requestScope.cust.id}">删除该客户</a></li>
			
			</c:if>
			<li><a class="btn" href="/schedule/add.do?custid=${requestScope.cust.id}">添加日程</a></li>
			<li><span class="font1">客户姓名： </span></li>
			<li><span class="font2"> ${requestScope.cust.custname}</span></li>
			<li><span class="font1">客户公司</span></li>
			<li><span class="font2">${requestScope.cust.jobs}</span></li>
			<li><span class="font1">客户职位:</span></li>
			<li><span class="font2">${requestScope.cust.company.custname}</span></li>
			<li><span class="font1">客户电话:</span></li>
			<li><span class="font2">&nbsp;${requestScope.cust.tel}</span></li>
		</ul>
		</c:when>
		<c:otherwise>
		<ul class="ul">
			<li><a class="btn" href="/schedule/add.do?comid=${requestScope.cust.id}">添加日程</a></li>
			<li><a class="btn btn-primary" href="/cust/editcompany.do?id=${requestScope.cust.id}">修改客户公司</a></li>
			<li><span class="font1">客户公司名称： </span></li>
			<li><span class="font2"> ${requestScope.cust.custname}</span></li>
			<li><span class="font1">客户公司电话:</span></li>
			<li><span class="font2">&nbsp;${requestScope.cust.tel}</span></li>
		</ul>	
		</c:otherwise>
		</c:choose>
	</div>
</div>
	
<div class="container">
	<ul class="nav nav-tabs">
  <li>
    <a id ="contentshow" href="javascript:;">备注</a>
  </li>
  <li><a id="changeshow" href="javascript:;">机会</a></li>
  <li><a id="scheduleshow" href="javascript:;">日程</a></li>
  <li><a href="javascript:;" id="cshow">后续功能</a>
</ul>
</div>
<div class="container">
	<div id="show">
		<c:forEach items="${requestScope.page.items}" var="content">
			<div>
				<p>${content.content}</p>
				<small>${content.createtime}</small>
				
			</div>
		</c:forEach>
	</div>
	<!-- 分页 -->
	<!-- 
    <div class="pagination pagination-right">
     <ul>
        <c:choose>
            <c:when test="${page.pageNo == 1}">
              <li class="disabled"><a href="javascript:;" data-page="1">首页</a></li>
              <li class="disabled"><a href="javascript:;" data-page="${page.pageNo - 1}">上一页</a></li>
            </c:when>
        	<c:otherwise>
              <li><a href="javascript:;" class="page" data-page="1">首页</a></li>
              <li><a href="javascript:;" class="page" data-page="${page.pageNo - 1}">上一页</a></li>
            </c:otherwise>
       </c:choose>
       <c:choose>
            <c:when test="${page.totalPages == page.pageNo}">
              <li class="disabled"><a href="javascript:;" data-page="${page.pageNo + 1}">下一页</a></li>
              <li class="disabled"><a href="javascript:;" data-page="${page.totalPages}">末页</a></li>
            </c:when>
       		<c:otherwise>
              <li><a href="javascript:;" class="page" data-page="${page.pageNo + 1}">下一页</a></li>
              <li><a href="javascript:;" class="page" data-page="${page.totalPages}">末页</a></li>
       		</c:otherwise>
       </c:choose>
   </ul>
 </div>
 <!-- pager end -->
</div>

<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/edit/scripts/module.min.js"></script>
<script src="/static/js/edit/scripts/hotkeys.min.js"></script>
<script src="/static/js/edit/scripts/uploader.min.js"></script>
<script src="/static/js/edit/scripts/simditor.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/handlebars-v3.0.3.js"></script>
<script src="/static/js/moment-with-locales.js"></script>

<script type="type/handlebars" id="contemplate">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<td>事件内容</td>
				<td>创建时间</td>
			</tr>
		</thead>
		<thead>
			{{#each page.items}}
			<tr>
				<td>{{{content}}}</td>
				<td>{{createtime}}</td>
			</tr>
            {{/each}}
		</thead>
	</table>
	<div class="pagination pagination-right">
     <ul>
		{{#pagestart page.pageNo 1}}
			<li class="disabled"><a href="javascript:;" data-page="1">首页</a></li>
            <li class="disabled"><a href="javascript:;" data-page="${page.pageNo - 1}">上一页</a></li>
		{{else}}
			<li><a href="javascript:;" class="page" data-page="1">首页</a></li>
        	<li><a href="javascript:;" class="page" data-page="${page.pageNo - 1}">上一页</a></li> 
		{{/pagestart}}
        {{#endpage page.totalPages page.pageNo}}
              <li class="disabled"><a href="javascript:;" data-page="${page.pageNo + 1}">下一页</a></li>
              <li class="disabled"><a href="javascript:;" data-page="${page.totalPages}">末页</a></li>
        {{else}}
              <li><a href="javascript:;" class="page" data-page="${page.pageNo + 1}">下一页</a></li>
              <li><a href="javascript:;" class="page" data-page="${page.totalPages}">末页</a></li>
       	{{/endpage}}
   </ul>
 </div>
</script>
<script type="text/x-handlebars-template" id="chancetemplate">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<td>标题</td>
				<td>客户名字</td>
				<td>创建者</td>
				<td>事件内容</td>
				<td>创建时间</td>
				<td>金额</td>
				<td style="width:5em;">状 态</td>
				<td>操作</td>
			</tr>
		</thead>
		<thead>
			{{#each page.items}}
			<tr>
				<td>{{chtitle}}</td>
				<td>{{customer.custname}}</td>
				<td>{{account.username}}</td>
				<td style="width:200px;">{{{chcontent}}}</td>
				<td>{{chcreatetime}}</td>
				<td>{{chmoney}}</td>
				{{#if_eq chstate 1}}
				<td style="width:5em;">成功</td>
				{{/if_eq}}
				{{#if_eq chstate 2}}
				<td style="width:5em;">终结</td>
				{{/if_eq}}
				{{#if_eq chstate 0}}
				<td style="width:5em;">跟进</td>
				{{/if_eq}}
				{{#if_eq chstate 3}}
				<td style="width:5em;">失败</td>
				{{/if_eq}}
				<td><a class="btn" href="javascript:;" data-id="{{id}}">进入</a></td>
			</tr>
            {{/each}}
		</thead>
	</table>
	
	<div class="pagination pagination-right">
     <ul>
		{{#pagestart page.pageNo 1}}
			<li class="disabled"><a href="javascript:;" data-page="1">首页</a></li>
            <li class="disabled"><a href="javascript:;" data-page="${page.pageNo - 1}">上一页</a></li>
		{{else}}
			<li><a href="javascript:;" class="page" data-page="1">首页</a></li>
        	<li><a href="javascript:;" class="page" data-page="${page.pageNo - 1}">上一页</a></li> 
		{{/pagestart}}
        {{#endpage page.totalPages page.pageNo}}
              <li class="disabled"><a href="javascript:;" data-page="${page.pageNo + 1}">下一页</a></li>
              <li class="disabled"><a href="javascript:;" data-page="${page.totalPages}">末页</a></li>
        {{else}}
              <li><a href="javascript:;" class="page" data-page="${page.pageNo + 1}">下一页</a></li>
              <li><a href="javascript:;" class="page" data-page="${page.totalPages}">末页</a></li>
       	{{/endpage}}
   </ul>
 </div>
		

</script>


<script type="type/handlebars" id="schtemplate">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<td>日程名称</td>
				<td>创建者</td>
				<td>日程创建时间</td>
				<td>日程开始时间</td>
				<td>日程到期时间</td>
				<td>是否过期</td>
			</tr>
		</thead>
		<thead>
			{{#each overs}}
			<tr>
				<td>{{{schtitle}}}</td>
				<td style="color:{{sort.sortname}};">{{account.username}}</td>
				<td>{{schcreatetime}}</td>
				<td>{{schfromtime}}</td>
				<td>{{scheduletime}}</td>
				<td>已过期</td>
			</tr>
            {{/each}}
			{{#each unovers}}
			<tr>
				<td>{{{schtitle}}}</td>
				<td style="color:{{sort.sortname}};">{{account.username}}</td>
				<td>{{schcreatetime}}</td>
				<td>{{schfromtime}}</td>
				<td>{{scheduletime}}</td>
				<td>未完成</td>
			</tr>
            {{/each}}
		</thead>
	</table>
</script>

<script>
	$(document).ready(function(){
		
		var flags = 0;
		var chancepageNO = 0; //标记机会页面大小
		var contentpageNO = 0;//标记content页面大小
		var total = 0;
		
		var editer = new Simditor({
            textarea:$("#editer"),
           
		  defaultImage: 'images/image.png',
		  toolbar :false
		  
        });
		
		
		
		
		Handlebars.registerHelper('pagestart', function(conditional, a, options) {
			if(conditional == a) {
			    return options.fn(this);
			  }else if(conditional != a){
				  return options.inverse(this);
			  }
			});
		
		Handlebars.registerHelper('is_done',function(a, b, options){
			if(a == b){
				return options.fn(this);
			}else if(a != b){
				return options.inverse(this);
			}
		});
		
		Handlebars.registerHelper('if_eq', function(a, b, options) {
			if(a == b) {
			    return options.fn(this);
			  }else if(a != b){
				  return options.inverse(this);
			  }
		});
		
		Handlebars.registerHelper('endpage', function(a, b, options) {
			  if(a == b) {
			    return options.fn(this);
			  }else if(a != b){
				  return options.inverse(this);
			  }
		});
		
		$("#scheduleshow").on("click", function(){
			//用jQuery获取模板
			$.ajax({
				url:"/api/schedule/list.do",
				type:"get",
				data:{'custid':'${requestScope.cust.id}'},
				success:function(data){
					console.log("111");
					var source = $("#schtemplate").html();
					var template = Handlebars.compile(source);
					$('#show').html("");
					var json = template(data);
					$('#show').html(json);
				},
				error:function(){
					alert("服务器忙，请重试");
				}
			});
			
		});
		
		$("#contentshow").on("click",function(){
			//用jquery获取模板
		    //var source   =  $("#contemplate").html();
		    //预编译模板
		    $.ajax({
					url:"/api/content/show.do",
					type:"get",
					data:{'p':contentpageNO,
						  'id':'${param.id}'
						},
					success:function(data){
						contentpageNO = data.page.pageNo;
						console.log(data.page.totalPages);
						console.log("contentPageNo: "+ contentpageNO);
						console.log(total);
						var source   =  $("#contemplate").html();
						var template = Handlebars.compile(source);
					    //模拟json数据
					    $('#show').html("");
					    var json = template(data);
					    $("#show").html(json);
					}
						
				});
		    
		});
		
		$("#changeshow").on("click",function(){
			//用jquery获取模板
		  //  var source   =  $("#chancetemplate").html();
		    //预编译模板
		    $.ajax({
					url:"/api/chance/show.do",
					type:"get",
					data:{'p':chancepageNO,
						   'q_eq_chcustid':'${param.id}'
						},
					success:function(data){
						chancepageNO = data.page.pageNo;
						total = data.page.totalPages;
						console.log(total);
						var source   =  $("#chancetemplate").html();
						var template = Handlebars.compile(source);
					    //模拟json数据
					    $('#show').html("");
					    var json = template(data);
					    $("#show").html(json);
					}
						
				});
		    
		});
		
		$(".page").click(function(){
		    var pageNo = $(this).attr("data-page");
		    $("#pageNo").val(pageNo);
		    $("#textget").submit();
		});
		
		function fChance(){
			$.ajax({
				url:"/api/chance/show.do",
				type:"get",
				data:{'p':'${requestScope.page.pageNo}',
					   'e_eq_custid':'${param.id}'
					},
				success:function(data){
					console.log(data.list.title);
					var source   =  $("#chancetemplate").html();
					var template = Handlebars.compile(source);
				    //模拟json数据
				    $('#show').html("");
				    var json = template(data);
				    $("#show").html(json);
				}
					
			});
		}
	
	
	
	
	});
	
	
	
	
	
	
	
	
	
	/*
	 //defaultImage:'/js/edit/images/image.png',
            upload:{
                url:'/upload.do',
                fileKey:'file'
           }
	toolbar:[
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
	  'alignment',    
]*/
</script>
</body>
</html>