package com.chen.web.api.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chen.entity.Account;
import com.chen.entity.Customer;
import com.chen.service.CustomerService;
import com.chen.web.Controller;

@WebServlet("/api/cust.json")
public class CustJsonServlet extends Controller{

	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String keyword = request.getParameter("query");
		if(keyword == null){
			response.sendError(401);
		}
		String custname = new String(keyword.getBytes("ISO8859-1"), "UTF-8");
		
		Account account = (Account) request.getSession().getAttribute("account");
		CustomerService cs = new CustomerService();
		List<Customer> cList = cs.findByName(custname, account);
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		for(Customer cust: cList){
			Map<String,Object> map = new HashMap<String, Object>();
			System.out.println("custname: "+cust.getCustname());
            map.put("value",cust.getCustname());
            map.put("data",cust.getId());
            datas.add(map);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("query", "Unit");
		result.put("suggestions", datas);
		renderJson(response, result);
	}
}
