package com.chen.web.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chen.entity.Account;
import com.chen.service.UserService;
import com.chen.web.Controller;

@WebServlet("/admin/user/changepass.do")
public class ChangePassServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		forward("user/changepass.jsp", request, response);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oldpass = request.getParameter("oldpass");
		String newpass = request.getParameter("newpass");
		if(oldpass == null || newpass == null){
			response.sendRedirect("/admin/user/changepass.do?status=null");
			return;
		}
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		
		if(!oldpass.equals(account.getUserpass())){
			response.sendRedirect("/admin/user/changepass.do?status=noequal");
			return;
		}
		UserService us = new UserService();
		Map<String, Object> map = new HashMap<String, Object>();
		if(us.changePass(account.getId(), newpass) >= 1){
			session.invalidate();
			map.put("state", "ok");
			renderJson(response, map);
			//response.sendRedirect("/cust/list.do");
			return;
		}else{
			//renderJson(response, map);
			response.sendError(500, "«Î…‘∫Û‘Ÿ ‘");
			return;
		}
		
	}
}
