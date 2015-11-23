package com.chen.web.api.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chen.entity.Account;
import com.chen.entity.Schedule;
import com.chen.service.ScheduleService;
import com.chen.service.SortService;
import com.chen.web.Controller;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

@WebServlet("/api/schedule/list.do")
public class ListServlet extends Controller{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Account account = (Account) request.getSession().getAttribute("account");
		SortService ss = new SortService();
		String cust = request.getParameter("custid");
		Long custid = null;
		if(org.apache.commons.lang3.StringUtils.isNumeric(cust)){
			custid = Long.valueOf(cust);
		}else{
			Map<String, Object>map = new HashMap<String, Object>();
			map.put("result", "error");
			renderJson(response, map);
			return;
		}
		ScheduleService chs = new ScheduleService();
		
	
		Map<String,List<Schedule>> schedules = chs.findUndoByCustid(custid ,getNowtime());
		//request.setAttribute("overList", unoverList);
		
		response.setContentType("application/json; charset=UTF-8"); 
		Gson gson = new Gson();
		String json =  gson.toJson(schedules);
		print(json, response);
	}
	
}
