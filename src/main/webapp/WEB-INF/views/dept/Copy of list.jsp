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
		<form id="add">
			<input type="text" name="deptname" />
			<button id="add" type="button">添加</button>
		</form>
		
		
	</div>
</div>
<div class="container udMargin">
 	<form id="searchForm" action="/admin/dept/list.do" class="form-search" style="margin-bottom: 0px">
         <input type="hidden" value="${page.pageNo}" id="pageNo" name="p"/>
      
     </form>
</div>
<div class="container udMargin">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<td>部门</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty requestScope.page.items}">
                 <tr>
                    <td colspan="7"> <i class="fa fa-frown-o"></i> 没有任何记录</td>
                 </tr>
             </c:if>
			<c:forEach items="${requestScope.page.items}" var="dept">
				<tr>
					<td data-id="${dept	.id}">${dept.deptname}</td>
					<td>
						<a class="changedept" data-id="${dept.id}" href="javascript:;">修改</a>
						<a class="deldept" data-id="${dept.id}" href="javascript:;">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<input style="width:20px;" class="hide" name="deptname" placeholder="部门名字">
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
    
	
	   <form id="change" class="change hide">
		 <input class="dept" type="text" autofocus name="deptname">
		 <button class="changebtn" type="button" name="" data-id="">提交</button>
	   </form>

   
    <script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
    <script type="text/javascript">
     	
    	$(document).ready(function(){
    		var $form = $("#change");
    		
    		$(".deldpet").on("click", function(){
    			var id = $(this).attr("data-id");
    			
    		});
    		
        	$(".changedept").on("click", function(){
        		var id = $(this).attr("data-id");
        		var val = $("td[data-id="+id+"]").text();
        		window.location.href="/admin/dept/change.do?id=" + id;
        		/*$("td[data-id="+id+"]").html($form.show());
        		$form.children(".dept").val(val);
        		$form.children(".depid").val(id);
        		$form.children(".changebtn").attr("data-id", id);
        		$(".changedept").parent().hide();
        		$form.children("input").focus();*/
        		
        		/*$.ajax({
					url:"/admin/dept/change.do",
					type:"post",
					data:{
						"id":id,
						"deptname":$form.children("input").val()
					}
					success:function(json){
						if(json.result == "success"){
						   	window.location.href="";
						}else{
					   	alter("system message: " + json.message);
						}
					}
			});*/
        	});
        	
        	$("#add").on("click", function(){
        		var id = $(this).attr("data-id");
        		var val = $("td[data-id="+id+"]").text();
        		//window.location.href="/admin/dept/add.do?id=" + id;
        		/*$("td[data-id="+id+"]").html($form.show());
        		$form.children(".dept").val(val);
        		$form.children(".depid").val(id);
        		$form.children(".changebtn").attr("data-id", id);
        		$(".changedept").parent().hide();
        		$form.children("input").focus();*/
        		
        		$.ajax({
					url:"/admin/dept/add.do",
					type:"post",
					data:$("#add").serialize(),
					success:function(json){
						if(json.result == "success"){
						   	window.location.href="";
						}else{
					   	alter("system message: " + json.message);
						}
					}
			});
        	});
            /*
        	$(document).on("blur",".dept", function(){
        		  $(this).parent().parent().html($(this).val());
        		  $(this).attr("autofocus");
        		  $(".changedept").parent().show();
        		});
        	*/
        		$(".changebtn").on("click", function(){
        			var id = $(this).attr("data-id");
        			
        			$(this).parent().parent().html($(this).val());
        			$(".changedept").parent().show();
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