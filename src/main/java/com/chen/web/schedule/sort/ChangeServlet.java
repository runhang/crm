package com.chen.web.schedule.sort;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.entity.Sort;
import com.chen.service.SortService;
import com.chen.web.Controller;

@WebServlet("/schedule/sort/change.do")
public class ChangeServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
		String sortname = request.getParameter("sortname");
		String sortcolor = request.getParameter("sortcolor");
		Long sortid = null;
		if(!StringUtils.isNumeric(id)){
			map.put("result", "error");
			map.put("message", "无该账号");
			return;
		}
		sortid = Long.valueOf(id);
		Sort sort = new Sort();
		sort.setId(sortid);
		sort.setSortname(sortname);
		sort.setSortcolor(sortcolor);
		SortService ss = new SortService();
		if(sortcolor== null &&
				sort.getSortname() == null){
			map.put("result", "error");
			map.put("message", "参数为空");
		}else if(sort.getSortcolor() == null && 
			sort.getSortname() != null){
			if(ss.updateSortname(sort)>0){
				map.put("result", "success");
				map.put("message", sort.getSortname());
			}else{
				map.put("result", "error");
				map.put("message", "服务器忙");
			}
		}else if(sort.getSortname() == null && 
				sort.getSortcolor() != null){
			if(ss.updateSortColor(sort)>0){
				map.put("result", "success");
				map.put("value", sort.getSortcolor());;
			}else{
				map.put("result", "error");
				map.put("message", "服务器忙");
			}			
		}else {
			if(ss.updateSort(sort)>0){
				map.put("result", "success");
				map.put("message", sort.getSortname());
				map.put("value", sort.getSortcolor());
			}else{
				map.put("result", "error");
				map.put("message", "服务器忙");
			}
		}
		renderJson(response, map);
	}
	
}
