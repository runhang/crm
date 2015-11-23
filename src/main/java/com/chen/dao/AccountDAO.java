package com.chen.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chen.entity.Account;
import com.chen.entity.Department;
import com.chen.exception.DataAccessException;
import com.chen.util.DBHelper;
import com.chen.util.DBManager;

public class AccountDAO {

	private DBHelper dbHelper = new DBHelper();
	
	public long addAccount(Account account){
		StringBuffer sql = new StringBuffer(
				" insert into t_account( ");
		sql.append(" username, userpass, createtime, ");
		sql.append(" lastloginip, deptid )values(?,?,?,?,?)");
		return dbHelper.insert(Long.TYPE, sql.toString(), account.getUsername(),
				 account.getUserpass(), account.getCreatetime(), 
				 account.getLastloginip(), account.getDeptid());
		
	}
	
	public int deleteUser(Long id){
		String sql = " update t_account set del=? where id = ?";
		return dbHelper.delete(sql,1,id);
	}
	
	public int resetPwd(Long id, String pass){
		String sql = " update t_account set userpass=? where id=? ";
		return dbHelper.modify(sql, pass, id);
	}
	
	public List<Account> findAll(){
		StringBuffer sql = new StringBuffer(
				" select id username, userpass, ");
		sql.append(" createtime, lastlogintime, lastloginip, ");
		sql.append(" deptid, grade, useable from t_account where del=0");
		return dbHelper.queryList(sql.toString(), Account.class);
	}
	
	public List<Account> findByDeptid(Long deptid){
		StringBuffer sql = new StringBuffer(
				" select id, username, userpass, ");
		sql.append(" createtime, lastlogintime, lastloginip, ");
		sql.append(" deptid, grade, useable from t_account where del=0 and deptid=? ");
		return dbHelper.queryList(sql.toString(), Account.class, deptid);
	}
	
	public Account findByUsername(String username){
		StringBuffer sql = new StringBuffer(
				" select id, username,  userpass, ");
		sql.append("createtime, lastlogintime, lastloginip, ");
		sql.append("deptid, grade, useable from t_account where del=0 and username=? ");
		return dbHelper.queryObject(sql.toString(), Account.class, username);
	}
	
	public List<Account> find(String where,List<Object> param){
		Logger logger = LoggerFactory.getLogger(AccountDAO.class);
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(
				" select ac.id, username,  userpass, ")
			.append(" createtime, lastlogintime, lastloginip, ")
			.append(" ac.deptid, grade, useable, de.deptname from t_account as ac ")
			.append(" inner join t_dept as de on de.id = ac.deptid ").append(where).append(" and del=0 ")
			.append(" order by ac.id desc limit ?,? ");
			logger.debug("执行[{}]", sql);
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Account>() {
				
				@Override
				protected Account handleRow(ResultSet rs) throws SQLException {
					Account ac = new Account();
					Department dept = new Department();
					ac.setId(rs.getLong("id"));
					ac.setUsername(rs.getString("username"));
					ac.setUserpass(rs.getString("userpass"));
					ac.setCreatetime(rs.getString("createtime"));
					ac.setLastlogintime(rs.getString("lastlogintime"));
					ac.setLastloginip(rs.getString("lastloginip"));
					ac.setUseable(rs.getBoolean("useable"));
					ac.setGrade(rs.getInt("grade"));
					ac.setDeptid(rs.getLong("id"));
					dept.setDeptname(rs.getString("deptname"));
					dept.setId(ac.getDeptid());
					ac.setDept(dept);
					return ac;
				}
			} , param.toArray());
			
		} catch (SQLException e) {
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
			//e.printStackTrace();
		}
	}

	public int recode(Account account) {
		String sql = "update t_account set lastlogintime=?, lastloginip=? where id=?";
		return dbHelper.modify(sql, account.getLastlogintime(), account.getLastloginip()
				 	,account.getId());
	}

	public Long count(String where, List<Object> params) {
		StringBuffer sql = new StringBuffer(" select count(*) from t_account ");
		if(where == null || where == ""){
        	sql.append("where del=0");
        }else{
        	sql.append(where) .append(" and del=0 ");
        }
		System.out.println(sql.toString());
        return dbHelper.query(sql.toString(),Long.class,params.toArray());
    }

	public int banUser(Long id) {
		String sql = "update t_account set useable=? where del = 0 and id=?";
		return dbHelper.modify(sql.toString(),0, id);
	}

	public int changePass(Long id, String newpass) {
		String sql = " update t_account set userpass=? where del=0 and id=? ";
		return dbHelper.modify(sql, newpass, id);
	}

	public int activeUser(Long id) {
		String sql = "update t_account set useable=? where del=0 and id=?";
		return dbHelper.modify(sql, 1, id);
	}


	
}
