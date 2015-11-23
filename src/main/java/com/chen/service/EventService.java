package com.chen.service;

import java.util.List;
import com.chen.dao.EventDAO;
import com.chen.entity.ChanceEvent;
import com.chen.util.Pager;

public class EventService {

	private EventDAO ed;
	
	public EventService() {
		ed = new EventDAO();
	}
	
	
	public List<ChanceEvent> findAll(){
		return ed.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public Pager<ChanceEvent> findByPage(int pageNo, Object[] array) {
		String where = (String) array[0];
		List<Object> params = (List<Object>) array[1];
		
		
		Pager<ChanceEvent> pager = new Pager<ChanceEvent>(pageNo, ed.count(where, params).intValue(), 10);
		params.add(pager.getStart());
		params.add(pager.getPageSize());
		List<ChanceEvent> list = ed.find(where,params);
        pager.setItems(list);
		return pager;
	}


	public long save(ChanceEvent ce) {
		return ed.addEvent(ce);
	}

}
