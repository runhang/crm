package com.chen.dao;

import java.util.List;

import com.chen.entity.Sort;
import com.chen.util.DBHelper;;

public class SortDAO {

	private DBHelper dbHelper;
	
	public SortDAO(){
		dbHelper = new DBHelper();
	}
	
	public long addSort(Sort sort){
		String sql = " insert into t_sort (sortname, sortcolor) values(?,?) ";
		return dbHelper.insert(Long.class, sql, sort.getSortname(), sort.getSortcolor()).longValue();
	}

	public int delsort(Long id){
		String sql = " delete from t_sort where id=? ";
		return dbHelper.delete(sql, id);
	}
	
	public int changeSortname (Sort sort){
		String sql = " update t_sort set sortname=? where id=? ";
		return dbHelper.update(sql, sort.getSortname(), sort.getId());
	}
	
	public int changeSortcolor(Sort sort){
		String sql = " update t_sort set sortcolor=? where id=? ";
		return dbHelper.update(sql, sort.getSortcolor(), sort.getId());
	}
	
	public List<Sort> findAll(){
		String sql = " select id, sortname, sortcolor from t_sort order by id desc ";
		return dbHelper.queryList(sql, Sort.class);
	}

	public int changeSort(Sort sort) {
		String sql = " update t_sort set sortname=?, sortcolort=? where id=? order by id desc ";
		return dbHelper.update(sql, sort.getSortname(), sort.getSortcolor(), sort.getId());
	}

	public Object findBySortName(String sortname) {
		String sql = " select id, sortname, sortcolor from t_sort where sortname=? ";
		return dbHelper.queryObject(sql, Sort.class, sortname);
	}

	public Sort findBySortId(Long sortid) {
		String sql = " select sortname, sortcolor from t_sort where id=? ";
		return dbHelper.queryObject(sql, Sort.class, sortid);
	}
	
}
