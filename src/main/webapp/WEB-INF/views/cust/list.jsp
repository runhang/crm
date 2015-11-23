<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>insert new title</title>
	<link rel="stylesheet" href="/static/css/layout2.css" />
	<link rel="stylesheet" href="/static/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="/static/css/font-awesome.min.css"/>
	<link rel="stylesheet" href="/static/css/layout3.css" />
	
</head>
<body>
	<script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="show" name="active"/>
</jsp:include>

<div class="row">
<div class="offset1 span3">
	<span>最新动态</span>
	<ul class="ul">
		<li><a href="/cust/list.do"><span class="span2">联系人</span></a></li>
		<li><a href="#"><span class="span2">日程安排(今天的任务)</span></a></li>
		<li><a href="#"><span class="span2">业务机会(当前的业务)</span></a></li>
		<li><a href="#"><span class="span2">搜索</span></a></li>
		
	</ul>
</div>
<div class="span7" style="width:730px;">
	<div class="form-actions">
    	<div class="pub top">
      		<form id="searchForm" action="/cust/list.do" class="form-search" style="margin-bottom: 10px">
         		<span>显示</span>
         		<input type="hidden" value="${page.pageNo}" id="pageNo" name="p"/>
         		<select name="q_eq_flags" id="">
        		   <option value="">所有的联系人</option>
        		   <option value="0">所有的公司</option>
        		   <option value="1">所有的个人</option>
          		</select>
         		<input type="text" name="q_like_custname" placeholder="账号名称"/>
         		<button class="btn"><i class="fa fa-search"></i> 搜索</button>
     		</form>
   		<c:forEach items="${requestScope.page.items}" var="cust">
   			<div class="cust">
   			<c:choose>
   			<c:when test="${cust.flags}">
   				<i class="fa fa-user" style="color: #f8b9b7;"></i>
   			</c:when>
   			<c:otherwise>
   				<i class="fa fa-home fa-fw" style="color:#f8b9b7;"></i>
   			</c:otherwise>
   			</c:choose>
			<a href="/cust/event.do?id=${cust.id}"><span>${cust.custname}</span></a>
            <span class="pull-right">${cust.company.custname}</span>
            <br/>
            <span style="margin-left:20px;">${cust.tel}</span>
        </div>
   		</c:forEach>	
   		
    </div>
  </div>
   <!-- 分页 -->
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
<!-- span6 end -->
<!-- right layout -->
	<div class="span3" >
		<div class="btn-group btn-group-vertical">
		<h2>hello</h2>
		<ul class="ul">
	  	 <li><a class="btn btn-success udMargin" href="/cust/addcust.do">添加客户</a></li>
	  	 <li><a class="btn btn-info udMargin" href="/cust/addcom.do">添加公司</a></li>
		</ul>
	</div>
	</div>
<!-- end -->
</div>

	
 <script>
     $(function(){
        $(".page").click(function(){
        	var pageNo = $(this).attr("data-page");
            $("#pageNo").val(pageNo);
            $("#searchForm").submit();
        });
    });
 </script>  

</body>
</html>