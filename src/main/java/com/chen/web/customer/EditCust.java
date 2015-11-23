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
import com.chen.web.Controller;

@WebServlet("/cust/editcust.do")
public class EditCust extends Controller{

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sid = request.getParameter("id");
		if(sid != null && StringUtils.isNumeric(sid)){
			Long id = Long.valueOf(sid);
			if(id >= 0){
				CustomerService cs = new CustomerService();
				Customer cust = cs.findById(id);
				if(cust != null){
					request.setAttribute("cust", cust);
				}
			}
		}
		forward("/cust/editcust.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String custid = request.getParameter("cust");
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
		if(custid == null ||custname == null || jobs == null 
			|| company == null || tel == null || !StringUtils.isNumeric(custid)){
			response.sendRedirect("/cust/list.do");
			return;
		}
		Long id = Long.valueOf(custid);
		Account account = (Account) request.getSession().getAttribute("account");
		Customer cust = new Customer();
		cust.setId(id);
		cust.setFlags(true);//true ÎªÓÃ»§
		cust.setAccountid(account.getId());
		cust.setCustname(custname);
		cust.setJobs(jobs);
		cust.setTel(tel);
		cust.setEmail(email);
		cust.setIm(im);
		cust.setAddress(address);
		cust.setViews(view.equals("yes")? true: false);
		cust.setWebsite(website);
		cust.setContent(content);
		CustomerService cs = new CustomerService();
		if(cs.editCustomer(cust, company) > -1){
			response.sendRedirect("/cust/event.do?id="+id);
			return;
		}else{
			response.sendError(500);
			return;
		}
		
	}
}
