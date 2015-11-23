package com.chen.web.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;

import com.chen.entity.Account;
import com.chen.service.UserService;
import com.chen.web.Controller;

@WebServlet("/user/login.do")
public class LoginServlet extends Controller{

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String, Object> map = new HashMap<String, Object>();
		if(username == null && password == null){
			map.put("result", "error");
			map.put("message", "账号和密码不能为空");
			return;
		}
		UserService us = new UserService();
		String time = getNowtime();
		String ip = getIp(request);
		if(us.findByName(username)){//如果账号不存在
			map.put("result", "error");
			map.put("message", "账号不存在");
			map.put("value", "-1");
			renderJson(response, map);
			return;
		}
		Account account = us.login(username, password, time, ip);
		if(account == null){
			//response.sendRedirect("/user/login.do?status=error");
			//return;
			map.put("result", "error");
			map.put("message", "账号或密码错误");
			map.put("value", "-1");
			renderJson(response, map);
			return;
		}
		if(!account.getUseable()){
			//response.sendRedirect("/user/login.do?status=nouse");
			//return;
			map.put("result", "error");
			map.put("message", "该账号不可用");
			map.put("value", "-1");
			renderJson(response, map);
			return;
		}
		
		map.put("result", "success");
		HttpSession session = request.getSession();
		session.setAttribute("account", account);
		if(account.getGrade() == 2){
			map.put("message", "success");
			map.put("value", 2);
			LoggerFactory.getLogger(LoginServlet.class).debug("111");
			//response.sendRedirect("/index.do");
		}else if(account.getGrade() == 0){
			map.put("message", "success");
			map.put("value", 0);
			//response.sendRedirect("/admin/user/index.do");
		}
		renderJson(response, map);
	}
}
