package com.chen.entity;

import java.io.Serializable;

public class Department implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String deptname;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
}
