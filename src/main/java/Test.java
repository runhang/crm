
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

import com.chen.dao.ScheduleDAO;
import com.chen.entity.Account;
import com.chen.entity.Schedule;
import com.chen.service.UserService;


public class Test {

	public static void main(String[] args) {
		String text = "123456";
		System.out.println(DigestUtils.md5Hex(text));
		StringBuffer str = new StringBuffer("11");
		System.out.println(str.toString());
		UserService us = new UserService();
		/*List<Account> list = us.findByDeptid((long) 23);
		if(list.isEmpty()){
			System.out.println(" list " + list);
		}else{
			System.out.println(" list is not null : " + list);
		}*/
		ScheduleDAO shd = new ScheduleDAO();
		Object[] stime = (Object[]) shd.findUnoverNodonetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss")).toArray();
		Map<String, List<Schedule>> maps = new HashMap<String ,List<Schedule>>();
		for(int i = 0; i < stime.length; i++){
			System.out.println("shijian: "+stime[i]);
			 List<Schedule> list = shd.findUnOverNodoneByTime((String)stime[i], 2L);
			 if(!list.isEmpty()){
				 maps.put((String)stime[i], list);
			 }
		
		}
	}
}
