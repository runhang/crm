package com.chen.web.schedule;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.chen.entity.Account;
import com.chen.entity.Schedule;
import com.chen.service.ScheduleService;
import com.chen.web.Controller;

@WebServlet("/schedule/show.do")
public class ShowServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Account account = (Account) request.getSession().getAttribute("account");
		
		String states = request.getParameter("q_eq_state");
		ScheduleService chs = new ScheduleService();
		if( states != null &&( "1".equals(states) 
				|| "0".equals(states) )){
			
			Boolean schstates = "1".equals(states)?true :false ;
			List<Schedule> schList = chs.findByStates(schstates, account.getId());
			request.setAttribute("schList", schList);
			forward("schedule/show.jsp", request, response);
			return;
		}
		
		/*String overNo = request.getParameter("over_p");
		String unoverNo = request.getParameter("unover_p");
		*//*
		int overpage = 1;
		if(overNo != null && StringUtils.isNumeric(overNo)){
			overpage = Integer.valueOf(overNo);
		}
		
		int unoverpage = 1;
		if(unoverNo != null && StringUtils.isNumeric(unoverNo)){
			unoverpage = Integer.valueOf(unoverNo);
		}*/
		
		List<Schedule> overList = chs.findOver(account.getId(), getNowtime());
		Map<String, List<Schedule>> unoverMap = chs.findUnOverNodone(account.getId(), getNowtime());
		request.setAttribute("overList", overList);
			 
		request.setAttribute("unoverMap", unoverMap);
		
		forward("schedule/show.jsp", request, response);
	}
	
	
}
