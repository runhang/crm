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
		<form id="addform">
			<input id="addinput" type="text" name="deptname" />
			<button id="addbtn" type="button">添加</button>
		</form>
		
		
	</div>
</div>
<div class="container udMargin">
 	<form id="searchForm" action="/admin/dept/list.do" class="form-search" style="margin-bottom: 0px">
         <input type="hidden" value="${page.pageNo}" id="pageNo" name="p"/>
      
     </form>
</div>
<div class="container udMargin">
	<table id="showtable" class="table table-striped table-bordered table-hover">
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
					<td data-id="${dept.id}">${dept.deptname}</td>
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
    

   <input style="width:12em;" class="hide" id="change" name="deptname">
   
    <script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
    <script type="text/javascript">
     	
    	$(document).ready(function(){
    		var $change = $("#change");
    		var tempval = null;
    		var tempid = null;
    		
    		
    		
        	$(document).on("click", ".changedept", function(){
        		var id = $(this).attr("data-id");
        		tempid = id;
        		var val = $("td[data-id="+id+"]").text();
        		tempval = val;
        		$change.val(tempval);
        		$change.show();
        		$("td[data-id="+id+"]").html($change); 
        		//$chagne.focus();
        		$(".changedept").hide();
        		$change.focus();
        	});
        	
        	$(document).on("blur", "#change", function(){
        		$change.hide();
        		if($(this).val() ==''){
        			$("td[data-id="+tempid+"]").html(tempval);
        		}else if($(this).val() == tempval){
        			$("td[data-id="+tempid+"]").html(tempval);
        		}else{
        			$.ajax({
    					url:"/admin/dept/change.do",
    					type:"post",
    					data:{'id':tempid,'deptname':$change.val()},
    					success:function(json){
    						if(json.result == "success"){
    							$("td[data-id="+tempid+"]").html(json.value);
    		        		
    						}else{
    							$("td[data-id="+tempid+"]").html(tempname);
    					   		alert("system message: " + json.message);
    						}
    					}
    				});
        		}
        		$(".changedept").show();
        	});
        	
        	
        	
        	$("#addbtn").on("click", function(){
        		//var id = $(this).attr("data-id");
        		//var val = $("td[data-id="+id+"]").text();
        		if($("#addinput").val() == ''){
        			alert("部门内容不能为空 ");
        		}else{
        		
        		$.ajax({
					url:"/admin/dept/add.do",
					type:"post",
					data:$("#addform").serialize(),
					success:function(json){
						if(json.result == "success"){
						   	//window.location.href="";
						   	$("#showtable").prepend(
						   		"<tr>"+
						   		"<td data-id="+json.value+">"+json.message+"</td>"+
						   		"<td>"+
						   		"<a class='changedept' data-id="+json.value+" href='javascript:;'>修改</a>"+
								"<a class='deldept' data-id="+json.value+" href='javascript:;'>删除</a>"+
								
						   		"</td>"+
						   		"</tr>"); 
						   	//$("#showtable").chidren();
						   	var length = $("#showtable tbody").children("tr").length;
						   	console.log($("#showtable tbody").children("tr").length);
						   	if(length > 10){
						   		var tr = $("#showtable tbody tr").last();
						   		tr.remove();
						   	}
						}else{
					   		alert("system message: " + json.message);
						}
					}
				});
        		}
        	});
            
        	$(document).on("click", ".deldept", function(){
        		var $id = $(this).attr("data-id");
        		$.ajax({
					url:"/admin/dept/del.do",
					type:"get",
					data:{'p':'${page.pageNo}',"id":$id},
					success:function(json){
						if(json.result == "success"){
							$("td[data-id="+$id+"]").parent().remove();
						   	//window.location.href="";
						   	if(json.message != "" && json.value != ""){
						  
						   	$("#showtable").append(
						   		"<tr>"+
						   		"<td data-id="+json.value+">"+json.message+"</td>"+
						   		"<td>"+
						   		"<a class='changedept' data-id="+json.value+" href='javascript:;'>修改</a>"+
								"<a class='deldept' data-id="+json.value+" href='javascript:;'>删除</a>"+
								
						   		"</td>"+
						   		"</tr>"); 
						   	}
						}else{
					   		alert("system message: " + json.message);
						}
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