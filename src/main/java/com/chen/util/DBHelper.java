package com.chen.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chen.exception.DataAccessException;


public class DBHelper {
	
	private static Logger logger = null;
	
	static{
		logger = LoggerFactory.getLogger(DBHelper.class);
	}
	
	public int modify(String sql, Object... params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		try {
			logger.debug(sql);
			return runner.update(sql, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
		
	}
	
	public int delete(String sql, Object...params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		try {
			logger.debug(sql);
			return runner.update(sql, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
			//return -1;
		}
		
	}
	
	public int insert(String sql, Object...params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		try {
			logger.debug(sql);
			return runner.update(sql, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			//return -1;
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}

	}
	
	//getKey is true, return stmt.getGeneratedKeys()
	public <T> T insert(Class<T> returnType, String sql, Object...params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		ScalarHandler<T> sh = new ScalarHandler<T>();
		//返回子增键，默认为自增
		try {
			logger.debug(sql);
			return runner.insert(sql, sh, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			//return null;
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
	}
	
	//query table
	public <T> T queryObject(String sql, Class<T> cls, Object... params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		BeanHandler<T> beanHandler = new BeanHandler<T>(cls); 
		try {
			logger.debug(sql);
			return runner.query(sql, beanHandler, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			//return null;
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
	}
	
	
	public <T> List<T> queryList(String sql, Class<T>cls, Object...params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		BeanListHandler<T>rsh = new BeanListHandler<T>(cls);
		try {
			logger.debug(sql);
			return runner.query(sql, rsh, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			//return null;
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
	}
	
	
	public List<Map<String, Object>> queryMapList(String sql, Object...params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		MapListHandler rsh = new MapListHandler();
		try {
			logger.debug(sql);
			return runner.query(sql, rsh, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			//return null;
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
	}
	
	public Map<String, Object>queryMap(String sql, Object...params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		MapHandler rsh = new MapHandler();
		try {
			logger.debug(sql);
			return runner.query(sql, rsh, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			//return null;
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
	}

	
	public <T> T queryScalar(String sql, Class<T> cls, Object...params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		ScalarHandler<T> sch = new ScalarHandler<T>();
		try {
			logger.debug(sql);
			return runner.query(sql, sch,params);
		} catch (SQLException e) {
			//e.printStackTrace();
			//return null;
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
	}

	public <T> List<T> queryColumnList(String sql, Class<T> cls, Object...params){
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		ColumnListHandler<T> sch = new ColumnListHandler<T>();
		try {
			logger.debug(sql);
			return runner.query(sql, sch, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			//return null;
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
	}
	
	public <T> T query(String sql, Class<T> cls, Object... array) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		ScalarHandler<T> sch = new ScalarHandler<T>();
		try {
			logger.debug(sql);
			return runner.query(sql, sch, array);
		} catch (SQLException e) {
			//e.printStackTrace();
			//return null;
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
	}

	public int update(String sql, Object... params) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		try {
			logger.debug(sql);
			return runner.update(sql, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			logger.error("执行[{}]异常 {}",sql,e);
			throw new DataAccessException("执行["+sql+"]异常",e);
		}
	}
	
	
}
