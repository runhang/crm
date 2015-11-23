package com.chen.dao;

import java.util.List;

import com.chen.entity.Trends;
import com.chen.util.DBHelper;

public class TrendsDAO {

	private DBHelper dbHelper;
	
	public TrendsDAO(){
		dbHelper = new DBHelper();
	}
	
	public long save(Trends trends){
		String sql = " insert into t_trends(tretype, tretitle, trecontent, trecreatetime, treaccountid)values(?,?,?,?,?) ";
		return dbHelper.insert(sql, trends.getTretype(), trends.getTretitle(),trends.getTrecontent(),
						trends.getTrecreatetime(), trends.getTreaccountid());
	}
	
	public List<Trends> findAll(Long accountid){
		StringBuffer sql = new StringBuffer(" SELECT tr.id, tretype, tretitle, trecontent, trecreatetime, treaccountid, username ")
					.append(" FROM t_trends AS tr LEFT JOIN t_account AS ac    ")
					.append(" ON tr.treaccountid = ac.id WHERE tr.treaccountid = ? order by tr.id desc");
		return dbHelper.queryList(sql.toString(), Trends.class, accountid);
	}

	public Long count(Long accountid) {
		String sql = " select count(*) from t_trends where treaccountid=? ";
		return dbHelper.queryScalar(sql, Long.class, accountid);
	}

	public List<Trends> findByPage(Long accountid, Integer startPage,
			Integer pageSize) {
		StringBuffer sql = new StringBuffer(" SELECT tr.id, tretype, tretitle, trecontent, trecreatetime, treaccountid, username ")
			.append(" FROM t_trends AS tr LEFT JOIN t_account AS ac    ")
			.append(" ON tr.`treaccountid` = ac.id WHERE tr.treaccountid = ? order by tr.id desc limit ?, ? ");
		return dbHelper.queryList(sql.toString(), Trends.class, accountid, startPage, pageSize);
}
	
	
}
