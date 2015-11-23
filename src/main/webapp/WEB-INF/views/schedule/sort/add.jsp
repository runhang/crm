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
	<link rel="stylesheet" href="/static/js/colorpicker/css/bootstrap-colorpicker.min.css" />
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
	
	<div class="loginwarpper" style="width:600px;">
		<form id="addform" class="add">
			<input id="addsortname" type="text" name="sortname" />
			<input id="inputcolor" disabled type="text" name="sortcolor" />
			<span id="sortcolors"  style="display:inline-block; cursor:pointer; background-color:#555; width:25px; height:25px;">
			</span>
			<button id="add" type="button">添加</button>
		</form>
		
		
	</div>
</div>
<div class="container udMargin">
 	<form id="searchform" action="/admin/dept/list.do" class="form-search" style="margin-bottom: 0px">
         <input type="hidden" value="${page.pageNo}" id="pageNo" name="p"/>
      
     </form>
</div>
<div class="container udMargin">
	<table id="showtable" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>分类名称</th>
				<th>分类颜色</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty requestScope.sortList}">
                 <tr>
                    <td colspan="7"> <i class="fa fa-frown-o"></i> 没有任何记录</td>
                 </tr>
             </c:if>
			<c:forEach items="${requestScope.sortList}" var="sort">
				<tr>
					<td data-id="${sort.id}">${sort.sortname}</td>
					<td data-color-id="${sort.id}" style="color:${sort.sortcolor};">${sort.sortcolor}</td>
					<td>
						<a class="changesortname" data-id="${sort.id}" href="javascript:;">修改分类名</a>
						<a class="changesortcolor" data-id="${sort.id}" href="javascript:;">修改分类颜色</a>
						<a class="delsort" data-id="${sort.id}" href="javascript:;">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
 </div>
<input style="width:20px;" class="hide" name="deptname" placeholder="部门名字">
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
</div>
  -->  

   <input style="width:12em;" class="hide" id="changename" name="sortname">
   <input style="width:12em;" class="hide" id="changecolor" name="sortcolor"/>
  
    <script src="/static/js/jquery-1.11.3.min.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
	<script src="/static/js/colorpicker/js/bootstrap-colorpicker.min.js"></script>
    <script type="text/javascript">
     	
    	$(document).ready(function(){
    		var $changename = $("#changename");
    		var $changecolor = $("#changecolor");
    		var tempval = null;
    		var tempcolor = null;
    		var tempid = null;
    		
    		
    		$('#sortcolors').colorpicker().on('changeColor.colorpicker', function(event){
  			  $("#inputcolor").val(event.color.toHex());
    		});
    		
    		
    		$(document).on("focus", "#changecolor", function(){
    			$(this).colorpicker();
    		});
    		
      		
    		
    		$(document).on("click", ".changesortcolor", function(){
        		var id = $(this).attr("data-id");
        		tempid = id;
        		var val = $("td[data-color-id="+id+"]").text();
        		tempval = val;
        		$changecolor.val(tempval);
        		$changecolor.show();
        		$("td[data-color-id="+id+"]").html($changecolor); 
        		//$chagne.focus();
        		$(".changesortname").hide();
        		$(".changesortcolor").hide();
        		$changecolor.focus();
        	});
    		
        	$(document).on("click", ".changesortname", function(){
        		var id = $(this).attr("data-id");
        		tempid = id;
        		var val = $("td[data-id="+id+"]").text();
        		tempval = val;
        		$changename.val(tempval);
        		$changename.show();
        		$("td[data-id="+id+"]").html($changename); 
        		//$chagne.focus();
        		$(".changesortname").hide();
        		$(".changesortcolor").hide();
        		$changename.focus();
        	});
        	
        	$(document).on("blur", "#changecolor", function(){
        		$changecolor.hide();
        		if($(this).val() ==''){
        			$("td[data-color-id="+tempid+"]").html(tempval);
        		}else if($(this).val() == tempval){
        			$("td[data-color-id="+tempid+"]").html(tempval);
        		}else{
        			$.ajax({
    					url:"/schedule/sort/change.do",
    					type:"post",
    					data:{'id':tempid,'sortcolor':$changecolor.val()},
    					success:function(json){
    						if(json.result == "success"){
    							$("td[data-color-id="+tempid+"]").html(json.value);
    		        		
    						}else{
    							$("td[data-color-id="+tempid+"]").html(tempval);
    					   		alert("system message: " + json.message);
    						}
    					}
    				});
        		}
        		$(".changesortname").show();
        		$(".changesortcolor").show();
        	});
        	
        	$(document).on("blur", "#changename", function(){
        		$changename.hide();
        		if($(this).val() ==''){
        			$("td[data-id="+tempid+"]").html(tempval);
        		}else if($(this).val() == tempval){
        			$("td[data-id="+tempid+"]").html(tempval);
        		}else{
        			$.ajax({
    					url:"/schedule/sort/change.do",
    					type:"post",
    					data:{'id':tempid,'sortname':$changename.val()},
    					success:function(json){
    						if(json.result == "success"){
    							$("td[data-id="+tempid+"]").attr("class",json.message);
    							$("td[data-id="+tempid+"]").html(json.message);
    		        		
    						}else{
    							$("td[data-id="+tempid+"]").html(tempval);
    					   		alert("system message: " + json.message);
    						}
    					}
    				});
        		}
        		$(".changesortname").show();
        		$(".changesortcolor").show();
        	});
        	
        	$(document).on("click", ".delsort", function(){
        		var $sortid = $(this).attr("data-id");
        		var tempsort = $(this);
        			$.ajax({
    					url:"/schedule/sort/del.do",
    					type:"get",
    					data:{'id':$sortid},
    					success:function(json){
    						if(json.result == "success"){
    							$("td[data-id="+$sortid+"]").parent().remove();
    		        		
    						}else{
    							alert(json.message);
    						}
    						var sortid = null;
    					}
    					
    				});
        		
        	});
        	
        	
        	$("#add").on("click", function(){
        		//var id = $(this).attr("data-id");
        		//var val = $("td[data-id="+id+"]").text();
        		$("#addsortname").next().next().next().next().remove();
        		if($("#addsortname").val()==''){
        			$("#addsortname").parent().append(
        					"<span class='errorSpan'>请填写名称</span>")
        		}else{
        		 $.ajax({
					url:"/schedule/sort/add.do",
					type:"post",
					data:{"sortname":$("#addsortname").val(),"sortcolor":$("#inputcolor").val()},
					success:function(json){
						if(json.result == "success"){
						   	//window.location.href="";
						   	$("#showtable").prepend(
						   		"<tr>"+
						   		"<td data-id="+json.value+">"+json.message+"</td>"+
						   		"<td data-color-id="+json.value+" style='color:"+json.color+"'>"+json.color+"</td>"+
						   		"<td>"+
						   		"<a class='changesortname' data-id="+json.value+" href='javascript:;'>修改分类名</a>"+
						   		"<a class='changesortcolor' data-id="+json.value+" href='javascript:;'>修改分类颜色</a>"+
						   		"<a class='delsort' data-id="+json.value+" href='javascript:;'>删除</a>"+
								
						   		"</td>"+
						   		"</tr>"); 
						   	//$("#showtable").chidren();
						   	//var length = $("#showtable tbody").children("tr").length;
						   //	console.log($("#showtable tbody").children("tr").length);
						   //	if(length > 10){
						   		//var tr = $("#showtable tbody tr").last();
						   		//tr.remove();
						   //	}
						}else{
					   		alert("system message: " + json.message);
						}
					}
				  });
        		}
        	});
            
        	$
        	/*
        	$(document).on("click", ".delsort", function(){
        		var $id = $(this).attr("data-id");
        		$.ajax({
					url:"/schedule/sort/del.do",
					type:"get",
					data:{"id":$id},
					success:function(json){
						if(json.result == "success"){
							$("td[data-id="+$id+"]").parent().remove();
						   	//window.location.href="";
						   	if(json.message != "" && json.value != ""){
						  
						   	$("#showtable").append(
						   		"<tr>"+
						   		"<td data-id="+json.value+">"+json.message+"</td>"+
						   		"<td data-id="+json.value+" style='color:"+json.color+"'>"+json.color+"</td>"+
						   		"<td>"+
						   		"<a class='changesort' data-id="+json.value+" href='javascript:;'>修改</a>"+
								"<a class='delsort' data-id="+json.value+" href='javascript:;'>删除</a>"+
						   		"</td>"+
						   		"</tr>"); 
						   	}
						}else{
					   		alert("system message: " + json.message);
						}
					}
				});
        	});
               */  
        	
         	$(".page").click(function(){
                var pageNo = $(this).attr("data-page");
             	$("#pageNo").val(pageNo);
             	$("#searchForm").submit();
         	});
    });
        	 
       
        
        
    </script>
</body>
</html>