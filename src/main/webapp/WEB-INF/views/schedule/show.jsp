<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>    
<head>
	<meta charset=UTF-8>
	<title>show schecule</title>
	
	<link rel="stylesheet" href="/static/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="/static/css/font-awesome.min.css"/>
	<link rel="stylesheet" href="/static/css/layout3.css" />
	<link rel="stylesheet" href="/static/css/layout2.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="schedule" name="active"/>
</jsp:include>
<div style="width:60%; margin-left:30%;">
	<a class="btn btn-primary" href="/schedule/add.do">添加日程</a>
	<a class="btn btn-success" href="/schedule/fullcalendar.do">日历模式查看</a>
	<a class="btn btn-info" href="/schedule/sort/add.do">添加分组</a>
	<form action="/schedule/show.do" method="get">
	<select id="schstates" name="q_eq_state">
		<option id="optbtn" value=''>请选择</option>
		<option value="0" ${param.q_eq_state == 0 ? 'selected' :''}>未完成</option>
		<option value="1" ${param.q_eq_state == 1 ? 'selected' :''}>已完成</option>
		
	</select>
	<button class="btn">搜索</button>
	</form>
	
</div>

	<c:if  test="${param.q_eq_state !='1' && param.q_eq_state != '0'}">
	<div  style="width:70%; margin-left:20%;">
	<div>
	<ul class="ul">
		<li><span>已过期</span></li>
		<c:forEach items="${requestScope.overList}" var="over"> 
			<c:if test="${!over.schstates}">
			<li style="background-color:${over.sort.sortcolor}">
			<span class="font2">${over.schtitle}</span>
			<span class="font1 leftMargin">${over.scheduletime}</span>
			<span style="color:${over.sort.sortcolor}">&nbsp;&nbsp;类别： ${over.sort.sortname}</span>
			</li>
			</c:if>
		</c:forEach>
	</ul>
	</div>
	
	<div >
	<div class="udMargin divHeight" style="text-align:center;">
		<span class="centerMargin" style="text-align:center; margin-top:5px;">未过期</span>
	</div>
		<c:if test="${empty requestScope.unoverMap}">
			<tr>
           		<td colspan="8"> <i class="fa fa-frown-o"></i> 没有任何记录</td>
        	</tr>
		</c:if>
		<c:forEach items="${requestScope.unoverMap}" var="unoverList">
			<ul class="ul">
			
			<li><label style="background-color:#99bbbb; color:#f00;">
				<span class="timeshow">${unoverList.key}</span>
			</label>
			</li>
			<c:forEach items="${unoverList.value}" var="unover">
				<c:if test="${!unover.schstates}">
				<li >
				<span style="background-color:${unover.sort.sortcolor}" data-id="${unover.id}" class="spanBtn"></span>
				<span style="color:${unover.sort.sortcolor}">${unover.schtitle}</span>
				<span style="color:${unover.sort.sortcolor}">&nbsp;&nbsp;类别： ${unover.sort.sortname}</span>
				</li>
				</c:if>
			</c:forEach>
			</ul>
		</c:forEach>
		</div>
	  </div>
	  
	  </c:if>
	  <c:if test="${param.q_eq_state == 1 or param.q_eq_state == '0'}">
	  <div style="width:60%; margin-left:30%;">
	  <c:if test="${empty requestScope.schList}">
			<tr>
           		<td colspan="8"> <i class="fa fa-frown-o"></i> 没有任何记录</td>
        	</tr>
	</c:if>
	
	  <c:if test="${param.q_eq_state==1}">
	  	<span class="centerMargin" style="text-align:center; margin-top:5px;">已完成</span>
	  </c:if>
	  <c:if test="${param.q_eq_state=='0'}">
	  	<span class="centerMargin" style="text-align:center; margin-top:5px;">未完成</span>
	  </c:if>
	  	<c:forEach items="${requestScope.schList}" var="schlist">
	  	
			<ul class="ul">
				<li>
				<c:if test="${!schlist.schstates}">
				<span data-id="${schlist.id}" style="background-color:${schlist.sort.sortcolor}" class="spanBtn"></span>
				</c:if>
				<c:if test="${schlist.schstates}">
				<span data-id="${schlist.id}" style="background-color:${schlist.sort.sortcolor};" class="spandoBtn"></span>
				</c:if>
				<span style="color:${schlist.sort.sortcolor};">${schlist.schtitle}</span>
				</li>
				
			</ul>
		</c:forEach>
		</div>
	</c:if>


	<script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var $optbtn = $("#optbtn");
			var $spanbtn = $(".spanBtn");
			var $schstates = $("#schstates");
			
			$schstates.change(function(){
				
			});
			
			$(document).on("click", ".timeshow", function(){
				$(this).parent().parent().nextAll().toggle();
			});
			
			
			$(document).on("click", ".spandoBtn", function(){
				//console.log(22);
				var $scheduleid = $(this).attr("data-id");
				var $this = $(this);
				$.ajax({
					url: "/schedule/undo.do",
					data:{'id':$scheduleid},
					type: "get",
					cache:false,
					dataType:'json',
					success:function(data){
						if(data.result == 'success'){
							var length = $this.parent().parent().children().length;
							var $tempul = $this.parent().parent();
							$this.parent().remove();
							console.log("length: " +length);
							if(length == 2){
								$tempul.remove();
							}
						}else if(data.result == 'error'){
							alert('系统信息： ' + data.message);
						}else {
							console.log('error');
						}
					},
					error:function() {    
				         // view("异常！");    
				        alert("服务器未找到");    
				     }
				});
			});
			
			//点击实现昨晚功能
			$(document).on("click", ".spanBtn", function(){
				//console.log(22);
				var $scheduleid = $(this).attr("data-id");
				var $this = $(this);
				$.ajax({
					url: "/schedule/done.do",
					data:{'id':$scheduleid},
					type: "get",
					cache:false,
					dataType:'json',
					success:function(data){
						if(data.result == 'success'){
							var length = $this.parent().parent().children().length;
							var $tempul = $this.parent().parent();
							$this.parent().remove();
							console.log("length: " +length);
							if(length == 2){
								$tempul.remove();
							}
							
						}else if(data.result == 'error'){
							alert('系统信息： ' + data.message);
						}else {
							console.log('error');
						}
					},
					error:function() {    
				         // view("异常！");    
				        alert("服务器未找到");    
				     }
				});
				
			});
		});
		
	</script>
</body>
</html>