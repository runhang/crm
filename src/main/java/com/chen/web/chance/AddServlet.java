package com.chen.web.chance;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chen.entity.Chance;
import com.chen.service.ChanceService;
import com.chen.web.Controller;



@WebServlet("/chance/add.do")
public class AddServlet extends Controller{

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		forward("chance/add.jsp", request, response);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String custid = request.getParameter("custid");
		String chtitle = request.getParameter("chtitle");
		String chcontent = request.getParameter("chcontent");
		String chviews = request.getParameter("chviews");
		String chmoney = request.getParameter("chmoney");
		if(custid == null && chtitle == null 
				&& chcontent == null &&chviews == null){
			response.sendError(401);
		}
		//Integer state = Integer.valueOf(chstate);
		Double money = Double.valueOf(chmoney);
		Chance chance = new Chance();
		ChanceService chs = new ChanceService();
		chance.setChtitle(chtitle);
		chance.setChcontent(chcontent);
		chance.setChmoney(money);
		chance.setChstate(0);
		chance.setChviews("yes".equals(chviews)?true:false);
		chance.setChcreatetime(getNowtime());
		if(chs.save(chance) > 0){
			response.sendRedirect("/chance/list.do");
		}else{
			//shibai
			response.sendError(500, "出现异常，请稍后再试");
		}
		
		
		
	}
	
}
