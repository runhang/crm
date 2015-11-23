package com.chen.web.schedule.sort;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.service.SortService;
import com.chen.web.Controller;


@WebServlet("/schedule/sort/del.do")
public class DelServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestHeader = request.getHeader("X-Requested-With");
		Map<String, Object> map = new HashMap<String, Object>();
		if(requestHeader == null || !"XMLHttpRequest".equalsIgnoreCase(requestHeader)){
			map.put("result", "error");
			map.put("message", "非法请求");
			renderJson(response, map);
			return;
		}
		String id = request.getParameter("id");
		Long sortid = null;
		if(!StringUtils.isNumeric(id)){
			map.put("result", "error");
			map.put("message", "失效的请求");
			renderJson(response, map);
			return;
		}
		sortid = Long.valueOf(id);
		if(sortid < 0){
			map.put("result", "error");
			map.put("message", "恶意的请求");
			renderJson(response, map);
			return;
		}
		SortService ss = new SortService();
		if(ss.delSort(sortid) > 0){
			map.put("result", "success");
			map.put("message", "ok");
			map.put("value", id);
		}else{
			map.put("result", "error");
			map.put("message", "服务器忙，请稍后重试");
		}
		renderJson(response, map);
		
	}
	
	
	
}
