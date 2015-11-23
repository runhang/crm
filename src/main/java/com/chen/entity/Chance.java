package com.chen.entity;

import java.io.Serializable;

public class Chance  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String chtitle;
	private Long chcustid; //对应客户id
	private Long chaccountid;//添加账号的id
	private String chcontent;
	private Double chmoney;
	private Integer chstate; // 对应于时间的状态。成功。失败。跟踪，消亡
	private Boolean chviews;   //公开或私有化
	private String chcreatetime; //createtime
	private Customer customer;
	private Account account;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChtitle() {
		return chtitle;
	}
	public void setChtitle(String chtitle) {
		this.chtitle = chtitle;
	}
	public Long getChcustid() {
		return chcustid;
	}
	public void setChcustid(Long chcustid) {
		this.chcustid = chcustid;
	}
	public Long getChaccountid() {
		return chaccountid;
	}
	public void setChaccountid(Long chaccountid) {
		this.chaccountid = chaccountid;
	}
	public String getChcontent() {
		return chcontent;
	}
	public void setChcontent(String chcontent) {
		this.chcontent = chcontent;
	}
	public Double getChmoney() {
		return chmoney;
	}
	public void setChmoney(Double chmoney) {
		this.chmoney = chmoney;
	}
	public Integer getChstate() {
		return chstate;
	}
	public void setChstate(Integer chstate) {
		this.chstate = chstate;
	}
	public Boolean getChviews() {
		return chviews;
	}
	public void setChviews(Boolean chviews) {
		this.chviews = chviews;
	}
	public String getChcreatetime() {
		return chcreatetime;
	}
	public void setChcreatetime(String chcreatetime) {
		this.chcreatetime = chcreatetime;
	}
	
	
}
