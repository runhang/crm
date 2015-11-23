package com.chen.web.api.content;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.entity.Content;
import com.chen.service.ContentService;
import com.chen.util.Pager;
import com.chen.web.Controller;
import com.google.gson.Gson;

@WebServlet("/api/content/show.do")
public class ShowServlet extends Controller{

		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			//Account account = (Account) request.getSession().getAttribute("account");
			String pageNo = request.getParameter("p");
			int page = 1;
			if(pageNo != null && StringUtils.isNumeric(pageNo)){
				page = Integer.valueOf(pageNo);
			}
			Map<String, Pager<Content>>map = new HashMap<String, Pager<Content>>();
			
			String sid = request.getParameter("id");
			Long id = null ;
			if(StringUtils.isNumeric(sid)){
				id = Long.valueOf(sid);
				ContentService cts = new ContentService();
				
	 			Pager<Content> pager = cts.find(page, id);
				if(pager !=null){
					map.put("page", pager);
					//request.setAttribute("page", pager);
				}
			}
			response.setContentType("application/json; charset=UTF-8"); 
			Gson gson = new Gson();
			String json =  gson.toJson(map);
			print(json, response);
			//forward("cust/event.jsp", request, response);	
		}



	
}
