package com.chen.web.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chen.entity.Account;
import com.chen.entity.Content;
import com.chen.entity.Customer;
import com.chen.service.ContentService;
import com.chen.service.CustomerService;
import com.chen.util.Pager;
import com.chen.web.Controller;

@WebServlet("/cust/event.do")
public class AddEventServlet extends Controller{
	
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
		//CustomerService ccs = new CustomerService();
		//Pager<Customer> pager = ccs.findByPage(account, page, builderWhere(request));
		//request.setAttribute("page", pager);
		//forward("cust/list.jsp", request, response);
		////////////////////////////////////
		String sid = request.getParameter("id");
		Long id = null ;
		if(StringUtils.isNumeric(sid)){
			id = Long.valueOf(sid);
			///////////////////////////////////////////////
			//Map<String, List<Content>>map = new HashMap<String, List<Content>>();
			////////////////////////////////////////
			CustomerService cs = new CustomerService();
			ContentService cts = new ContentService();
			Customer cust = new Customer();
			cust = cs.findById(id);
			//List<Content> cList  = cts.findByCustid(id);
 			Customer customer = cs.findById(id);
 			Pager<Content> pager = cts.find(page, id);
			if(customer != null && pager !=null){
				request.setAttribute("cust", cust);
				//map.put("list", pager.getItems());
				request.setAttribute("page", pager);
			}
		}
		forward("cust/event.jsp", request, response);	
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger log = LoggerFactory.getLogger(AddEventServlet.class);
		String sid = request.getParameter("id");
		String scontent = request.getParameter("content");
		if(sid == null || !StringUtils.isNumeric(sid)){
			response.sendError(401,"非法操作，权限终结");
			return;
		}
		Long id = Long.valueOf(sid);
		ContentService cts = new ContentService();
		Content content = new Content();
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		if(account != null){
			content.setAccountid(account.getId());
		}
		content.setContent(scontent);
		content.setCustid(id);
		content.setCreatetime(getNowtime());
		if(cts.addContent(content) > 0){
			log.debug("添加{}备注内容为：{} 存储成功",id, content);
			response.sendRedirect("/cust/event.do?id="+ id);
			return;
		}else{
			
			response.sendError(500, "请稍后再试");
		}
		
		
	}
	
}
