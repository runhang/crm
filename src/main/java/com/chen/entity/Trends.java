package com.chen.entity;

import java.io.Serializable;


public class Trends implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String tretype;
	private String tretitle;
	private String trecontent;
	private String trecreatetime;
	private Long treaccountid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTretype() {
		return tretype;
	}
	public void setTretype(String tretype) {
		this.tretype = tretype;
	}
	public String getTretitle() {
		return tretitle;
	}
	public void setTretitle(String tretitle) {
		this.tretitle = tretitle;
	}
	public String getTrecontent() {
		return trecontent;
	}
	public void setTrecontent(String trecontent) {
		this.trecontent = trecontent;
	}
	public String getTrecreatetime() {
		return trecreatetime;
	}
	public void setTrecreatetime(String trecreatetime) {
		this.trecreatetime = trecreatetime;
	}
	public Long getTreaccountid() {
		return treaccountid;
	}
	public void setTreaccountid(Long treaccountid) {
		this.treaccountid = treaccountid;
	}
	
	
	
}
