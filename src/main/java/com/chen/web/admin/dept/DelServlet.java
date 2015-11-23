package com.chen.web.admin.dept;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.chen.entity.Department;
import com.chen.service.DeptService;
import com.chen.service.UserService;
import com.chen.web.Controller;

@WebServlet("/admin/dept/del.do")
public class DelServlet extends Controller{


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String deptid = request.getParameter("id");
		
		String requestHeader = request.getHeader("X-Requested-With");
		Map<String, Object> map = new HashMap<String, Object>();
		if(requestHeader == null || !"XMLHttpRequest".equalsIgnoreCase(requestHeader)){
			map.put("result", "error");
			map.put("message", "非法请求，拒绝处理");
			renderJson(response, map);
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		String pageNo = request.getParameter("p");
		int page = 1;
		if(StringUtils.isNumeric(pageNo)){
			page = Integer.parseInt(pageNo);
		}
		
		if(!StringUtils.isNumeric(deptid)){
			map.put("result", "error");
			map.put("message", "参数错误");
			renderJson(response, map);
			return;
		}
		
		Long id = Long.valueOf(deptid);
		
		if(id < 0){
			map.put("result", "error");
			map.put("message", "参数id错误,拒绝请求");
			renderJson(response, map);
			return;
		}
		
		DeptService ds = new DeptService();
		UserService us = new UserService();
		if(!us.findByDeptid(id).isEmpty()){
			map.put("result", "error");
			map.put("message", "该部门有数据,不能删除");
			renderJson(response, map);
			return;
		}else{
			if(ds.delete(id) > 0){
				Department dept = ds.findAfterOne(page);
				map.put("result", "success");
				if(dept != null){
					map.put("message", dept.getDeptname());
					map.put("value", dept.getId());
				}else{
					map.put("message", "");
					map.put("value", "");
				}
			}else{
				map.put("result", "error");
				map.put("message", "删除失败");
			}
		}
		renderJson(response, map);
	}
	
}
