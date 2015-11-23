package com.chen.dao;

import java.awt.Event;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chen.entity.Account;
import com.chen.entity.Chance;
import com.chen.entity.ChanceEvent;
import com.chen.exception.DataAccessException;
import com.chen.util.DBHelper;
import com.chen.util.DBManager;

public class EventDAO {

		private DBHelper dbHelper;
		
		public EventDAO() {
			dbHelper = new DBHelper();
		}
		
		public long addEvent(ChanceEvent event){
			StringBuffer sql = new StringBuffer(" insert into t_event (evcontent, evchanceid, ")
					.append(" evaccountid, evcreatetime, evstate)values(?,?,?,?,?) ");
			return dbHelper.insert(Long.TYPE, sql.toString(), event.getEvcontent(), event.getEvchanceid(),
						event.getEvaccountid(), event.getEvcreatetime(), event.getEvstate());
		}
		
		public int updateState(ChanceEvent event){
			String sql = " update t_content set evchstate=? where id=? ";
			return dbHelper.update(sql, event.getEvstate(), event.getId());
		}
		
		public Event findById(Long id){
			StringBuffer sql = new StringBuffer(" select id, evcontent, evchanceid, chaccountid ")
			 		.append(" evcreatetime, evstate from t_chance where id=?");
			return dbHelper.queryObject(sql.toString(), Event.class, id);
		}
		
		public List<Event> findList(String where, List<Object> params){
			StringBuffer sql = new StringBuffer(" select id, evcontent evchanceid, evaccountid, ")
	 		.append(" evcreatetime evstate from t_cevent ")
	 		.append(where)
	 		.append(" order by id desc limit ?,? ");
			return dbHelper.queryList(sql.toString(), Event.class, params.toArray());
		}
		
		public Long count(String where, List<Object> params){
			StringBuffer sql = new StringBuffer(" select count(*) from t_event ");
			sql.append(where);
	        return dbHelper.query(sql.toString(),Long.class,params.toArray());
		}

		public List<ChanceEvent> find(String where, List<Object> params) {
			QueryRunner runner = new QueryRunner(DBManager.getDataSource());
			Logger log = LoggerFactory.getLogger(EventDAO.class);
			StringBuffer sql = new StringBuffer(" SELECT ev.id, evcontent, evchanceid, evaccountid, evcreatetime, evstate, ")
				.append(" chtitle, chcontent, username FROM t_event AS ev  ")
				.append(" LEFT JOIN t_chance AS ch ON ch.id = ev.evchanceid ")
				.append(" LEFT JOIN t_account AS ac  ON ac.id = ev.evaccountid ")
				.append(where)
				.append(" order by ev.id desc limit ?, ? ");
			try {
				return runner.query(sql.toString(), new AbstractListHandler<ChanceEvent>(){

					@Override
					protected ChanceEvent handleRow(ResultSet rs) throws SQLException {
						ChanceEvent ce = new ChanceEvent();
						ce.setId(rs.getLong("id"));
						ce.setEvcontent(rs.getString("evcontent"));
						ce.setEvaccountid(rs.getLong("evaccountid"));
						ce.setEvchanceid(rs.getLong("evchanceid"));
						ce.setEvcreatetime(rs.getString("evcreatetime"));
						ce.setEvstate(rs.getInt("evstate"));
						Chance chance = new Chance();
						chance.setChtitle(rs.getString("chtitle"));
						chance.setChcontent(rs.getString("chcontent"));
						Account account = new Account();
						account.setUsername(rs.getString("username"));
						ce.setChance(chance);
						ce.setAccount(account);
						return ce;
					}
					
				}, params.toArray());
			} catch (SQLException e) {
				log.error("执行[{}]异常 {}",sql,e);
				throw new DataAccessException("执行["+sql+"]异常",e);
				//e.printStackTrace();
			}
		}

		public List<ChanceEvent> findAll() {
			QueryRunner runner = new QueryRunner(DBManager.getDataSource());
			Logger log = LoggerFactory.getLogger(EventDAO.class);
			StringBuffer sql = new StringBuffer(" SELECT ev.id, evcontent, evchanceid, evaccountid, evcreatetime, evstate, ")
				.append(" chtitle, chcontent, username FROM t_event AS ev  ")
				.append(" LEFT JOIN t_chance AS ch ON ch.id = ev.evchanceid ")
				.append(" LEFT JOIN t_account AS ac  ON ac.id = ev.evaccountid ")
				.append(" order by ev.id desc ");
			try {
				return runner.query(sql.toString(), new AbstractListHandler<ChanceEvent>(){

					@Override
					protected ChanceEvent handleRow(ResultSet rs) throws SQLException {
						ChanceEvent ce = new ChanceEvent();
						ce.setId(rs.getLong("id"));
						ce.setEvcontent(rs.getString("evcontent"));
						ce.setEvaccountid(rs.getLong("evaccountid"));
						ce.setEvchanceid(rs.getLong("evchanceid"));
						ce.setEvcreatetime(rs.getString("evcreatetime"));
						ce.setEvstate(rs.getInt("evstate"));
						Chance chance = new Chance();
						chance.setChtitle(rs.getString("chtitle"));
						chance.setChcontent(rs.getString("chcontent"));
						Account account = new Account();
						account.setUsername(rs.getString("username"));
						ce.setChance(chance);
						ce.setAccount(account);
						return ce;
					}
					
				});
			} catch (SQLException e) {
				log.error("执行[{}]异常 {}",sql,e);
				throw new DataAccessException("执行["+sql+"]异常",e);
				//e.printStackTrace();
			}
		}
	
	
}
