package com.chen.service;

import java.util.List;

import com.chen.dao.SortDAO;
import com.chen.entity.Sort;

public class SortService {

	private SortDAO sd;
	
	public SortService(){
		sd = new SortDAO();
	}
	
	public long save(Sort sort){
		return sd.addSort(sort);
	}
	
	public int updateSortname(Sort sort){
		return sd.changeSortname(sort);
	}
	
	public int updateSortColor(Sort sort){
		return sd.changeSortcolor(sort);
	}
	
	public int updateSort(Sort sort){
		if(sort.getSortcolor() == null &&
			sort.getSortname() == null){
			return 0;
		}else if(sort.getSortcolor() == null && 
			sort.getSortname() != null){
			return sd.changeSortname(sort);
		}else if(sort.getSortname() == null && 
				sort.getSortcolor() != null){
			return sd.changeSortcolor(sort);
					
		}else {
			return sd.changeSort(sort);
		}
	}

	public List<Sort> findAll() {
		return sd.findAll();
	}

	public Object findBySortName(String sortname) {
		return sd.findBySortName(sortname);
	}

	public int delSort(Long id) {
		return sd.delsort(id);
	}

	public Sort findBySortId(Long sortid) {
		return sd.findBySortId(sortid);
	}
	
}
