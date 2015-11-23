package com.chen.web.api.schedule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.entity.Account;
import com.chen.entity.Schedule;
import com.chen.entity.Sort;
import com.chen.service.ScheduleService;
import com.chen.service.SortService;
import com.chen.web.Controller;

@WebServlet("/api/schedule/add.do")
public class AddServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestHeader = request.getHeader("X-Requested-With");
		Map<String, Object> map = new HashMap<String, Object>();
		if(requestHeader == null || !"XMLHttpRequest".equalsIgnoreCase(requestHeader)){
			map.put("result", "error");
			map.put("message", "非法请求");
			renderJson(response, map);
			return;
		}
		
		String schtitle = request.getParameter("schtitle");
		String schfromtime = request.getParameter("schfromtime");
		String scheduletime = request.getParameter("scheduletime");
		String schview = request.getParameter("schviews");
		String sort = request.getParameter("sort");
		String cust = request.getParameter("custid");
		String chance =request.getParameter("chanceid");
		Boolean schviews = false;
		Long sortid = 1L;
		Long custid = null;
		Long chanceid = null;
		if(schtitle == null || scheduletime== null){
			response.sendError(401, "非法请求");
			return;
		}
		if(schviews != null){
			if(schview.equals("0")){
				schviews = false;
			}else if(schviews.equals("1")){
				schviews = true;
			}
		}
		if(StringUtils.isNumeric(sort)){
			sortid = Long.valueOf(sort);
		}
		if(StringUtils.isNumeric(cust)){
			custid = Long.valueOf(cust);
		}
		if(StringUtils.isNumeric(chance)){
			chanceid=Long.valueOf(chance);
		}
		Account account = (Account) request.getSession().getAttribute("account");
		Map<String, Object>dataMap = new HashMap<String, Object>();
		ScheduleService schs = new ScheduleService();
		Schedule schedule = schs.getSchedule(schtitle, schfromtime, scheduletime, schviews,false,
				sortid,custid, chanceid, account.getId());
		schedule.setSchcreatetime(getNowtime());
		Long scheduleid = schs.save(schedule);
		if(scheduleid > 0){
			//response.sendRedirect("/schedule/show.do");
			//return;
			Sort ssort = new SortService().findBySortId(sortid);
			map.put("result", "success");
			dataMap.put("id", scheduleid);
			dataMap.put("start", schedule.getSchfromtime());
			dataMap.put("end", schedule.getScheduletime());
			dataMap.put("title", schedule.getSchtitle());
			dataMap.put("color", ssort.getSortcolor());
			map.put("calendar", dataMap);
		}else {
			map.put("result", "error");
			map.put("message", "save error");
		}
		renderJson(response, map);
	}
}
