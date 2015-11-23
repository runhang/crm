package com.chen.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/*")
public class RedirectAttributeFilter extends AbstractFilter{
	
	private static Logger logger = LoggerFactory.getLogger(RedirectAttributeFilter.class);
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpSession session = request.getSession();
		
		
		//请求过滤
        logger.debug("请求过滤");
        Enumeration<String> sessionKeys = session.getAttributeNames();
        while(sessionKeys.hasMoreElements()) {
            String key = sessionKeys.nextElement();
           if(key.startsWith("redirect-")) {
                Object value = session.getAttribute(key);
                session.removeAttribute(key);
                key = key.substring(key.indexOf("-")+1);
                request.setAttribute(key,value);
                logger.debug("{}->{}",key,value);
           }
        }
		
		chain.doFilter(req, resp);
		
		logger.debug("响应过滤");
		if(response.getStatus() == 302){
			logger.debug("状态码是{}",302);
			Enumeration<String> names = request.getAttributeNames();
			while(names.hasMoreElements()){
				String name = names.nextElement();
				//if(name.startsWith("redirect-")) {
	                Object value = request.getAttribute(name);
	                logger.debug("{}->{}",name,value);
	                session.setAttribute("redirect-"+name,value);//redirect-message :xxxx
	           // }
			}
		}
		
	}

}
