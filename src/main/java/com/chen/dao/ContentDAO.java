package com.chen.dao;

import java.util.List;

import com.chen.entity.Content;
import com.chen.util.DBHelper;

public class ContentDAO {

	private DBHelper dbHelper;
	
	public ContentDAO(){
		dbHelper = new DBHelper();
	}
	
	public int addContent(Content content){
		String sql = " insert into t_content(content, accountid, custid, createtime) values(?, ?, ?, ?) ";
		return dbHelper.insert(sql, content.getContent(),content.getAccountid(), 
					content.getCustid(), content.getCreatetime());
	}
	
	
	public List<Content> findByCustid(Long custid){
		StringBuffer sql = new StringBuffer(" select id, content, accountid, custid, ")
				.append("  createtime from t_content where custid=?  order by id desc ");
		return dbHelper.queryList(sql.toString(), Content.class, custid);
	}
	
	public List<Content> findAccountid(Long accountid){
		StringBuffer sql = new StringBuffer(" select id, content, accountid, custid, ")
				.append("  createtime from t_content where accountid=? order by id desc ");
		return dbHelper.queryList(sql.toString(), Content.class, accountid);
	}
	
	public List<Content> findAll(){
		StringBuffer sql = new StringBuffer(" select id, content, accountid, custid, ")
				.append("  createtime from t_content order by id desc ");
		return dbHelper.queryList(sql.toString(), Content.class);
	}
	
	
	public List<Content> find(Long custid, Integer start, Integer size){
		StringBuffer sql = new StringBuffer(" select id, content, accountid, custid, ")
			.append("  createtime from t_content where custid=? ")
			.append(" order by id desc limit ?, ?");
		return dbHelper.queryList(sql.toString(), Content.class, custid, start, size);
	}

	public Long count(Long custid) {
		StringBuffer sql = new StringBuffer(" select count(*) from t_content where custid=?");
		System.out.println(sql.toString());
        return dbHelper.query(sql.toString(),Long.class, custid);
    
	}
	
}
