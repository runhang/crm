package com.chen.web.api.chance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.entity.Chance;
import com.chen.service.ChanceService;
import com.chen.util.Pager;
import com.chen.web.Controller;
import com.google.gson.Gson;

@WebServlet("/api/chance/show.do")
public class showServlet extends Controller{


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Pager<Chance>>map = new HashMap<String, Pager<Chance>>();
		request.setCharacterEncoding("UTF-8");
		String pageNo = request.getParameter("p");
		int page = 1;
		if(StringUtils.isNumeric(pageNo)){
			page = Integer.parseInt(pageNo);
		}
		ChanceService chs = new ChanceService();
		Pager<Chance> pager = chs.findByPage(page, builderWhere(request));
		map.put("page", pager);
		response.setContentType("application/json; charset=UTF-8"); 
		Gson gson = new Gson();
		String json =  gson.toJson(map);
		print(json, response);
		//request.setAttribute("page", pager);
		//forward("chance/list.jsp", request, response);
		
	}
	
	
	
}
