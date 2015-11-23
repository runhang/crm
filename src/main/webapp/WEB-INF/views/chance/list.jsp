<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>insert new title</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="/static/css/layout2.css" />
	<link rel="stylesheet" href="/static/js/picker/daterangepicker-bs2.css" />
	<style type="text/css">
		.table th, .table td {
    		text-align: center;
		}
	</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="chance" name="active"/>
</jsp:include>
<div class="container udMargin">
	<div class="loginwarpper">
		<a class="btn btn-primary" href="/chance/add.do">添加机会</a>
		<a class="btn btn-primary" href="/schedule/add.do?chanceid=${chance.id}" >添加日程</a>
	</div>
	<div>
		<h3><f:formatNumber value="${requestScope.totalMoney}" type="currency" pattern="¥.0#"/>元</h3>
		
	</div>
</div>
<div class="container udMargin">
<!-- form action="/chance/list.do" -->
 	<form id="searchform" action="/chance/list.do"  class="form-search" style="margin-bottom: 0px">
         <input type="hidden" value="${page.pageNo}" id="pageNo" name="p"/>
         <!-- >= -->
         <input type="hidden" id="getime" name="q_ge_chcreatetime" value="">
         <!-- <= -->
         <input type="hidden" id="letime" name="q_le_chcreatetime" value="">
         <select name="q_eq_chstate">
			<option value="">所有状态</option>
			<option value = "0" ${param.q_eq_chstate == 0 ? 'selected' : ''}>跟踪</option>
			<option value = "1" ${param.q_eq_chstate == 1 ? 'selected' : ''}>成功</option>
			<option value = "2" ${param.q_eq_chstate == 2 ? 'selected' : ''}>失败</option>
			<option value = "3" ${param.q_eq_chstate == 3 ? 'selected' : ''}>作废</option>
		 </select>
		 
         <input id="repertrange" placeholder="daterangetime" value="${param.q_le_chcreatetime}"/><i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
		 <!--  
         <select id="ctime">
               <option value="0">--时间查询--</option>
               <option value="1" >最近一周</option>
               <option value="2">最近一月</option>
               <option value="3">最近六个周</option>
               <option value="4">最近三个月</option>
               <option value="5">最近一年</option>
         </select>
         -->
         <input type="text" name="q_like_chtitle" value="${q_like_chtitle}" placeholder="标题"/>
         <button id="search" type="button" class="btn"><i class="fa fa-search"></i> 搜索</button>
     </form>
     
</div>
<div class="container udMargin">
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
		<tbody>
			<c:if test="${empty requestScope.page.items}">
                 <tr>
                    <td colspan="7"> <i class="fa fa-frown-o"></i> 没有任何记录</td>
                 </tr>
             </c:if>
			<c:forEach items="${requestScope.page.items}" var="chance">
				<tr data-id="${chance.id}">
				<td>${chance.chtitle}</td>
				<td>${chance.customer.custname}</td>
				<td>${chance.account.username}</td>
				<td style="width:300px;"><p>${chance.chcontent}</p></td>
				<td>${chance.chcreatetime}</td>
				<td>
				<f:formatNumber value="${chance.chmoney}" type="currency" pattern="¥.0#"/>
				</td>
				
				<c:choose>
				<c:when test="${chance.chstate ==0}">
				<td>跟进</td>
				</c:when>
				<c:when test="${chance.chstate==1}">
				<td>成功</td>
				</c:when>
				<c:when test="${chance.chstate==2}">
				<td>失败</td>
				</c:when>
				<c:otherwise>
				<td>作废</td>
				</c:otherwise>
				</c:choose>
				<td><!-- href="/chance/event/add.do?id=${chance.id}" -->
					
					<!--  <input type="hidden" name="id" value="${chance.id}"/>-->
					<input type="hidden" name="q_eq_evchanceid" value="${chance.id}"/>
					<a class="btn btn-primary" href="/chance/event/add.do?q_eq_evchanceid=${chance.id}" >添加机会事件</a>
					
					
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
    <script src="/static/js/jquery.validate.min.js"></script>
    <!--  
    <script src="/static/js/moment-with-locales.js"></script>
    -->
    <script src="/static/js/picker/moment.min.js"></script>
    <script src="/static/js/picker/daterangepicker.js"></script>
   
    <script type="text/javascript">
    
     	
    $(document).ready(function(){
    	var $search = $("#search"); 
        var $ctime = $("#ctime");
        
        //时间
        
        $('#repertrange').daterangepicker({
        	format: 'YYYY-MM-DD',
        	timePicker:true,
        	//startDate: moment().subtract(29, 'days'),
           // endDate: moment(),
        	ranges: {
                '今天': [moment(), moment()],
                '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '7天以内': [moment().subtract(6, 'days'), moment()],
                '30天以内': [moment().subtract(29, 'days'), moment()],
                '3个月以内': [moment().subtract(3, 'month').startOf('month'), moment()],
                '6个月以内': [moment().subtract(6, 'month').startOf('month'), moment()]
             },
             locale: {
                 applyLabel: '确定',
                 cancelLabel: '取消',
                 fromLabel: '从',
                 toLabel: '到',
                 customRangeLabel: '用户自定义时间',
                 daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
                 monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月',
                              '八月','九月', '十月', '十一月', '十二月'],
                 firstDay: 1
             }
         
        }, function(start, end, label) {
        	
            console.log(label);
        	$("#getime").val(start.format('YYYY-MM-DD'));
        	$("#letime").val(end.format('YYYY-MM-DD'));
            
        });
        //end
        
        /*
    	$search.on("click", function(){
    		var ctime = $ctime.val();
    		console.log(ctime);
    		if(ctime == 1){
    			$("#op1").val(moment().subtract(7, 'days').format("YYYY-MM-DD HH:MM:SS"));
    			console.log(moment().subtract(7, 'days').format("YYYY-MM-DD HH:MM:SS"));
    		}else if(ctime==2){
    			$("#op2").val(moment().subtract(1, 'months').format("YYYY-MM-DD HH:MM:SS"));
    			console.log(moment().subtract(1, 'months').format("YYYY-MM-DD HH:MM:SS"));
    		}else if(ctime==3){
    			$("#op3").val(moment().subtract(6, 'weeks').format("YYYY-MM-DD HH:MM:SS"));
    			console.log(moment().subtract(6, 'weeks').format("YYYY-MM-DD HH:MM:SS"));
    		}else if(ctime==4){
    			$("#op4").val(moment().subtract(3, 'months').format("YYYY-MM-DD HH:MM:SS"));
    			console.log(moment().subtract(3, 'months').format("YYYY-MM-DD HH:MM:SS"));
    		}else if(ctime==5){
    			$("#op5").val(moment().subtract(1, 'years').format("YYYY-MM-DD HH:MM:SS"));
    			console.log(moment().subtract(1, 'years').format("YYYY-MM-DD HH:MM:SS"));
    		}
    		
    		$("#searchform").submit();
    	});
        */
        $search.on("click", function(){
        	$("#searchform").submit();
    	});    
        	
                 
        	
         $(".page").click(function(){
             var pageNo = $(this).attr("data-page");
             $("#pageNo").val(pageNo);
             $("#searchform").submit();
         });
    });
        	 
       
        
        
    </script>
</body>
</html>