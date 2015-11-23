package com.chen.web.admin.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.service.UserService;
import com.chen.web.Controller;

@WebServlet("/admin/user/resetpass.do")
public class ResetPassServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String requestHeader = request.getHeader("X-Requested-With");
		Map<String, Object> map = new HashMap<String, Object>();
		if(requestHeader == null || !"XMLHttpRequest".equalsIgnoreCase(requestHeader)){
			response.sendError(401, "权限失效");
		}
		
		String sid = request.getParameter("id");
		//String username = request.getParameter("username");
		if(sid == null || !StringUtils.isNumeric(sid)){
			//forward("msg/error.jsp?username" + username, request, response);
			response.sendError(401, "权限失效");
			return;
		}
		Long id = Long.valueOf(sid);
		if(id < 0){
			map.put("result", "error");
			map.put("message","请不要进行而已攻击！");
			renderJson(response, map);
			return;
		}
		UserService us = new UserService();
		if(us.resetPass(id) > 0){
			//forward("msg/ok.jsp?username=" + username, request, response);
			map.put("result", "success");
			map.put("message", "重置密码成功");
			renderJson(response, map);
			return;
		}else {
			map.put("result", "error");
			map.put("message", "用户不存在");
			renderJson(response, map);
			return;
		}
		
		
	}
	
}
