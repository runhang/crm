package com.chen.service;

import java.util.List;

import com.chen.dao.DeptDAO;
import com.chen.entity.Department;
import com.chen.util.Pager;

public class DeptService {
	
private DeptDAO dd;
	
	public DeptService(){
		dd = new DeptDAO();
	}
	
	public long addDept(String deptname){
		Department dept = null; 
		if(dd.findByName(deptname) == null){
			dept = new Department();
			dept.setDeptname(deptname);;
			return dd.addDept(dept);
		}else{
			return 0;
		}
		
	}
	
	
	
	
	public int changeDept(String deptname, Long id) {
		if(id >= 0){
			System.out.println(id);
			Department dept = new Department();
			dept.setDeptname(deptname);
			dept.setId(id);
			return dd.change(dept);
		}else{
			return 0;
		}
		
	}

	public Department findById(Long id) {
		return dd.findById(id);
		
	}
	
	public List<Department> findAll(){
		return dd.findAll();
	}

	@SuppressWarnings("unchecked")
	public Pager<Department> findByPage(int pageNo, Object[] array) {
		String where = (String) array[0];
        List<Object> params = (List<Object>) array[1];
        Pager<Department> pager = new Pager<Department>(pageNo, dd.count(where,params).intValue(),10);
       
        params.add(pager.getStart());
        params.add(pager.getPageSize());
       
        List<Department> list = dd.find(where,params);
        pager.setItems(list);
        return pager;
	}

	public int delete(Long id) {
		return dd.delDept(id);
	}

	public Department findAfterOne(int pageNo) {
        Department dept = dd.find(pageNo*10, 1);
        return dept;
	}

	
}
