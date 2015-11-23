package com.chen.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.chen.dao.CustomerDAO;
import com.chen.entity.Account;
import com.chen.entity.Customer;
import com.chen.util.Pager;
import com.chen.util.PinyinUtil;

public class CustomerService {

	private CustomerDAO cd;
	private TrendsService ts;
	
	public CustomerService(){
		cd = new CustomerDAO();
		ts = new TrendsService();
	}
	
	public long addCustomer(Customer cust, String companyName){
		Customer cus = new Customer();
		//如果是个人
		if(cust.getFlags()){
			cus = cd.findByName(companyName);
			if(cus == null){
				Customer com = new Customer();
				copy(com, cust, companyName);
				com.setNamepinyin(PinyinUtil.getPinyin(companyName));
				long cid = cd.addCompany(com);
				if(cid >= 0){
					cust.setCompanyid(cid);
				}else{
					throw new RuntimeException("cid 查询错误");
				}
			}else{
				//如果查询成功，即公司存在
				cust.setCompanyid(cus.getId());
			}
			cust.setNamepinyin(PinyinUtil.getPinyin(cust.getCustname()));
			cust.setFlags(true);
			cd.addCustomer(cust);
			ts.saveTrends(cust.getAccountid(), cust.getCustname(), cust.getContent(), "添加用户", cust.getCreatetime());
		}else{
			//如果是公司，本函数为存储用户，公司调用其他函数 
			addCompany(cust);
		}
		
		return 0;
	}
	
	public long addCompany(Customer cust){
		Customer cus = new Customer();
		//如果是公司
		cus.setFlags(false);//公司为false
		cust.setFlags(false);
		cus = cd.findByName(cust.getCustname());
		cust.setNamepinyin(PinyinUtil.getPinyin(cust.getCustname()));
		if(cus == null){
			ts.saveTrends(cust.getAccountid(), cust.getCustname(), cust.getContent(), "添加公司", cust.getCreatetime());
			return cd.addCompany(cust);
		}else{
			ts.saveTrends(cust.getAccountid(), cust.getCustname(), cust.getContent(), "更新操作", cust.getCreatetime());
			return cd.updateCompany(cust, cus.getId());
		}
		
	}
	
	/*
	 * 拷贝出未在数据库中出现的公司
	 */
	private void copy(Customer com, Customer cust, String company){
		com.setCustname(company);
		com.setTel(cust.getTel());
		com.setViews(true);
		com.setAccountid(cust.getAccountid());
		com.setFlags(false);//公司为false
	}

	@SuppressWarnings("unchecked")
	public Pager<Customer> findByPage(Account account, int pageNo, Object[] array) {
		String where = (String) array[0];
		if(StringUtils.isEmpty(where.trim())) {
            where = " where accountid = ? or views = 0 ";
        } else {
            where += " and (accountid = ? or views = 0) ";
        }
	
        List<Object> params = (List<Object>) array[1];
        params.add(account.getId());
        Pager<Customer> pager = new Pager<Customer>(pageNo, cd.count(where,params).intValue(),10);
        params.add(pager.getStart());
        params.add(pager.getPageSize());
        List<Customer> list = cd.find(where, params);
        pager.setItems(list);
        return pager;
	}

	public int updateContent(String content, Long id) {
		String context = cd.queryContent(id);
		StringBuffer temp = new StringBuffer(content);
		temp.append(context);
		//ts.saveTrends(cust.getAccountid(), "", cust.getContent(), "添加内容", cust.getCreatetime());
		return cd.updateContent(temp.toString(), id);
	}

	public Customer findById(Long id) {
		return cd.findById(id);
		
	}

	public int delCust(Long id) {
		return cd.delCust(id);
	}

	public long editCompany(Customer com) {
		//Customer cus = new Customer();
		//如果是公司
		com.setFlags(false);//公司为false
		//cus = cd.findByName(com.getCustname());
		//if(cus == null){
			//return cd.addCompany(com);
		//}else{
		ts.saveTrends(com.getAccountid(), com.getCustname(), com.getContent(), "更新公司", com.getCreatetime());
			return cd.updateCompany(com, com.getId());
		//}
	}

	public int editCustomer(Customer cust, String company) {
		Customer cus = new Customer();
		//如果是个人
		if(cust.getFlags()){
			cus = cd.findByName(company);
			if(cus == null){
				Customer com = new Customer();
				copy(com, cust, company);
				long cid = cd.addCompany(com);
				if(cid >= 0){
					cust.setCompanyid(cid);
				}else{
					throw new RuntimeException("cid 查询错误");
				}
			}else{
				//如果查询成功，即公司存在
				cust.setCompanyid(cus.getId());
			}
			cust.setFlags(true);
			ts.saveTrends(cust.getAccountid(), cust.getCustname(), cust.getContent(), "修改用户", cust.getCreatetime());
			return cd.editCustomer(cust);
		}else{
			//如果是公司，本函数为存储用户，公司调用其他函数 
			if(addCompany(cust)>0){
				return 1;
			}else{
				return -1;
			}
		}
	}

	public List<Customer> findByName(String name, Account account) {
		return cd.findByName(name, account);
	}

	
}
