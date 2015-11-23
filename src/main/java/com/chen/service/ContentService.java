package com.chen.service;


import java.util.List;

import com.chen.dao.ContentDAO;
import com.chen.entity.Content;
import com.chen.util.Pager;

public class ContentService {

	private ContentDAO ctd;
	
	public ContentService(){
		ctd = new ContentDAO();
	}
	
	public int addContent(Content content){
		if(content != null){
			return ctd.addContent(content);
		}else{
			return -1;
		}
	}
	public List<Content> findByCustid(Long custid){
		return ctd.findByCustid(custid);
	}
	
	public List<Content> findAccountid(Long accountid){
		return ctd.findAccountid(accountid);
	}
	
	public List<Content> findAll(){
		return ctd.findAll();
	}
	
	public Pager<Content> find(int pageNo, Long custid){
        Pager<Content> pager = new Pager<Content>(pageNo, ctd.count(custid).intValue(), 10);
        
        
        List<Content> list = ctd.find(custid, pager.getStart(), pager.getPageSize());
        pager.setItems(list);
        return pager;
	}
	
	
}
