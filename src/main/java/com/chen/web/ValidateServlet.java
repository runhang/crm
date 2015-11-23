package com.chen.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chen.service.UserService;

@WebServlet("/user/validate.do")
public class ValidateServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService us = new UserService();
		String username = request.getParameter("username");
		boolean isok = us.findByName(username);
		PrintWriter out = response.getWriter();
		out.print(isok);
		out.flush();
		out.close();
	}
	
}
