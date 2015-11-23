package com.chen.entity;

import java.io.Serializable;

public class Schedule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String schtitle;
	private String schcreatetime;
	private String schfromtime;
	private String scheduletime;
	private Boolean schstates;
	private Boolean schviews;
	private Long schsortid;
	private Long schcustid;
	private Long schchanceid;
	private Long schaccountid;
	private Sort sort;
	private Customer customer;
	private Chance chance;
	private Account account;
	
	
	public String getSchfromtime() {
		return schfromtime;
	}
	public void setSchfromtime(String schfromtime) {
		this.schfromtime = schfromtime;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Chance getChance() {
		return chance;
	}
	public void setChance(Chance chance) {
		this.chance = chance;
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
	public String getSchtitle() {
		return schtitle;
	}
	public void setSchtitle(String schtitle) {
		this.schtitle = schtitle;
	}
	public String getSchcreatetime() {
		return schcreatetime;
	}
	public void setSchcreatetime(String schcreatetime) {
		this.schcreatetime = schcreatetime;
	}
	public String getScheduletime() {
		return scheduletime;
	}
	public void setScheduletime(String scheduletime) {
		this.scheduletime = scheduletime;
	}
	public Boolean getSchstates() {
		return schstates;
	}
	public void setSchstates(Boolean schstates) {
		this.schstates = schstates;
	}
	public Boolean getSchviews() {
		return schviews;
	}
	public void setSchviews(Boolean schviews) {
		this.schviews = schviews;
	}
	public Long getSchsortid() {
		return schsortid;
	}
	public void setSchsortid(Long schsortid) {
		this.schsortid = schsortid;
	}
	public Long getSchcustid() {
		return schcustid;
	}
	public void setSchcustid(Long schcustid) {
		this.schcustid = schcustid;
	}
	public Long getSchchanceid() {
		return schchanceid;
	}
	public void setSchchanceid(Long schchanceid) {
		this.schchanceid = schchanceid;
	}
	public Long getSchaccountid() {
		return schaccountid;
	}
	public void setSchaccountid(Long schaccountid) {
		this.schaccountid = schaccountid;
	}
	
	
	
	
}
