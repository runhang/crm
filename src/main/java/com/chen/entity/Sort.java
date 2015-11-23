package com.chen.entity;

import java.io.Serializable;

public class Sort implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String sortname;
	private String sortcolor;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSortcolor() {
		return sortcolor;
	}
	public void setSortcolor(String sortcolor) {
		this.sortcolor = sortcolor;
	}
	
}
