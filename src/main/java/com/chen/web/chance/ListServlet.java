package com.chen.web.chance;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.entity.Chance;
import com.chen.service.ChanceService;
import com.chen.util.Pager;
import com.chen.web.Controller;

@WebServlet("/chance/list.do")
public class ListServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pageNo = request.getParameter("p");
		int page = 1;
		if(StringUtils.isNumeric(pageNo)){
			page = Integer.parseInt(pageNo);
		}
		
		 
		
		ChanceService chs = new ChanceService();
		//List<Department> deptList = chs.showDept();
		Double totalMoney = chs.totalMoney(builderWhere(request));
		Pager<Chance> pager = chs.findByPage(page, builderWhere(request));
		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("page", pager);
		//request.setAttribute("deptList", deptList);
		forward("chance/list.jsp", request, response);
	}
	
}
