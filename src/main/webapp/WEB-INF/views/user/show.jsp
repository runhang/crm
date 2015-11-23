<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>insert new title</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="/static/css/layout2.css" />
	<style type="text/css">
		.table th, .table td {
    		text-align: center;
		}
	</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="main" name="active"/>
</jsp:include>
<div class="container udMargin">
	<div class="loginwarpper">
		<a class="btn btn-primary" href="/admin/dept/list.do">部门管理</a>
		<a class="btn btn-warning" href="/admin/dept/change.do">修改部门</a>
		<a class="btn btn-warning" href="/admin/adduser.do">添加用户</a>
		
	</div>
</div>
<div class="container udMargin">
 	<form id="searchForm" action="/admin/list.do" class="form-search" style="margin-bottom: 0px">
         <input type="hidden" value="${page.pageNo}" id="pageNo" name="p"/>
         <select name="q_eq_deptid">
               <option value="">--选择部门--</option>
            <c:forEach items="${deptList}" var="dept">
               <option value="${dept.id}" ${q_eq_deptid == dept.id ? 'selected' : ''}>${dept.deptname}</option>
            </c:forEach>
         </select>
         <input type="text" name="q_like_username" value="${q_like_username}" placeholder="账号名称"/>
         <button class="btn"><i class="fa fa-search"></i> 搜索</button>
     </form>
</div>
<div class="container udMargin">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<td>账号</td>
				<td>所属部门</td>
				<td>创建时间</td>
				<td>最后登录时间</td>
				<td>最后登录ip</td>
				<td>状态</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty requestScope.page.items}">
                 <tr>
                    <td colspan="7"> <i class="fa fa-frown-o"></i> 没有任何记录</td>
                 </tr>
             </c:if>
			<c:forEach items="${requestScope.page.items}" var="account">
				<tr data-id="${account.id}">
				<td>${account.username}</td>
				<td>${account.dept.deptname}</td>
				<td>${account.createtime }</td>
				<td>${account.lastlogintime}</td>
				<td>${account.lastloginip}</td>
				<td data-id="${account.id}">
				
				<c:choose>
					<c:when test="${account.useable}">
						可用
					</c:when>
					<c:otherwise>
						不可用
					</c:otherwise>
				</c:choose>
				</td>
				<td>
					
					<c:choose>
					<c:when test="${account.useable}">
						<a class="banuser" href="javascript:;" data-id="${account.id}">禁用用户</a>
					</c:when>
					<c:otherwise>
						<a class="activeuser" href="javascript:;" data-id="${account.id}">激活用户</a>
					</c:otherwise>
					</c:choose>
					&nbsp;&nbsp;
					<a class="deluser" href="javascript:;" data-id="${account.id}">删除</a>
					&nbsp;&nbsp;
					<a class="resetpass" href="javascript:;" data-id="${account.id}">重置密码</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

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
</div>
    

   
    <script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
    <script type="text/javascript">
     	
    	$(document).ready(function(){
    		var $banuser = $(".banuser"); 
        	var $activeuser = $(".activeuser");
        	var $resetpass = $(".resetpass");
        	//重置密码
        	$resetpass.on("click", function(){
        		var id = $(this).attr("data-id");
        		$.get("/admin/user/resetpass.do", {"id":id}, function(data){
        			if(data.result == "success"){
        				alert("密码重置成功");
        			}else{
        				alert("系统提示："  + data.message);
        			}
        		});
        	});
        	
        	//删除用户
        	$(".deluser").on("click", function(){
        		var id = $(this).attr("data-id");
        		$.get("/admin/user/deluser.do", {"id":id}, function(data){
        			if(data.result == "success"){
        				$("tr[data-id="+id+"]").remove();
        				
        				
        			}else{
        				alert("系统提示："  + data.message);
        			}
        		});
        	});
        	
        	//禁用用户
       		 $(document).on("click",".banuser", function(){
       		 	var $a = $(this);
                var id = $a.attr("data-id");
                $.get("/admin/user/banuser.do",{"userid":id},function(data){
                    if(data.result == "success") {
                   	 $a.attr("class", "activeuser");
                   	 $a.html("激活用户");
                   	 $("td[data-id="+id+"]").html("不可用");
                   	// $(this).off("click", function(){});
                   	 alert(data.message);
                    }else {
                        alert("系统提示："  + data.message);
                    }
                  });
                
                });
       	 	//激活用户
         	$(document).on("click", ".activeuser", function(){
         	 	var $ac = $(this);
         	 	var id = $ac.attr("data-id");
             	$.get("/admin/user/activeuser.do",{"userid":id},function(data){
                 if(data.result == "success") {
                	 $ac.attr("class", "banuser");
                	 $ac.html("禁用用户");
                   	 $("td[data-id="+id+"]").html("可用");
                	 
                     alert(data.message);
                 } else {
                     alert("系统提示："  + data.message);
                 }
             });
         });
               
        	
                 
        	
         $(".page").click(function(){
             var pageNo = $(this).attr("data-page");
             $("#pageNo").val(pageNo);
             $("#searchForm").submit();
         });
    });
        	 
       
        
        
    </script>
</body>
</html>