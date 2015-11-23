<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>index page</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="/static/css/layout3.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="main" name="active"/>
</jsp:include>

<div class="row-fluid">
	<div class="span3">
		<ul class="ul">
		   <li>
			<a href="http://runlove.sinaapp.com" target="_blank"><span class="font2">后续功能</span></a>
		   </li>
		   <li>
			<a href="http://runlove.sinaapp.com" target="_blank"><span class="font2">后续功能</span></a>
		   </li>
		   <li>
			<a href="http://runlove.sinaapp.com" target="_blank"><span class="font2">后续功能</span></a>
		   </li>
		   <li>
			<a href="#"><span class="font2">后续功能</span></a>
		   </li>
		   <li>
			<a href="#"><span class="font2">后续功能</span></a>
		   </li>
		   
		</ul>
	</div>
	<div class="span6">
		<div class="pagebody">
		   <div class="pagerheader">最新动态</div>
		<table>
		  <tbody>
		  <c:forEach items="${requestScope.pager.items}" var="trends">
		  <tr>
			  <td class="type">
				<span class="event">${trends.tretype }</span>
			  </td>
			  <td>
				 <strong>
				   <a href="#">${trends.tretitle}</a>
				  </strong>
				   <br/>
				   ${trends.trecontent}
				   <br/>
					<span style="color:#ff0000;">由&nbsp;${sessionScope.account.username}&nbsp;创建</span>
				</td>
				<td class="time">
					<span class="today" style="color:#ff0000;">${trends.trecreatetime}</span>
				</td>
			</tr>
			</c:forEach>
			<tr>
			  <td class="type">
				<span class="event">事件</span>
			  </td>
			  <td>
				 <strong>
				   <a href="#">收购摩托罗拉</a>
				  </strong>
				   <br>
				阿森纳官方网站宣布，他们已经就纳斯里的转会与曼城达成协议，这位法国国脚即将离队，在同时，阿森纳也宣布，纳斯里将不会参加明天对乌迪内斯的欧冠资格赛，他将前往曼彻斯特接受体检。阿森纳官网没有透露纳斯里的转会费，不过《卫报》预测，纳斯里的转会费为2300万英镑。
				   <br>
					<span style="color:#ff0000;">由&nbsp;${sessionScope.account.username}&nbsp;创建</span>
				</td>
				<td class="time">
					<span class="today">21:55</span>
				</td>
			</tr>
			</tbody></table>
		</div>
		<div class="pagination pagination-right">
		<form id="turnpage" action="/index.do" method="get">
			<input id="pageNo" type="hidden" name="p" value="${requestScope.pager.pageNo}" />
		</form>
     <ul>
        <c:choose>
            <c:when test="${pager.pageNo == 1}">
              <li class="disabled"><a href="javascript:;" data-page="1">首页</a></li>
              <li class="disabled"><a href="javascript:;" data-page="${pager.pageNo - 1}">上一页</a></li>
            </c:when>
        	<c:otherwise>
              <li><a href="javascript:;" class="page" data-page="1">首页</a></li>
              <li><a href="javascript:;" class="page" data-page="${pager.pageNo - 1}">上一页</a></li>
            </c:otherwise>
       </c:choose>
       <c:choose>
            <c:when test="${pager.totalPages == pager.pageNo}">
              <li class="disabled"><a href="javascript:;" data-page="${pager.pageNo + 1}">下一页</a></li>
              <li class="disabled"><a href="javascript:;" data-page="${pager.totalPages}">末页</a></li>
            </c:when>
       		<c:otherwise>
              <li><a href="javascript:;" class="page" data-page="${pager.pageNo + 1}">下一页</a></li>
              <li><a href="javascript:;" class="page" data-page="${pager.totalPages}">末页</a></li>
       		</c:otherwise>
       </c:choose>
   </ul>
 </div>
	</div>
	<div class="span3">
		<ul class="ul">
		   <li>
			<a href="/cust/addcust.do" class="btn btn-info">添加客户</a>
		  
			<a href="/chance/add.do" class="btn btn-info">添加客户</a>
		   </li>
		  </ul>
		   <div class="box">
				<h3>您的日程安排</h3>
				<strong class="timeline">
				今日日程
				</strong>
				<c:forEach items="${requestScope.todayList}" var="today">
				<div class="task">
					<input class="undo" type="checkbox" data-id="${today.id}">
						<span class="type">${today.customer.tel}</span>
						<span class="content">${today.schtitle}</span>
				</div>
				</c:forEach>
				
				<strong class="timeline">
				过期日程
				</strong>
				<c:forEach items="${requestScope.overList}" var="overSchedule">
				<div class="task">
					<input class="over" type="checkbox"  data-id="${overSchedule.id}">
					<span class="type">${overSchedule.customer.tel}</span>
					<span class="content">${overSchedule.schtitle}</span>		
				</div>
				</c:forEach>
				<div class="task">
					<input type="checkbox">
						<span class="type">电话</span>
						<span class="content">预约王总</span>
				</div>
						
				</div>
		   
		<ul class="ull">
			<li style="background-color:#FF5A5E;">过期事件<span id="chover" >${overUndone}</span></li>
			<li style="background-color:#5AD3D1;">待办事件<span id="chundone" >${unoverUndo}</span></li>
			<li style="background-color:#FDB45C;">已完成事件<span id="chdone">${done}</span></li>
		</ul>
		<canvas id="myChart" width="200" height="200"></canvas>
	</div>
</div>


<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/Chart.min.js"></script>
<script>
	$(document).ready(function(){
		var data = [
		            {
		                value: '${overUndone}',
		                color:"#F7464A",
		                highlight: "#FF5A5E",
		                label: "过期事件"
		            },
		            {
		                value: '${unoverUndo}',
		                color: "#46BFBD",
		                highlight: "#5AD3D1",
		                label: "待办事件"
		            },
		            {
		                value: '${done}',
		                color: "#FDB45C",
		                highlight: "#FFC870",
		                label: "已完成事件"
		            }
		        ];
		
		console.log(parseInt(data[2].value)+1);
		data[2].value = parseInt(data[2].value)+1;
		var pie = document.getElementById("myChart").getContext("2d");
		var myPie = new Chart(pie).Pie(data);
		 
		$(document).on('click', '.over', function(){
			var scheduleid = $(this).attr("data-id");
			var $this = $(this);
			$.ajax({
				url:'schedule/done.do',
				dataType:'json',
				tye:'get',
				data:{'id':scheduleid},
				success:function(datas){
					if(datas.result == 'success'){
						data[2].value = parseInt(data[2].value)+1;
						$("#chdone").html(data[2].value);
						$this.parent().remove();
						myPie = new Chart(pie).Pie(data);
					}else{
						alert(datas.message);
					}
				},
				error:function(){
					alert('无法连接服务器');
				}
			});
		});
		 
		$(document).on('click', '.undo', function(){
			var scheduleid = $(this).attr("data-id");
			var $this = $(this);
			$.ajax({
				url:'schedule/done.do',
				dataType:'json',
				tye:'get',
				data:{'id':scheduleid},
				success:function(datas){
					if(datas.result == 'success'){
						data[2].value = parseInt(data[2].value)+1;
						data[1].value = parseInt(data[1].value)-1;
						$this.parent().remove();
						myPie = new Chart(pie).Pie(data);
						$("#chdone").html(data[2].value);
						$("#chundone").html(data[1].value);
					}else{
						alert(datas.message);
					}
				},
				error:function(){
					alert('无法连接服务器');
				}
			});
		});
		 
		
		 $(".page").click(function(){
             var pageNo = $(this).attr("data-page");
             $("#pageNo").val(pageNo);
             $("#turnpage").submit();
         });
	});	
</script>
</body>
</html>