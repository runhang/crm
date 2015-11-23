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
	
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="main" name="active"/>
</jsp:include>

<div class="containter">
	<h2>hello</h2>
	<a class="btn" href="/cust/addcust.do">添加</a>
	<a class="btn" href="/cust/addcust.do">添加</a>
</div>
<div class="contaienr" style=" width:50%;margin:5px auto 5px auto;">
	<div class="content form-actions">
    	<div class="pub top">
      		<span>显示</span>
      		<form id="searchForm" action="/cust/list.do" class="form-search" style="margin-bottom: 0px">
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
   			<div>
   			<i class="fa fa-meh-o" style="color: #f8b9b7"></i>
			<span>${cust.custname}</span>
            <span class="pull-right">${cust.company.custname}</span>
            <br/>
            <span style="margin-left:18px;">${cust.tel}</span>
        </div>
   		</c:forEach>	
   		<div>
   			<i class="fa fa-meh-o" style="color: #f8b9b7"></i>
			<span>乔布斯</span>
            <span class="pull-right">苹果</span>
            <br/>
            <span style="margin-left:10px;">3883833838</span>
        </div>
    </div>
  </div>


<div class="pagination pagination-right content">
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
</div>

	<script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>

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