package com.chen.web.admin.user;

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
import com.chen.web.Controller;

@WebServlet("/admin/adduser.do")
public class AddServlet extends Controller{


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService us = new UserService();
		List<Department> deptList = us.showDept();
		request.setAttribute("deptList", deptList);
		forward("admin/adduser.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String dept = request.getParameter("dept");
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		Long deptid = null;
		if(dept == null || username == null || pass == null){
			response.sendRedirect("/admin/adduser.do");
			return;
		} 
		if(StringUtils.isNumeric(dept)){
			deptid = Long.valueOf(dept);
		}else{
			response.sendRedirect("/admin/adduser.do");
			return;
		}
		String ip = getIp(request);
		String createtime = getNowDate();
	    Account account = new Account();
	    account.setUsername(username);
	    account.setUserpass(pass);
	    account.setDeptid(deptid);
	    account.setLastloginip(ip);
	    account.setCreatetime(createtime);
		UserService us = new UserService();
		us.register(account);
		response.sendRedirect("/admin/user/list.do");
	
		
	}
	
	
}
