<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<title>edit customer</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="/static/js/edit/styles/simditor.css" />
	<link rel="stylesheet" href="/static/css/layout3.css" />
	<link rel="stylesheet" href="/static/js/datetimepicker/css/bootstrap-datetimepicker.min.css" />
	
	<style>
	  .simditor-body{
		max-height:260px;
		width:300px;
	}
	 .simditor{
		width:300px;	 
	 }
	 .simditor .simditor-body {
  		padding: 0px;
  		width:300px;
 		min-height: 260px;
	}
	</style>
</head>
<body>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="addschedule" name="active"/>
</jsp:include>

<div class="container">
	<form method="post" action="/schedule/add.do" id="subform" class="form-horizontal">
		<input type="hidden" name="custid" value="${param.custid}"/>
		<input type="hidden" name="chanceid" value="${param.chanceid}"/>
		
		<input name="com" type="hidden" value="${param.comid}" />
		<div class="control-group">
			<label  class="control-label">日程：</label>
			<div class="controls">
				<input name="schtitle" placeholder="日程" type="text"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">日程开始时间:</label>
			<div class="controls">
			 <div id="from_datetime" class="input-append date form_datetime">
    		   <input id="schfromtime" name="schfromtime" size="16" type="text" value="" readonly>
    		   <span class="add-on"><i class="icon-th"></i></span>
			 </div>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">日程完成时间:</label>
			<div class="controls">
			 <div id="to_datetime" class="input-append date form_datetime">
    		   <input id="scheduletime" name="scheduletime" size="16" type="text" value="" readonly>
    		   <span class="add-on"><i class="icon-th"></i></span>
			 </div>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">分类</label>
			<div class="controls">
				<select name="schsort" >
					<option value="0">默认</option>
					<c:forEach items="${sortList}" var="sort">
						<option value="${sort.id}">${sort.sortname}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">是否公开</label>
			<div class="controls">
				<select name="schviews" >
					<option value="no">私有</option>
					<option value="yes">公开</option>
				</select>
			</div>
		</div>
		
		<div class="control-group">
			
			<div class="controls">
				<button class="btn btn-primary" id="subtn" type="button">提交</button>
				<button class="btn btn-primary" type="button">保存</button>
			</div>
		</div>
	</form>
	
	<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
	
	<script src="/static/js/jquery.validate.min.js"></script>
	<script src="/static/js/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript"  src="/static/js/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	
	<script>
	
		$(document).ready(function(){
			var $subtn = $("#subtn");
			var $subform = $("#subform");
			var $schfromtime = $("#schfromtime")
			var $scheduletime = $("#scheduletime");
			var $title = $("input[name=schtitle]");
			
			$.fn.datetimepicker.dates['zh-CN'] = {
					days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
					daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
					daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
					months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
					monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
					today: "今天",
					meridiem: ["上午", "下午"]
			};
			
			$("#from_datetime").datetimepicker({
		        format: "yyyy-mm-dd hh:ii",
		        autoclose: true,
		        todayBtn: true,
		        pickerPosition: "bottom-left",
		        language:'zh-CN'
		    });
			
			$("#to_datetime").datetimepicker({
		        format: "yyyy-mm-dd hh:ii",
		        autoclose: true,
		        todayBtn: true,
		        pickerPosition: "bottom-left",
		        language:'zh-CN'
		    });
			
			
			$subtn.bind("click", function(){
				console.log(111);
				$scheduletime.next().next().remove();
				$title.next().remove();
				if($scheduletime.val() != '' && $schfromtime.val() != ''
						&& $title.val() != ''){
					if($schfromtime.val() <= $scheduletime.val()){
						$subform.submit();
					}
				}else{
					if($title.val() == ''){
						$title.parent().append("<span class='errorSpan'>必填项</span>");
					}
					if($scheduletime.val() ==''){
					$scheduletime.parent().append("<span class='errorSpan'>必填项</span>");
				
					}
				}
			});
			
	
		});
	</script>
</div>

</body>
</html>