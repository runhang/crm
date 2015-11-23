package com.chen.web.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.chen.entity.Account;
import com.chen.entity.Customer;
import com.chen.service.CustomerService;
import com.chen.web.Controller;

@WebServlet("/cust/addcom.do")
public class AddComServlet extends Controller{

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		forward("cust/addcom.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		Long id = account.getId();
		String custname = request.getParameter("custname");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String im = request.getParameter("im");
		String website = request.getParameter("website");
		String view = request.getParameter("view");
		String content = request.getParameter("content");
		if(id == null || custname == null || tel == null){
			response.sendRedirect("/cust/addcust.do");
			return;
		}
		//////////////////////////////////////
		Customer cust = new Customer();
		cust.setFlags(true);//true ÎªÓÃ»§
		cust.setAccountid(id);
		cust.setCustname(custname);
		cust.setTel(tel);
		cust.setEmail(email);
		cust.setIm(im);
		cust.setAddress(address);
		cust.setViews(view.equals("yes")? true: false);
		cust.setWebsite(website);
		cust.setContent(content);
		cust.setCreatetime(getNowDate());
		////////////////////////////////////////////////
		CustomerService cs = new CustomerService();
		if(cs.addCompany(cust) > 0){
			response.sendRedirect("/cust/list.do");
			return;
		}else{
			response.sendError(500);
			return;
		}
		
	}
	
}
