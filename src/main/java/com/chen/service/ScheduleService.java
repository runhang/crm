package com.chen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chen.dao.ScheduleDAO;
import com.chen.entity.Schedule;
import com.chen.util.Pager;

public class ScheduleService {

	private ScheduleDAO shd;
	
	public ScheduleService(){
		shd = new ScheduleDAO();
	}
	
	public long save(Schedule schedule){
		return shd.addSchedule(schedule);
	}
	
	public int delete (Long id){
		return shd.delete(id);
	}
	
	public int updateState(Long id){
		return shd.updateDoneSchedule(id);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Pager<Schedule> findUnOver(int pageNo, Long accountid, String nowtime, Object[] array) {
		
		String temp = (String) array[0];
        List<Object> params = (List<Object>) array[1];
        StringBuffer where = new StringBuffer(temp)
        	.append(" and scheduletime>? ");
        params.add(nowtime);
        params.add(accountid);//,nowtime, page.toArray()
        Pager<Schedule> pager = new Pager<Schedule>(pageNo, shd.count(where.toString(), params).intValue(),10);
        params.add(pager.getStart());
        params.add(pager.getPageSize());
       
        List<Schedule> list = shd.findUnOver(where.toString(), params);
        pager.setItems(list);
        return pager;
	}
	
public List<Schedule> findOver(Long accountid, String nowtime) {
		
		List<Object> params = new ArrayList<Object>();
        StringBuffer where = new StringBuffer(" where 1 = 1 ")
    		.append(" and scheduletime<? ");
        params.add(nowtime);
        params.add(accountid);
        return shd.findOver(where.toString(), params);
	}
	
public List<Schedule> findOverUndoList(Long accountid, String nowtime) {
	
	List<Object> params = new ArrayList<Object>();
    StringBuffer where = new StringBuffer(" where 1 = 1 ")
		.append(" and scheduletime<? ");
    params.add(nowtime);
    params.add(accountid);
    return shd.findOverUndo(where.toString(), params);
}

	@SuppressWarnings("unchecked")
	public Pager<Schedule> findOver(int pageNo, Long accountid, String nowtime, Object[] array) {
		
		String temp = (String) array[0];
        List<Object> params = (List<Object>) array[1];
        StringBuffer where = new StringBuffer(temp)
    		.append(" and scheduletime<? ");
        params.add(nowtime);
        params.add(accountid);
        Pager<Schedule> pager = new Pager<Schedule>(pageNo, shd.count(where.toString(), params).intValue(),10);
        List<Object>page = new ArrayList<Object>();
        page.add(pager.getStart());
        page.add(pager.getPageSize());
       
        List<Schedule> list = shd.findOver(where.toString(), params);
        pager.setItems(list);
        return pager;
	}
	
public Map<String, List<Schedule>> findUnOver(Long accountid, String nowtime) {
	
		Object[] stime = (Object[]) shd.findUnovertime(nowtime).toArray();
		Map<String, List<Schedule>> maps = new HashMap<String ,List<Schedule>>();
		for(int i = 0; i < stime.length; i++){
			System.out.println(stime);
			 List<Schedule> list = shd.findUnOverByTime((String)stime[i], accountid);
			 if(!list.isEmpty()){
				 maps.put((String)stime[i], list);
			 }
		}
        return maps;
        
	}

	public Schedule getSchedule(String schtitle, String schfromtime, String scheduletime,
			Boolean schviews, boolean schstate, Long schsortid, Long custid, Long chanceid, Long schaccountid) {
		Schedule sch = new Schedule();
		sch.setSchtitle(schtitle);
		sch.setSchfromtime(schfromtime);
		sch.setScheduletime(scheduletime);
		sch.setSchviews(schviews);
		sch.setSchstates(schstate);
		sch.setSchsortid(schsortid);
		sch.setSchcustid(custid);
		sch.setSchchanceid(chanceid);
		sch.setSchaccountid(schaccountid);
		return sch;
	}

	public Map<String, List<Schedule>> findUnOverNodone(Long accountid, String nowtime) {
		Object[] stime = (Object[]) shd.findUnoverNodonetime(nowtime).toArray();
		Map<String, List<Schedule>> maps = new HashMap<String ,List<Schedule>>();
		for(int i = 0; i < stime.length; i++){
			System.out.println(stime);
			 List<Schedule> list = shd.findUnOverNodoneByTime((String)stime[i], accountid);
			 if(!list.isEmpty()){
				 maps.put((String)stime[i], list);
			 }
		}
        return maps;
	}

	public List<Schedule> findByStates(Boolean schstates, Long accountid) {
		return shd.findByStates(schstates, accountid);
	}

	public int updateUndoState(Long scheduleid) {
		return shd.updateUndoState(scheduleid);
	}

	public List<Schedule> ShowUnOverNodone(Long accountid, String nowtime) {
		return shd.showUnOverNodone(accountid, nowtime);
	}

	public List<Schedule> findUnoverByToday(Long accountid, String nowtime) {
		return shd.findUnOverByTime(nowtime, accountid);
	}

	public List<Schedule> findUnoverUndoByToday(Long accountid, String nowtime) {
		return shd.findUndoByToday(nowtime, accountid);
	}

	public int overUndoCount(Long accountid, String nowtime) {
		return shd.overUndoCount(accountid, nowtime).intValue();
	}

	public int unoverUndoCount(Long accountid, String nowtime) {
		return shd.unoverUndoCount(accountid, nowtime).intValue();
	}

	public int doneCount(Long accountid) {
		return shd.doneCount(accountid).intValue();
	}

	public List<Schedule> findUndo(Long accountId, String startTime, String endTime) {
		return shd.findUndo(accountId, startTime, endTime);
	}

	public Map<String,List<Schedule>> findUndoByCustid(Long custid, String nowtime) {
		Map<String, List<Schedule>> maps = new HashMap<String ,List<Schedule>>();
		List<Schedule> overs = shd.findOverUndoByCustid(custid, nowtime);
		List<Schedule> unovers = shd.findUnoverNodoByCustid(custid ,nowtime);
		maps.put("overs", overs);
		maps.put("unovers", unovers);
		return maps;
	}

	
}
