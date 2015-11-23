package com.chen.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.slf4j.LoggerFactory;

import com.chen.entity.Account;
import com.chen.entity.Chance;
import com.chen.entity.Customer;
import com.chen.entity.Schedule;
import com.chen.entity.Sort;
import com.chen.exception.DataAccessException;
import com.chen.util.DBHelper;
import com.chen.util.DBManager;

public class ScheduleDAO {

	private DBHelper dbHelper;
	
	public ScheduleDAO(){
		dbHelper = new DBHelper();
	}
	
	public long addSchedule(Schedule schedule){
		StringBuffer sql = new StringBuffer(" insert into t_schedule(schtitle, schcreatetime, schfromtime, scheduletime, schstates, ")
					.append(" schviews, schsortid, schcustid, schchanceid, schaccountid)values(?,?,?,?,?,?,?,?,?,?) ");
		return dbHelper.insert(Long.class, sql.toString(), schedule.getSchtitle(), schedule.getSchcreatetime(), schedule.getSchfromtime(),schedule.getScheduletime(),
							schedule.getSchstates(), schedule.getSchviews(), schedule.getSchsortid(), schedule.getSchcustid(),
							schedule.getSchchanceid(), schedule.getSchaccountid()).longValue();
	}
	
	public int updateDoneSchedule(Long id){
		String sql = " update t_schedule set schstates=? where id=? ";
		return dbHelper.update(sql, 1, id);
	}
	/**
	 * 
	 * @param accountid
	 * @param nowtime
	 * @param where
	 * @param params
	 * @return 未过期的日程
	 */
	public List<Schedule> findUnOverByPager(String where, List<Object>params){
		StringBuffer sql = new StringBuffer(" select schtitle, schcreatetime, schfromtime, scheduletime, schstates, ")
			.append(" schviews, schsortid, schcustid, schchanceid, schaccountid from t_schedule ")
			.append(where)
			.append(" and (schviews=1 or schaccountid=? ) order by id desc limit ?, ? ");
		return dbHelper.queryList(sql.toString(), Schedule.class, params.toArray());
	}

	public List<Schedule> findUnOver(String where, List<Object>params){
		StringBuffer sql = new StringBuffer(" select schtitle, schcreatetime, schfromtime,scheduletime, schstates, ")
			.append(" schviews, schsortid, schcustid, schchanceid, schaccountid from t_schedule ")
			.append(where)
			.append(" and (schviews=1 or schaccountid=? ) order by id desc ");
		return dbHelper.queryList(sql.toString(), Schedule.class, params.toArray());
	}
	
	public int delete(Long id) {
		String sql = " delete from where id=? ";
		return dbHelper.delete(sql, id);
	}

	public List<Schedule> findByPage(String where, List<Object> params) {
		StringBuffer sql = new StringBuffer(" select schtitle, schcreatetime, schfromtime, scheduletime, schstates, ")
		.append(" schviews, schsortid, schcustid, schchanceid, schaccountid from t_schedule")
		.append(where)
		.append(" and (schviews=1 or schaccountid=? ) order by id limit ?, ? ");
		
		return dbHelper.queryList(sql.toString(), Schedule.class, params);
	}

	public Long count(String where, List<Object>params) {
		StringBuffer sql = new StringBuffer(" select count(*) from t_schedule ")
			.append(where)
			.append(" and (schviews=1 or schaccountid=?) ");
		return dbHelper.queryScalar(sql.toString(), Long.class, params.toArray());
	}

	public List<Schedule> findOverBypager(String where, List<Object> params) {
		StringBuffer sql = new StringBuffer(" select schtitle, schcreatetime, schfromtime, scheduletime, schstates, ")
			.append(" schviews, schsortid, schcustid, schchanceid, schaccountid from t_schedule ")
			.append(where)
			.append(" and (schviews=1 or schaccountid=? )  order by id desc limit ?, ? ");
		return dbHelper.queryList(sql.toString(), Schedule.class, params.toArray());
	}
	
	public List<Schedule> findOver(String where, List<Object> params) {
		StringBuffer sql = new StringBuffer(" select id, schtitle, schcreatetime, schfromtime, scheduletime, schstates, ")
			.append(" schviews, schsortid, schcustid, schchanceid, schaccountid from t_schedule ")
			.append(where)
			.append(" and (schviews=1 or schaccountid=? )  order by id desc ");
		return dbHelper.queryList(sql.toString(), Schedule.class, params.toArray());
	}

	public List<Schedule> findOverUndo(String where, List<Object> params) {
		StringBuffer sql = new StringBuffer(" select id, schtitle, schcreatetime, schfromtime, scheduletime, schstates, ")
			.append(" schviews, schsortid, schcustid, schchanceid, schaccountid from t_schedule ")
			.append(where)
			.append(" and schstates=0 and (schviews=1 or schaccountid=? )  order by id desc ");
		return dbHelper.queryList(sql.toString(), Schedule.class, params.toArray());
	}
	
	public List<String> findUnovertime(String nowtime) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		String sql = " SELECT DISTINCT( DATE_FORMAT(scheduletime,'%Y-%m-%d') ) AS scheduletime FROM t_schedule where scheduletime>=? ";
		LoggerFactory.getLogger(ScheduleDAO.class).debug(sql);
		try {
			return runner.query(sql,new AbstractListHandler<String>() {
					
				@Override
				protected String handleRow(ResultSet rs) throws SQLException {
					return rs.getString("scheduletime");
				}
			}, nowtime);
		} catch (SQLException e) {
			LoggerFactory.getLogger(ScheduleDAO.class).error("{}异常",sql,e);
			throw new DataAccessException("{"+sql+"}异常", e);
			//e.printStackTrace();
		}
	}

	public List<Schedule> findUnOverByTime(String stime, Long accountid) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schfromtime schcreatetime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, tel, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 AND (DATE_FORMAT(scheduletime,'%Y-%m-%d') =?")
			.append(" and (schviews=1 or schaccountid=? )  order by sch.id desc ");
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					customer.setCustname(rs.getString("tel"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}},stime, accountid);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	public List<String> findUnoverNodonetime(String nowtime) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		String sql = " SELECT DISTINCT(DATE_FORMAT(scheduletime,'%Y-%m-%d')) as scheduletime FROM t_schedule where schstates=0 and scheduletime>=?";
		LoggerFactory.getLogger(ScheduleDAO.class).debug(sql);
		try {
			return runner.query(sql,new AbstractListHandler<String>() {
					
				@Override
				protected String handleRow(ResultSet rs) throws SQLException {
					return rs.getString("scheduletime");
				}
			}, nowtime);
		} catch (SQLException e) {
			LoggerFactory.getLogger(ScheduleDAO.class).error("{}异常",sql,e);
			throw new DataAccessException("{"+sql+"}异常", e);
			//e.printStackTrace();
		}
	}

	public List<Schedule> findUnOverNodoneByTime(String stime, Long accountid) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schcreatetime, schfromtime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 AND DATE_FORMAT(scheduletime,'%Y-%m-%d') =? and schstates=0 ")
			.append(" and (schviews=1 or schaccountid=? ) order by sch.id desc ");
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}}, stime, accountid);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	public List<Schedule> findByStates(Boolean schstates, Long accountid) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schcreatetime, schfromtime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 and schstates=? ")
			.append(" and (schviews=1 or schaccountid=? ) order by sch.id desc ");
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}}, schstates, accountid);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	public int updateUndoState(Long scheduleid) {
		String sql = " update t_schedule set schstates=? where id=? ";
		return dbHelper.update(sql, 0, scheduleid);
	}

	public List<Schedule> showUnOverNodone(Long accountid, String nowtime) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schcreatetime, schfromtime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 and schstates=? and scheduletime>=? ")
			.append(" and (schviews=1 or schaccountid=? ) order by sch.id desc ");
		LoggerFactory.getLogger(Schedule.class).debug("执行{}语句",sql);
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}}, 0, nowtime, accountid);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	public List<Schedule> findUnOverUndoByTime(String nowtime, Long accountid) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schcreatetime, schfromtime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 and schstates=? and (scheduletime>=? and scheduletime<=? )")
			.append(" and (schviews=1 or schaccountid=? ) order by sch.id desc ");
		LoggerFactory.getLogger(Schedule.class).debug("执行{}语句",sql);
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}}, 0, nowtime, nowtime, accountid);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	public Long overUndoCount(Long accountid, String nowtime) {
		String sql = " select count(*) from t_schedule where scheduletime<=? and schstates=0 and schaccountid=? ";
		return dbHelper.queryScalar(sql, Long.class, nowtime, accountid);
	}

	public Long unoverUndoCount(Long accountid, String nowtime) {
		String sql = " select count(*) from t_schedule where scheduletime>=? and schstates=0 and schaccountid=? ";
		return dbHelper.queryScalar(sql, Long.class, nowtime, accountid);
	}

	public Long doneCount(Long accountid) {
		String sql = " select count(*) from t_schedule where schstates=1 and schaccountid=? ";
		return dbHelper.queryScalar(sql, Long.class, accountid);
	}

	public List<Schedule> findUndoByToday(String nowtime, Long accountid) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schcreatetime, schfromtime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 and schstates=? and (scheduletime>=? and schcreatetime<=? )")
			.append(" and (schviews=1 or schaccountid=? ) order by sch.id desc ");
		LoggerFactory.getLogger(Schedule.class).debug("执行{}语句",sql);
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}}, 0, nowtime, nowtime, accountid);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	public List<Schedule> findUndo(Long accountId, String startTime,
			String endTime) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schcreatetime, schfromtime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 and schstates=? and (schfromtime>=? and scheduletime<=? )")
			.append(" and (schviews=1 or schaccountid=? ) order by sch.id desc ");
		LoggerFactory.getLogger(Schedule.class).debug("执行{}语句",sql);
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}}, 0, startTime, endTime, accountId);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	public List<Schedule> findUndo(Long accountId) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schcreatetime, schfromtime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 and schstates=?")
			.append(" and (schviews=1 or schaccountid=? ) order by sch.id desc ");
		LoggerFactory.getLogger(Schedule.class).debug("执行{}语句",sql);
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}}, 0, accountId);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	public List<Schedule> findOverUndoByCustid(Long custid, String nowtime) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schcreatetime, schfromtime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 and schstates=? and scheduletime <= ? ")
			.append(" and (schviews=1 or schcustid=? ) order by sch.id desc ");
		LoggerFactory.getLogger(Schedule.class).debug("执行{}语句",sql);
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}}, 0, nowtime, custid);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	public List<Schedule> findUnoverNodoByCustid(Long custid, String nowtime) {
		QueryRunner runner = new QueryRunner(DBManager.getDataSource());
		StringBuffer sql = new StringBuffer(" SELECT sch.id, schtitle, schcreatetime, schfromtime, scheduletime, ")
			.append("  schstates,schviews,sortname, sortcolor, schsortid, schcustid, ")
			.append(" custname, schchanceid, chtitle, schaccountid, username ")
			.append(" FROM t_schedule AS sch ")
			.append("  LEFT JOIN t_sort AS sort ON sch.schsortid = sort.id ")
			.append("  LEFT JOIN t_customer AS cust ON sch.schcustid = cust.id ")
			.append("  LEFT JOIN t_chance AS ch ON sch.schchanceid = ch.id ")
			.append("  LEFT JOIN t_account AS acc ON sch.schaccountid = acc.id ")
			.append("  WHERE 1 = 1 and schstates=? and scheduletime > ? ")
			.append(" and (schviews=1 or schcustid=? ) order by sch.id desc ");
		LoggerFactory.getLogger(Schedule.class).debug("执行{}语句",sql);
		try {
			return runner.query(sql.toString(), new AbstractListHandler<Schedule>(){

				@Override
				protected Schedule handleRow(ResultSet rs) throws SQLException {
					Schedule schedule = new Schedule();
					BeanProcessor bp = new BeanProcessor();
					schedule = bp.toBean(rs, Schedule.class);
					Sort sort = new Sort();
					Customer customer = new Customer();
					Chance chance = new Chance();
					Account account = new Account();
					sort.setSortname(rs.getString("sortname"));
					sort.setId(schedule.getSchsortid());
					sort.setSortcolor(rs.getString("sortcolor"));
					customer.setCustname(rs.getString("custname"));
					chance.setChtitle(rs.getString("chtitle"));
					chance.setId(schedule.getSchchanceid());
					account.setUsername(rs.getString("username"));
					account.setId(schedule.getSchaccountid());
					schedule.setSort(sort);
					schedule.setCustomer(customer);
					schedule.setChance(chance);
					schedule.setAccount(account);
					System.out.println(schedule.getSchcreatetime());
					return schedule;
				}}, 0, nowtime, custid);
			} catch (SQLException e) {
				throw new DataAccessException("{"+sql+"}异常",e);
				//e.printStackTrace();
			}
	}

	

	

	
	
}
