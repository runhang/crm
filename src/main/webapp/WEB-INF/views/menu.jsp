<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
  
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>menu</title>
	<link rel="stylesheet" href="/static/css/basic.css" />
	<link rel="stylesheet" href="/static/css/layout2.css" />
<body>
	<div class="navbar .navbar-static-top navbar-inverse">
		<div class="navbar-inner">
			<a style="margin:0px;padding:0px;" class="brand logoimg" href="/index.do"></a>
			<ul class="nav">
				<c:if test="${sessionScope.account.grade == 2}">
					<li<c:if test="${param.active == 'main'}"> class="active" </c:if>>
					<a href="/index.do">首页</a>
					</li>
				</c:if>
				<c:if test="${sessionScope.account.grade == 0}">
					<li<c:if test="${param.active == 'main'}"> class="active" </c:if>>
					<a href="/index.do">首页</a>
					</li>
					<li<c:if test="${param.active == 'user'}"> class="active" </c:if>>
					<a href="/admin/user/list.do">用户列表</a>
					</li>
					<li<c:if test="${param.active == 'dept'}"> class="active" </c:if>>
					<a href="/admin/dept/list.do">部门管理</a>
					</li>
				</c:if>
				<c:if test="${not empty sessionScope.account}">
				<li <c:if test="${param.active == 'show'}"> class="active" </c:if>>
				<a href="/cust/list.do">展示</a>
				</li>
				<li <c:if test="${param.active == 'chance'}"> class="active" </c:if>>
				<a href="/chance/list.do">机会</a>
				</li>
				<li <c:if test="${param.active == 'schedule'}"> class="active" </c:if>>
				<a href="/schedule/show.do">日程安排</a>
				</li>
				<li<c:if test="${param.active == 'addcust'}"> class="active" </c:if>>
    			  	<a href="/cust/addcust.do">添加客户</a>
    			</li>
    			<li <c:if test="${param.active == 'addcom'}"> class="active" </c:if>>
    			  	<a href="/cust/addcom.do">添加公司</a>
    			</li>
    			</c:if>
				<c:if test="${empty sessionScope.account}">
				<li <c:if test="${param.active == 'login'}"> class="active" </c:if>>
				<a href="/user/login.do">登陆</a>
				</li>
				</c:if>
				<!--<c:if test="${sessionScope.account.grade == 2}">-->
    			
    			<!--</c:if>-->
				
			</ul>
		<c:choose>
		  <c:when test="${not empty sessionScope.account}">	
		    <ul class="nav pull-right">
    		   <li class="dropdown" role="button">
    			<a class="dropdown-toggle" data-toggle="dropdown"  href="javascript:;">${sessionScope.account.username}<b class="caret"></b></a>
    			<ul  class="dropdown-menu widthMenu" role="menu">
    			  <c:if test="${sessionScope.account.grade == 2}">
    			  	<li><a href="/admin/user/changepass.do">修改密码：</a></li>
    			  </c:if>
    			  <c:if test="${sessionScope.account.grade == 0}">
    			  	<li><a href="/cust/addcust.do">设置：</a></li>
    			  </c:if>
    			  <li><a href="/logout.do">exit</a></li>
    			</ul>
    		   	</li>	
    	 	</ul>
		  </c:when>
		  <c:otherwise>
		   <div class="pull-right ">
    		<h5 style="margin:5px;" >用户未登录</h5>
    		<a href="/user/login.do" style="cursor:pointer;">请登陆</a>
    	   </div>
		  </c:otherwise>
		</c:choose>
			
		</div>
	</div>
	

</body>
</html>