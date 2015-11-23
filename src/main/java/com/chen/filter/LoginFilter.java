package com.chen.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chen.entity.Account;

@WebFilter("/*")
public class LoginFilter extends AbstractFilter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpSession session = request.getSession();
		Account account = (Account)session.getAttribute("account");
		String url = request.getRequestURI();
		if(account != null){
			chain.doFilter(req, resp);
			return;
		}
		if(url.equals("/img.png") || url.equals("/user/login.do") || url.startsWith("/static/"))
		{
			System.out.println(url);	
			chain.doFilter(req, resp);
			return;
		}else{
			response.sendRedirect("/user/login.do");
			return;
		}
		
	}
}
