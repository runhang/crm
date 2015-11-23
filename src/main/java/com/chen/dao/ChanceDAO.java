package com.chen.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chen.entity.Account;
import com.chen.entity.Chance;
import com.chen.entity.Customer;
import com.chen.exception.DataAccessException;
import com.chen.util.DBHelper;
import com.chen.util.DBManager;

public class ChanceDAO {

	private DBHelper dbHelper;
	
	private static Logger log;
	static{
		log = LoggerFactory.getLogger(ChanceDAO.class);
	}
	
	public ChanceDAO() {
		dbHelper = new DBHelper();
	}
	
	
	public long addChance(Chance chance){
		StringBuffer sql = new StringBuffer(" insert into t_chance (chtitle, chcustid, ")
				.append(" chaccountid, chcontent, chmoney, chstate, chviews, chcreatetime)values(?,?,?,?,?,?,?,?) ");
		return dbHelper.insert(Long.TYPE, sql.toString(), chance.getChtitle(), chance.getChcustid(),
					chance.getChaccountid(), chance.getChcontent(), chance.getChmoney(), chance.getChstate(),
					chance.getChviews(), chance.getChcreatetime());
	}
	
	public int updateState(Chance chance){
		String sql = " update t_content set chstate=? where id=? ";
		return dbHelper.update(sql, chance.getChstate(), chance.getId());
	}
	
	public Chance findById(Long id){
		StringBuffer sql = new StringBuffer(" select id, chtitle, chcustid, chaccountid, chcontent, ")
		 		.append(" chmoney, chstate, chviews, chcreatetime from t_chance where id=?");
		return dbHelper.queryObject(sql.toString(), Chance.class, id);
	}
	
	public List<Chance> findList(String where, List<Object> params){
		StringBuffer sql = new StringBuffer(" select id, chtitle, chcustid, chaccountid, chcontent, ")
 			.append(" chmoney, chstate, chviews, chcreatetime from t_chance ")
 			.append(where)
 			.append(" order by id desc limit ?,? ");
		return dbHelper.queryList(sql.toString(), Chance.class, params.toArray());
	}
	
	public Long count(String where, List<Object> params){
		StringBuffer sql = new StringBuffer(" select count(*) from t_chance ");
		sql.append(where);
        return dbHelper.query(sql.toString(),Long.class,params.toArray());
	}

	public List<Chance> find(String where, List<Object> params) {
		
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer("SELECT  ch.id, chtitle, chcustid, chaccountid, ch.chcontent, chmoney, chstate, chviews, ")
 			.append(" ch.chcreatetime, cu.custname, ac.username FROM t_chance AS ch  ")
 			.append(" LEFT JOIN t_customer AS cu ON ch.chcustid = cu.id ")
 			.append(" LEFT JOIN t_account AS ac ON ch.chaccountid = ac.id ")
 			.append(where)
 			.append(" order by ch.id desc limit ?,? ");
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Chance>(){

				@Override
				protected Chance handleRow(ResultSet rs) throws SQLException {
					Chance chance = new Chance();
					chance.setId(rs.getLong("id"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setChcustid(rs.getLong("chcustid"));
					chance.setChaccountid(rs.getLong("chaccountid"));
					chance.setChcontent(rs.getString("chcontent"));
					chance.setChmoney(rs.getDouble("chmoney"));
					chance.setChstate(rs.getInt("chstate"));
					chance.setChviews(rs.getBoolean("chviews"));
					chance.setChcreatetime(rs.getString("chcreatetime"));
					Customer customer = new Customer();
					customer.setCustname(rs.getString("custname"));
					customer.setId(chance.getChcustid());
					Account account = new Account();
					account.setUsername(rs.getString("username"));
					account.setId(chance.getChaccountid());
					chance.setCustomer(customer);
					chance.setAccount(account);
					return chance;
				}}, params.toArray());
		} catch (SQLException e) {
			log.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
			//e.printStackTrace();
		}
	}


	public int chanceSuccess(Long id) {
		String sql =" update t_chance set chstate=? where id=?";
		return dbHelper.update(sql, 1, id);
	}
	
	public int chanceFail(Long id) {
		String sql =" update t_chance set chstate=? where id=?";
		return dbHelper.update(sql,2, id);
	}
	
	public int chanceOver(Long id) {
		String sql =" update t_chance set chstate=? where id=?";
		return dbHelper.update(sql, 3, id);
	}
	
	public int chanceDoing(Long id) {
		String sql =" update t_chance set chstate=? where id=?";
		return dbHelper.update(sql, 0, id);
	}


	public Double totalMoney(String where, List<Object> params) {
		StringBuffer sql = new StringBuffer(" select sum(chmoney) from t_chance ")
				.append(where);
		return dbHelper.queryScalar(sql.toString(), Double.class, params.toArray());
	}
}
