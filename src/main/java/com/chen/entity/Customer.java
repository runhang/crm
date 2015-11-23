package com.chen.entity;

import java.io.Serializable;

public class Customer implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private String custname;
	private String namepinyin;
	private String jobs;
	private Long companyid; //用的是一个表结构，对应于本表的id
	private String tel;
	private String email;
	private Boolean views;// i true public， else private
	private Long accountid;
	private Boolean flags;//表示用户或公司，true为用户。false为公司
	private String address;
	private String im;
	private String website;
	private String content;
	private String createtime;
	public String getNamepinyin() {
		return namepinyin;
	}
	public void setNamepinyin(String namepinyin) {
		this.namepinyin = namepinyin;
	}
	private Customer company;
	
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Customer getCompany() {
		return company;
	}
	public void setCompany(Customer company) {
		this.company = company;
	}
	public void setFlags(Boolean flags) {
		this.flags = flags;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIm() {
		return im;
	}
	public void setIm(String im) {
		this.im = im;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getJobs() {
		return jobs;
	}
	public void setJobs(String jobs) {
		this.jobs = jobs;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Boolean getViews() {
		return views;
	}
	public void setViews(Boolean views) {
		this.views = views;
	}
	public Long getAccountid() {
		return accountid;
	}
	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}
	public Boolean getFlags() {
		return flags;
	}
	
	
	
}
