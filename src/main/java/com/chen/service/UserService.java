package com.chen.service;

import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import com.chen.dao.AccountDAO;
import com.chen.dao.DeptDAO;
import com.chen.entity.Account;
import com.chen.entity.Department;
import com.chen.util.Pager;

public class UserService {
	
	private AccountDAO ad;
	
	private static String DEFAULTPASS = "123456";
	
	public UserService(){
		ad = new AccountDAO();
	}
	
	public int resetPass(Long id){
		String pass = DigestUtils.md5Hex(DEFAULTPASS);
		if(id >= 0){
			return ad.resetPwd(id, pass);
		}
		return -1;
	}
	
	public Account login(String username, String password, String time, String ip){
		Account account = ad.findByUsername(username);
		if(account != null){
			if(account.getUserpass().equals(password)){
				account.setLastloginip(ip);
				account.setLastlogintime(time);
				recodeLogin(account);
				//new TrendsService().saveTrends(account.getId(), account.getUsername(), account.getUsername(), "µÇÂ½ÊÂ¼þ", TimeUtil.getNowTime());
				
				return account;
			}else {
				return null;
			}
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Pager<Account> findByPage(int pageNo, Object[] array) {
		
		String where = (String) array[0];
        List<Object> params = (List<Object>) array[1];
        Pager<Account> pager = new Pager<Account>(pageNo, ad.count(where,params).intValue(),10);
        
        params.add(pager.getStart());
        params.add(pager.getPageSize());
       
        List<Account> list = ad.find(where,params);
        pager.setItems(list);
        return pager;
	}
	
	public int recodeLogin(Account account){
		return ad.recode(account);
	}

	public List<Department> showDept() {
		return new DeptDAO().findAll();
	}

	public long register(Account account) {
		return ad.addAccount(account);
	}

	public boolean findByName(String username) {
		Account account = ad.findByUsername(username);
		if(account != null){
			return false;
		}else{
			return true;
		}
	}

	public int banUser(Long id) {
		return ad.banUser(id);
	}

	public int changePass(Long id, String newpass) {
		return ad.changePass(id, newpass);
	}

	public int activeUser(Long id) {
		return ad.activeUser(id);
	}

	public int delUser(Long id) {
		return ad.deleteUser(id);
	}

	public List<Account> findByDeptid(Long id) {
		return ad.findByDeptid(id);
	}

	


	
}
