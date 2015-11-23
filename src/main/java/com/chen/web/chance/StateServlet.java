package com.chen.web.chance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.chen.entity.Account;
import com.chen.entity.ChanceEvent;
import com.chen.service.ChanceService;
import com.chen.service.EventService;
import com.chen.web.Controller;

@WebServlet("/chance/changestate.do")
public class StateServlet extends Controller{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		String chanceid = request.getParameter("id");
		ChanceEvent ce = new ChanceEvent();
		ChanceService chs = new ChanceService();
		EventService es = new EventService();
		Map<String, Object>map = new HashMap<String, Object>();
		if(StringUtils.isNumeric(chanceid)){
			map.put("result", "error");
			map.put("message", "参数错误");
		}
		Long id = Long.valueOf(chanceid);
		Account account = (Account)request.getSession().getAttribute("account");
		/////
		ce.setEvchanceid(id);
		ce.setEvaccountid(account.getId());
		ce.setEvcreatetime(getNowtime());
		/////
		if("success".equals(type)){
			if(chs.chanceSuccess(id) >0){
				map.put("result", "success");
				map.put("message", "事件已成功");
				ce.setEvcontent("事件处理为成功");
				ce.setEvstate(1);
				es.save(ce);
			}else{
				map.put("result", "error");
				map.put("message", "处理success错误");
			}
		}else if("fail".equals(type)){
			if(chs.chanceFail(id) > 0){
				map.put("result", "success");
				map.put("message", "事件已改为失败");
				ce.setEvcontent("事件处理为失败");
				ce.setEvstate(2);
				es.save(ce);
			}else{
				map.put("result", "error");
				map.put("message", "处理fail错误");
			}
		}else if("doing".equals(type)){
			if(chs.chanceDoing(id) > 0){
				map.put("result", "success");
				map.put("message", "事件正在进行");
				ce.setEvcontent("处理为成跟进");
				ce.setEvstate(0);
				es.save(ce);
			}else{
				map.put("result", "error");
				map.put("message", "时间doing错误");
			}
		}else if("over".equals(type)){
			if(chs.chanceOver(id) > 0){
				map.put("result", "success");
				map.put("message", "事件已改为终结");
				ce.setEvcontent("时间处理为终结");
				ce.setEvstate(3);
				es.save(ce);
			}else{
				map.put("result", "error");
				map.put("message", "时间over错误");
			}
		}else{
			map.put("result", "error");
			map.put("message", "请求方式错误");
		}
		renderJson(response, map);
	}
}
