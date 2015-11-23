package com.chen.web.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.entity.Account;
import com.chen.entity.Customer;
import com.chen.service.CustomerService;
import com.chen.util.Pager;
import com.chen.web.Controller;

@WebServlet("/cust/list.do")
public class ListServlet extends Controller{

	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Account account = (Account) request.getSession().getAttribute("account");
		String pageNo = request.getParameter("p");
		int page = 1;
		if(pageNo != null && StringUtils.isNumeric(pageNo)){
			page = Integer.valueOf(pageNo);
		}
		CustomerService cs = new CustomerService();
		Pager<Customer> pager = cs.findByPage(account, page, builderWhere(request));
		request.setAttribute("page", pager);
		forward("cust/list.jsp", request, response);
	
	}

}
