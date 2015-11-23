package com.chen.web.admin.dept;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.entity.Department;
import com.chen.service.DeptService;
import com.chen.util.Pager;
import com.chen.web.Controller;

@WebServlet("/admin/dept/list.do")
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
		DeptService ds = new DeptService();
		Pager<Department> pager = ds.findByPage(page, builderWhere(request));
		request.setAttribute("page", pager);
		forward("dept/list.jsp", request, response);
	}
	
}
