package com.chen.web.schedule.sort;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.chen.entity.Sort;
import com.chen.service.SortService;
import com.chen.web.Controller;

@WebServlet("/schedule/sort/add.do")
public class AddServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		SortService ss = new SortService();
		List<Sort> sortList = ss.findAll();
		request.setAttribute("sortList", sortList);
		forward("/schedule/sort/add.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String sortname = request.getParameter("sortname");
		String sortcolor = request.getParameter("sortcolor");
		Map<String, Object> map = new HashMap<String, Object>();
		if(sortname == null){
			map.put("result", "error");
			map.put("message", "sortname can not null");
			renderJson(response, map);
			return;
		}
		SortService ss = new SortService();
		if(ss.findBySortName(sortname) != null){
			map.put("result", "error");
			map.put("message", "sortname is exist");
			renderJson(response, map);
			return;
		}
		if(sortcolor == null){
			sortcolor = "#000000";//如果为空，设为默认值
		}
		Sort sort = new Sort();
		sort.setSortname(sortname);
		sort.setSortcolor(sortcolor);
		Long id = ss.save(sort);
		if(id > 0){
			map.put("result", "success");
			map.put("message", sortname);
			map.put("color", sortcolor);
			map.put("value", id);
		}else{
			map.put("result", "error");
			map.put("message", "sortname save fail");
		}
		renderJson(response, map);
		
	}
	
}
