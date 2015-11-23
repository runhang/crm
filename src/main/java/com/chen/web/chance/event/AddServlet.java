package com.chen.web.chance.event;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.entity.Account;
import com.chen.entity.Chance;
import com.chen.entity.ChanceEvent;
import com.chen.service.ChanceService;
import com.chen.service.EventService;
import com.chen.web.Controller;

@WebServlet("/chance/event/add.do")
public class AddServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*String pNo = request.getParameter("p");
		int pageNo = 1;
		if(StringUtils.isNumeric(pNo)){
			pageNo = Integer.parseInt(pNo);
		}*/
		String chanceid =request.getParameter("q_eq_evchanceid");
		System.out.println("chanid :   " + chanceid);
		ChanceService chs = new ChanceService();
		EventService es = new EventService();
		if(StringUtils.isNumeric(chanceid)){
			Long id = Long.valueOf(chanceid);
			Chance chance = chs.findById(id);
			if(chance != null){
				request.setAttribute("chance", chance);
			}
		}
		//Pager<ChanceEvent> page = es.findByPage(pageNo, builderWhere(request));
		List<ChanceEvent> page = es.findAll();
		request.setAttribute("page", page);
		forward("chance/event/add.jsp", request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String,Object>map = new HashMap<String, Object>();
		
		String chanceid = request.getParameter("chanceid");
		String evcontent = request.getParameter("evcontent");
		if(chanceid == null || StringUtils.isNumeric(chanceid)){
			map.put("result", "error");
			map.put("message", "id为非法数字");
		}
		if(evcontent == null){
			map.put("result", "error");
			map.put("message", "content内容为空");
		}
		Account account = (Account) request.getSession().getAttribute("account");
		EventService es = new EventService();
		ChanceEvent ce = new ChanceEvent();
		ce.setEvchanceid(Long.valueOf(chanceid));
		ce.setEvcontent(evcontent);
		ce.setEvcreatetime(getNowtime());
		ce.setEvaccountid(account.getId());
		ce.setEvstate(0);
		System.out.println(evcontent);
		if(es.save(ce) > 0){
			map.put("result", "success");
			map.put("message", "添加成功！");
		}else{
			map.put("result", "error");
			map.put("message", "服务器忙，请稍后重试");
		}
		renderJson(response, map);
	}
	
	
}
