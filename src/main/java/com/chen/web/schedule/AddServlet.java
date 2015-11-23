package com.chen.web.schedule;

import java.io.IOException;
import java.util.List;

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

@WebServlet("/schedule/add.do")
public class AddServlet extends Controller{

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SortService ss = new SortService();
		List<Sort> sortList = ss.findAll();
		request.setAttribute("sortList", sortList);
		forward("/schedule/add.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String schtitle = request.getParameter("schtitle");
		String schfromtime = request.getParameter("schfromtime");
		String scheduletime = request.getParameter("scheduletime");
		String schview = request.getParameter("schviews");
		String sort = request.getParameter("sort");
		String cust = request.getParameter("custid");
		String com = request.getParameter("com");
		String chance =request.getParameter("chanceid");
		Boolean schviews = false;
		Long sortid = 1L;
		Long custid = null;
		Long chanceid = null;
		if(schtitle == null || scheduletime== null){
			response.sendError(401, "·Ç·¨ÇëÇó");
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
		}else if(StringUtils.isNumeric(com)){
			custid = Long.valueOf(com);
		}
		if(StringUtils.isNumeric(chance)){
			chanceid=Long.valueOf(chance);
		}
		Account account = (Account) request.getSession().getAttribute("account");
		
		ScheduleService schs = new ScheduleService();
		Schedule schedule = schs.getSchedule(schtitle, schfromtime, scheduletime, schviews,false,
				sortid,custid, chanceid, account.getId());
		schedule.setSchcreatetime(getNowtime());
		if(schs.save(schedule) > 0){
			response.sendRedirect("/schedule/show.do");
			return;
		}else {
			response.sendError(500);
		}
		
	}
	
}
