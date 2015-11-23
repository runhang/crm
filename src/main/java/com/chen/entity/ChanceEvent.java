package com.chen.entity;

import java.io.Serializable;

public class ChanceEvent implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String evcontent;
	private Long evchanceid;
	private Long evaccountid;
	private String evcreatetime;
	private Integer evstate;
	private Chance chance;
	private Account account;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEvcontent() {
		return evcontent;
	}
	public void setEvcontent(String evcontent) {
		this.evcontent = evcontent;
	}
	public Long getEvchanceid() {
		return evchanceid;
	}
	public void setEvchanceid(Long evchanceid) {
		this.evchanceid = evchanceid;
	}
	public Long getEvaccountid() {
		return evaccountid;
	}
	public void setEvaccountid(Long evaccountid) {
		this.evaccountid = evaccountid;
	}
	public String getEvcreatetime() {
		return evcreatetime;
	}
	public void setEvcreatetime(String evcreatetime) {
		this.evcreatetime = evcreatetime;
	}
	public Integer getEvstate() {
		return evstate;
	}
	public void setEvstate(Integer evstate) {
		this.evstate = evstate;
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
	
	
	
	
}
