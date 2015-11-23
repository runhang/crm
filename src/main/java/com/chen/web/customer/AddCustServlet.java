package com.chen.web.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.entity.Customer;
import com.chen.service.CustomerService;
import com.chen.web.Controller;

@WebServlet("/cust/addcust.do")
public class AddCustServlet extends Controller{

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		forward("cust/addcust.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("account");
		String custname = request.getParameter("custname");
		String jobs = request.getParameter("jobs");
		String company = request.getParameter("company");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String im = request.getParameter("im");
		String website = request.getParameter("website");
		String view = request.getParameter("view");
		String content = request.getParameter("content");
		if(id == null || custname == null || jobs == null 
				|| company == null || tel == null || !StringUtils.isNumeric(id)){
			response.sendRedirect("/cust/addcust.do");
			return;
		}
		Long accountid = Long.valueOf(id);
		Customer cust = new Customer();
		cust.setFlags(true);//true ÎªÓÃ»§
		cust.setAccountid(accountid);
		cust.setCustname(custname);
		cust.setJobs(jobs);
		cust.setTel(tel);
		cust.setEmail(email);
		cust.setIm(im);
		cust.setAddress(address);
		cust.setViews(view.equals("yes")? true: false);
		cust.setWebsite(website);
		cust.setContent(content);
		cust.setCreatetime(getNowDate());
		CustomerService cs = new CustomerService();
		if(cs.addCustomer(cust, company) > -1){
			response.sendRedirect("/cust/list.do");
			return;
		}else{
			response.sendError(500);
			return;
		}
		
	}
	
}
