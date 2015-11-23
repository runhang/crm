package com.chen.web.chance.event;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.entity.ChanceEvent;
import com.chen.service.EventService;
import com.chen.util.Pager;
import com.chen.web.Controller;

@WebServlet("/chance/event/list.do")
public class ListServlet extends Controller{

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
		EventService chs = new EventService();
		//List<Department> deptList = chs.showDept();
		Pager<ChanceEvent> pager = chs.findByPage(page, builderWhere(request));
		
		request.setAttribute("page", pager);
		//request.setAttribute("deptList", deptList);
		forward("chance/event/list.jsp", request, response);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	
}
