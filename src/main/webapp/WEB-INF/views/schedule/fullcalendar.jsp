<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
<link rel="stylesheet" href="/static/js/picker/daterangepicker-bs2.css" />
<link href='/static/js/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='/static/js/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<link rel="stylesheet" type="text/css" href="/static/js/edit/styles/simditor.css" />
<link rel="stylesheet" href="/static/css/layout3.css" />
<link rel="stylesheet" href="/static/js/datetimepicker/css/bootstrap-datetimepicker.min.css" />

<script src='/static/js/fullcalendar/lib/jquery.min.js'></script>
<script src='/static/js/fullcalendar/lib/moment.min.js'></script>
<script src='/static/js/fullcalendar/fullcalendar.min.js'></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"  src="/static/js/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	
<script>

	$(document).ready(function() {
		
		
		
		
		var eve = [
					{
						title: 'All Day Event',
						start: '2015-07-01'
					},
					{
						title: 'Long Event',
						start: '2015-07-07',
						end: '2015-07-10'
					},
					{
						id: 999,
						title: 'Repeating Event',
						start: '2015-07-09T16:00:00'
					},
					{
						id: 999,
						title: 'Repeating Event',
						color:"color:#f00",
						start: '2015-07-16T16:00:00'
					},
					{
						title: 'Conference',
						start: '2015-02-11',
						end: '2015-07-13'
					},
					{
						title: 'Meeting',
						start: '2015-07-12 10:30:00',
						end: '2015-07-12 12:30:00'
					},
					{
						title: 'Lunch',
						start: '2015-02-12 12:00:00'
					},
					{
						title: 'Meeting',
						start: '2015-07-12 14:30:00'
					},
					{
						title: 'Happy Hour',
						start: '2015-02-12 17:30:00'
					},
					{
						title: 'Dinner',
						start: '2015-07-12T20:00:00'
					},
					{
						title: 'Birthday Party',
						start: '2015-07-13T07:00:00',
						color: '#f00'
					},
					{
						title: 'Click for Google',
						url: 'http://google.com/',
						start: '2015-07-05'
					}
				];
			
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,basicWeek,basicDay'
			},
			defaultDate: '2015-07-01 12:01',
			editable: true,
			
			eventLimit: true, // allow "more" link when too many events
			
			events:'/api/schedule/show.json',
			
			selectable : true,
			
			selectHelper: true,
			select: function(start, end) {
				//var title = prompt('Event Title:');
				$('#myModal').modal({
					  keyboard: false
				});
				console.log(start);
				console.log(end);
				event.stopPropagation();
				$("input[name=schfromtime]").val(moment(start._d).format("YYYY-MM-DD HH:mm"));
				$("input[name=scheduletime]").val(moment(end._d).format("YYYY-MM-DD HH:mm"));
				var eventData;
				/*if (title) {
					eventData = {
						title: title,
						start: start,
						end: end
					};
				$.ajax({
					url:'/api/scheudle/add.do',
					type:'json',
					dataType:'json',
					success:function(data){
						
					},
					error:function(){
						console.log("error");
					}
				});
					console.log(moment(start._d));
					$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
				}*/
				$('#calendar').fullCalendar('unselect');
			},

			
			/*dayClick: function(date, jsEvent, view) {
				$('#myModal').modal({
					  keyboard: false
				});
				event.stopPropagation();
				console.log(moment(date._d).format("YYYY-MM-DD HH:mm"));
				$("input[name=scheduletime]").val(moment(date._d).format("YYYY-MM-DD HH:mm"));
				
			},
			 eventRender: function (date, element) {
                 element.attr('href', 'javascript:void(0);');
                 
             },
        */
			
			/*select:function(){
				console.log('select');
				$(this).hmtl();
			},*/
			eventClick: function(event, jsEvent, view) {
				//console.log('event click');
				console.log(event.id);
				$.ajax({
					url:"/schedule/done.do",
					type:'get',
					data:{'id':event.id},
					dataType:'json',
					success:function(){
						console.log("update is ok");
						$removeid = event.id;
						$('#calendar').fullCalendar('removeEvents', event.id);
					},
					error:function(){
						alert("请检查您的网络");
					}
				});
			}
				
		});
		
		
	});

</script>
<style>

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		max-width: 900px;
		margin: 0 auto;
	}

</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/menu.jsp">
	<jsp:param value="schedule" name="active"/>
</jsp:include>
	<div style="width:60%; margin-left:30%;">  
	<a class="btn btn-success" href="/schedule/show.do">列表模式查看</a>
	<a class="btn btn-primary" href="/schedule/add.do">添加日程</a>
	<a class="btn btn-info" href="/schedule/sort/add.do">添加分组</a>
	</div>
	<div id='calendar'></div>
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog-form" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
      <!-- method="post" action="/schedule/add.do"  --> 
       <form id="subform" method="post" class="form-horizontal">
		<input type="hidden" name="cust" value="${param.custid}"/>
		<input type="hidden" name="chance" value="${param.chanceid}"/>
		
		<input name="com" type="hidden" value="${requestScope.com.id}" />
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
			 <div id="form_datetime" class="input-append date form_datetime">
    		   <input id="scheduletime" name="scheduletime" size="16" type="text" value="" readonly>
    		   <span class="add-on"><i class="icon-th"></i></span>
			 </div>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">分类</label>
			<div class="controls">
				<select name="schsort" >
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
				<button data-dismiss="modal" class="btn btn-primary" id="subtn" type="button">提交</button>
				 <button class="btn" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</form>
      </div>    
<script>
$(document).ready(function(){
			var $subtn = $("#subtn");
			var $subform = $("#subform");
			var $schfromtime = $("#schfromtime");
			var $scheduletime = $("#scheduletime");
			var $title = $("input[name=schtitle]");
			
			
			
			
			$.fn.datetimepicker.dates['zh-CN'] = {
					days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
					daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
					daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
					months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
					monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
					today: "今天",
					suffix: [],
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
			
			
			$subtn.on("click", function(){
				$scheduletime.next().next().remove();
				$title.next().remove();
				if($scheduletime.val() != '' && $title.val() != ''){
					//$subform.submit();
					$.ajax({
						url:'/api/schedule/add.do',
						type:'post',
						data:$subform.serialize(),
						success:function(data){
							if(data.result == 'success'){
								console.log("success");
								//$('#myModal').hide();
								//$(".modal-backdrop").hide();
									var rs = data.calendar;
									$('#calendar').fullCalendar('renderEvent', rs, true);
									
							}else{
								console.log('save error');
							}
						},
						error:function(){
							console.log(404);
						}
					});
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
</body>
</html>
