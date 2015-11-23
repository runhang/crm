package com.chen.dao;

import java.util.List;

import com.chen.entity.Department;
import com.chen.util.DBHelper;

public class DeptDAO {

	private DBHelper dbHelper = new DBHelper();
	
	public long addDept(Department dept){
		String sql = " insert t_dept(deptname)values(?)";
		return dbHelper.insert(Long.class ,sql, dept.getDeptname());
	}
	
	public int delDept(Long id){
		String sql = " delete from t_dept where id=? ";
		return dbHelper.delete(sql, id);
	}
	
	public int change(Department dept){
		String sql  = "update t_dept set deptname=? where id=? ";
		return dbHelper.modify(sql, dept.getDeptname(), dept.getId());
	}
	
	public List<Department> findAll(){
		String sql = " select id, deptname from t_dept ";
		return dbHelper.queryList(sql, Department.class);
	}

	public List<Department> find(String where, List<Object> params) {
		StringBuffer sql = new StringBuffer(" select id, deptname from t_dept ")
					.append(where).append(" order by id desc limit ?, ? ");
		return dbHelper.queryList(sql.toString(), Department.class,params.toArray());
	}

	public Department findById(Long id) {
		String sql = " select id, deptname from t_dept where id=? ";
		return dbHelper.queryObject(sql, Department.class, id);
	}

	public Long count(String where, List<Object> params) {
		StringBuffer sql = new StringBuffer("select count(*) from t_dept ")
    	.append(where);
    return dbHelper.query(sql.toString(),Long.class,params.toArray());
	}

	public Object findByName(String deptname) {
		String sql = " select id, deptname from t_dept where deptname=?";
		return dbHelper.queryObject(sql, Department.class, deptname);
	}

	public Long count() {
		String sql = " select count(*) from t_dept ";
		return dbHelper.query(sql, Long.class);
	}

	public Department find(int start, int end) {
		StringBuffer sql = new StringBuffer(" select id, deptname from t_dept ")
		.append(" order by id desc  limit ?, ? ");
		return dbHelper.queryObject(sql.toString(),Department.class, start, end);
	}
	
}
