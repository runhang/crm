package com.chen.web.schedule;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chen.entity.Sort;
import com.chen.service.SortService;
import com.chen.web.Controller;

@WebServlet("/schedule/fullcalendar.do")
public class FullCalendarServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SortService ss = new SortService();
		List<Sort> sortList = ss.findAll();
		request.setAttribute("sortList", sortList);
		forward("/schedule/fullcalendar.jsp", request, response);
	}
	
	
}
