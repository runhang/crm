package com.chen.service;

import java.util.List;

import com.chen.dao.TrendsDAO;
import com.chen.entity.Trends;
import com.chen.util.Pager;

public class TrendsService {

	private TrendsDAO td;
	
	public TrendsService(){
		td = new TrendsDAO();
	}
	
	public long saveTrends(Trends trends){
		return td.save(trends); 
	}
	
	public List<Trends> findAll(Long accountid){
		return td.findAll(accountid);
	}
	
	public long saveTrends(Long accountid, String title, String content, String type, String createtime){
		Trends trends = new Trends();
		trends.setTreaccountid(accountid);
		trends.setTretitle(title);
		trends.setTrecontent(content);
		trends.setTretype(type);
		trends.setTrecreatetime(createtime);
		return td.save(trends);
	}

	public Pager<Trends> findByPage(int page, Long accountid) {
		Pager<Trends> pager = new Pager<Trends>(page, td.count(accountid).intValue(), 15);
		List<Trends> trendsList = td.findByPage(accountid, pager.getStart(), pager.getPageSize());
		pager.setItems(trendsList);
		return pager;
	}
	
}
