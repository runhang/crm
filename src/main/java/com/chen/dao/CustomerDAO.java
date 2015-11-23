package com.chen.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chen.entity.Account;
import com.chen.entity.Customer;
import com.chen.exception.DataAccessException;
import com.chen.util.DBHelper;
import com.chen.util.DBManager;

public class CustomerDAO {

	private DBHelper dbHelper = null;
	private static Logger log;
	static {
		log = LoggerFactory.getLogger(CustomerDAO.class);
	}
	
	public CustomerDAO(){
		dbHelper = new DBHelper();
	}
	
	public Customer findByName(String name){
		String sql = " select id, custname, namepinyin, jobs, companyid, tel, views, accountid, flags, address, im, website, content, email, createtime from t_customer where custname = ? ";
		return dbHelper.queryObject(sql, Customer.class, name);
	}
	
	public int changTel(Customer cust){
		String sql = " update t_customer set tel=? where id=? ";
		return dbHelper.modify(sql, cust.getTel(), cust.getId());
	}
	
	public int changeView(Customer cust){
		String sql = " update t_customer set views=? where id=? ";
		return dbHelper.modify(sql, cust.getViews(), cust.getId());
	}
	
	public int changeViewTel(Customer cust){
		String sql = " update t_customer set tel=?, views=? where id=?";
		return dbHelper.modify(sql, cust.getTel(), cust.getId());
	}
	
	public long addCompany(Customer cust){
		String sql = " insert into t_customer(custname, namepinyin, tel, email, views,accountid, flags, address, im, website, content, createtime)values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		return dbHelper.insert(Long.class, sql, cust.getCustname(), cust.getNamepinyin(), 
					cust.getTel(), cust.getEmail(), cust.getViews(), cust.getAccountid(), cust.getFlags(),
					cust.getAddress(), cust.getIm(), cust.getWebsite(), cust.getContent(), cust.getCreatetime());
	}
	
	public long addCustomer(Customer cust){
		String sql = " insert into t_customer(custname, namepinyin, jobs, companyid, tel, email, views, accountid, flags, address, im, website, content, createtime)values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return dbHelper.insert(Long.class, sql, cust.getCustname(), cust.getNamepinyin(), cust.getJobs(),
					cust.getCompanyid(), cust.getTel(), cust.getEmail(), cust.getViews(), 
					cust.getAccountid(), cust.getFlags(), cust.getAddress(), cust.getIm(), 
					cust.getWebsite(), cust.getContent(), cust.getCreatetime());
		
	}

	public long updateCompany(Customer cust, Long id) {
		String sql = " update t_customer set custname=?, namepinyin, tel=?, email=?, address=?, im=?, website=?, content=? where id=? ";
		return dbHelper.modify(sql, cust.getCustname(), cust.getTel(), cust.getEmail(), cust.getAddress(), cust.getIm(), cust.getWebsite(), cust.getContent(), id);
	}

	public Long count(String where, List<Object> params) {
		String sql ="select count(*) from t_customer " + where;
 		return dbHelper.query(sql, Long.class, params.toArray());
	}

	public List<Customer> find(String where, List<Object> params) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" select  id, custname, namepinyin, jobs, companyid, tel, views, accountid, flags, address, im, website, content, email, createtime, "); 
		sql.append(" (SELECT custname FROM t_customer AS a WHERE a.id = b.companyid) AS companyname ")
			.append(" from t_customer as b " )
			.append(where)
			.append(" order by id desc limit ?,? ");
			
		try {
			return runner.query(sql.toString().toString(), new AbstractListHandler<Customer>(){

				@Override
				protected Customer handleRow(ResultSet rs) throws SQLException {
					Customer customer = new Customer();
	                customer.setContent(rs.getString("content"));
	                customer.setFlags(rs.getBoolean("flags"));
	                customer.setCompanyid(rs.getLong("companyid"));
	                customer.setAccountid(rs.getLong("accountid"));
	                customer.setCustname(rs.getString("custname"));
	                customer.setNamepinyin(rs.getString("namepinyin"));
	                customer.setCreatetime(rs.getString("createtime"));
	                customer.setEmail(rs.getString("email"));
	                customer.setTel(rs.getString("tel"));
	                customer.setWebsite(rs.getString("website"));
	                customer.setId(rs.getLong("id"));
	                customer.setViews(rs.getBoolean("views"));
	                customer.setJobs(rs.getString("jobs"));

	                Customer company = new Customer();
	                company.setCustname(rs.getString("companyname"));
	                customer.setCompany(company);
	                return customer;
				}}, params.toArray());
		} catch (SQLException e) {
			log.error("执行[{}]异常{}", sql, e);
			throw new DataAccessException("执行["+sql.toString()+"]异常",e);
			//e.printStackTrace();
		}
		
	}

	public int updateContent(String content, Long id) {
		String sql = " update t_customer set content=? where id=?";
		return dbHelper.modify(sql, content, id);
	}

	public Customer findById(Long id) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" select b.id, custname, jobs, companyid, tel, views, accountid, flags, address, im, website, content, email, createtime, ")
					.append(" (select custname from t_customer as a where a.id = b.companyid) as companyname ")
					.append(" from t_customer as b where b.id = ? ");
		log.debug("执行[{}]", sql);
		try {
			return runner.query(sql.toString(), new ResultSetHandler<Customer>(){
				public Customer handle(ResultSet rs) throws SQLException {
					if(rs.next()) {
						return getResult(rs);
					}
					return null;
				}
				
			}, id);
		} catch (SQLException e) {
			log.error("执行[{}]异常{}", sql, e);
			throw new DataAccessException("执行["+sql.toString()+"]异常",e);
			//e.printStackTrace();
		}
	}
	
	public Customer getResult(ResultSet rs){
		
		Customer customer = new Customer();
        try {
        	customer.setContent(rs.getString("content"));
            customer.setFlags(rs.getBoolean("flags"));
            customer.setCompanyid(rs.getLong("companyid"));
            customer.setAccountid(rs.getLong("accountid"));
            customer.setCustname(rs.getString("custname"));
            customer.setCreatetime(rs.getString("createtime"));
            customer.setEmail(rs.getString("email"));
            customer.setTel(rs.getString("tel"));
            customer.setWebsite(rs.getString("website"));
			customer.setId(rs.getLong("id"));
			customer.setViews(rs.getBoolean("views"));
	        customer.setJobs(rs.getString("jobs"));
	        Customer company = new Customer();
	        company.setCustname(rs.getString("companyname"));
	        customer.setCompany(company);
	        return customer;
		} catch (SQLException e) {
			log.error("result error{}",e);
			//e.printStackTrace();
		}
        return null;
	}

	public String queryContent(Long id) {
		String sql = " select content from t_customer where id=?";
		return dbHelper.queryObject(sql, Customer.class, id).getContent();
	}

	public int delCust(Long id) {
		String sql = " delete from t_customer where id=?";
		return dbHelper.delete(sql, id);
	}

	public int editCustomer(Customer cust) {
		String sql = " update t_customer set custname=?, namepinyin=? jobs=?, companyid=?, tel=?, email=?, views=?, accountid=?, flags=?, address=?, im=?, website=?, content=? where id=?";
		return dbHelper.insert(sql, cust.getCustname(), cust.getNamepinyin(), cust.getJobs(),
					cust.getCompanyid(), cust.getTel(), cust.getEmail(), cust.getViews(), 
					cust.getAccountid(), cust.getFlags(), cust.getAddress(), cust.getIm(), 
					cust.getWebsite(), cust.getContent(),cust.getId());	
	}
	
	public int editCompany(Customer com) {
		String sql = " update t_customer set custname=?, namepinyin=?, tel=?, email=?, views=?, accountid=?, flags=?, address=?, im=?, website=?, content=? where id=?";
		return dbHelper.insert(sql, com.getCustname(), com.getNamepinyin(),com.getTel(), com.getEmail(), com.getViews(), 
					com.getAccountid(), com.getFlags(), com.getAddress(), com.getIm(), 
					com.getWebsite(), com.getContent(),com.getId());	
	}

	public List<Customer> findByName(String name, Account account) {
		StringBuffer custname = new StringBuffer("%")
				.append(name)
				.append("%");
		String sql = " SELECT id, custname FROM t_customer WHERE (custname like ? or namepinyin like ?) AND (accountid=? OR views=1)";
		return dbHelper.queryList(sql.toString(), Customer.class, custname.toString(), custname.toString(), account.getId());
	}

	
	
}
