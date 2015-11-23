package com.chen.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.entity.Account;
import com.chen.entity.Schedule;
import com.chen.entity.Trends;
import com.chen.service.ScheduleService;
import com.chen.service.TrendsService;
import com.chen.util.Pager;

@WebServlet("/index.do")
public class IndexServlet extends Controller{

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String pageNo = request.getParameter("p");
		int page = 1;
		if(StringUtils.isNumeric(pageNo)){
			page = Integer.parseInt(pageNo);
		}
		TrendsService ts = new TrendsService();
		Account account = (Account) request.getSession().getAttribute("account");	
		Pager<Trends> pager = ts.findByPage(page, account.getId());
		
		
		
		//List<Trends> trendsList = ts.findAll(account.getId());
		
		ScheduleService ss = new ScheduleService();
		List<Schedule> todayList = ss.findUnoverUndoByToday(account.getId(), getNowDate());
		List<Schedule> overList = ss.findOverUndoList(account.getId(), getNowDate());
		
		int overUndone = ss.overUndoCount(account.getId(), getNowtime());
		int unoverUndo = ss.unoverUndoCount(account.getId(), getNowtime());
		int done = ss.doneCount(account.getId());
		request.setAttribute("overUndone", overUndone);
		request.setAttribute("unoverUndo", unoverUndo);
		request.setAttribute("done", done);
		
		request.setAttribute("pager", pager);
		request.setAttribute("todayList", todayList);
		request.setAttribute("overList", overList);
		//request.setAttribute("trendsList", trendsList);
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}
	
	
}
