package com.chen.web.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.service.CustomerService;
import com.chen.web.Controller;

@WebServlet("/cust/delcust.do")
public class DelCustServlet extends Controller{

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sid = request.getParameter("id");
		if(sid == null || !StringUtils.isNumeric(sid)){
			response.sendRedirect("/cust.list.do");
			return;
		}
		Long id  =  Long.valueOf(sid);
		/*
		Account account = (Account) request.getSession().getAttribute("account");
		
		if(id != account.get){
			response.sendError(401, "无权限操作");
			return;
		}*/
		CustomerService cs = new CustomerService();
		if(cs.delCust(id) > 0){
			response.sendRedirect("/cust/list.do");
			return;
		}
		
	}
	
	
}
