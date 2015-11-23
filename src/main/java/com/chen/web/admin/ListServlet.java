package com.chen.web.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.entity.Account;
import com.chen.entity.Department;
import com.chen.service.UserService;
import com.chen.util.Pager;
import com.chen.web.Controller;

@WebServlet("/admin/user/list.do")
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
		UserService us = new UserService();
		List<Department> deptList = us.showDept();
		Pager<Account> pager = us.findByPage(page, builderWhere(request));
		
		request.setAttribute("page", pager);
		request.setAttribute("deptList", deptList);
		forward("user/show.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}