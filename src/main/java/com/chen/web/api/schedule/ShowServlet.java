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
import com.chen.entity.Sort;
import com.chen.service.ScheduleService;
import com.chen.service.SortService;
import com.chen.web.Controller;
import com.google.gson.Gson;

@WebServlet("/api/schedule/show.json")
public class ShowServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Account account = (Account) request.getSession().getAttribute("account");
		SortService ss = new SortService();
		
		//String states = request.getParameter("q_eq_state");
		ScheduleService chs = new ScheduleService();
		//if( states != null ){
			
			//List<Schedule> schList = chs.findByStates(false, account.getId());
			//request.setAttribute("schList", schList);
		//}
		//id:data[i].id,
		//title:data[i].schtitle,
		//start: data[i].schcreatetime,
		//color: data[i].sort.sortColor,
		//end:data[i].scheduletime
		
		//List<Schedule> overList = chs.findOver(account.getId(), getNowtime());
		String startTime = request.getParameter("start");
		String endTime = request.getParameter("end");
		List<Schedule> schList = chs.findUndo(account.getId(), startTime, endTime);
		//request.setAttribute("overList", unoverList);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i = 0; i <schList.size(); i++){
			Schedule sch = schList.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", sch.getId());
			map.put("title", sch.getSchtitle());
			map.put("start", sch.getSchfromtime());
			map.put("color", sch.getSort().getSortcolor());
			map.put("end", sch.getScheduletime());
			//start: data[i].schcreatetime,
			//color: data[i].sort.sortColor,
			//end:data[i].scheduletime
			list.add(map);
		}
		response.setContentType("application/json; charset=UTF-8"); 
		Gson gson = new Gson();
		String json =  gson.toJson(list);
		print(json, response);
		//forward("schedule/show.jsp", request, response);
	}
	
	
}
