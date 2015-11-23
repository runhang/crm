package com.chen.service;

import java.util.List;

import com.chen.dao.ChanceDAO;
import com.chen.entity.Chance;
import com.chen.util.Pager;

public class ChanceService {

	private ChanceDAO chd;
	private TrendsService ts;
	
	public ChanceService(){
		chd = new ChanceDAO();
		ts = new TrendsService();
	}
	
	public long save(Chance chance) {
		ts.saveTrends(chance.getChaccountid(), chance.getChtitle(), chance.getChcontent(), "添加机会", chance.getChcreatetime());
		return chd.addChance(chance);
	}

	@SuppressWarnings("unchecked")
	public Pager<Chance> findByPage(int pageNo, Object[] array) {
		String where = (String) array[0];
        List<Object> params = (List<Object>) array[1];
        Pager<Chance> pager = new Pager<Chance>(pageNo, chd.count(where,params).intValue(),10);
        
        params.add(pager.getStart());
        params.add(pager.getPageSize());
       
        List<Chance> list = chd.find(where,params);
        pager.setItems(list);
        return pager;
	}

	public Chance findById(Long id) {
		return chd.findById(id);
	}

	public int chanceSuccess(Long id) {
		Chance chance = chd.findById(id);
		if(chance != null){
			ts.saveTrends(chance.getChaccountid(), chance.getChtitle(), chance.getChcontent(), "成功事件", chance.getChcreatetime());
			return chd.chanceSuccess(id);
		}else {
			return -1;
		}
		
	}
	
	public int chanceFail(Long id){
		Chance chance = chd.findById(id);
		if(chance != null){
			ts.saveTrends(chance.getChaccountid(), chance.getChtitle(), chance.getChcontent(), "失败事件", chance.getChcreatetime());
			return chd.chanceFail(id);
		}else {
			return -1;
		}
	}
	
	public int chanceDoing(Long id){
		Chance chance = chd.findById(id);
		if(chance != null){
			ts.saveTrends(chance.getChaccountid(), chance.getChtitle(), chance.getChcontent(), "跟进事件", chance.getChcreatetime());
			return chd.chanceDoing(id);
		}else {
			return -1;
		}
	}
	
	public int chanceOver(Long id){
		Chance chance = chd.findById(id);
		if(chance != null){
			ts.saveTrends(chance.getChaccountid(), chance.getChtitle(), chance.getChcontent(), "过期事件", chance.getChcreatetime());
			return chd.chanceOver(id);
		}else {
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	public Double totalMoney(Object[] array) {
		String where = (String) array[0];
        List<Object> params = (List<Object>) array[1];
        return chd.totalMoney(where, params);
	}

}
