package com.chen.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


public class Controller extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(Controller.class);
	
	public String getNowtime(){
		return DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
	}
	
	public String getNowDate(){
		return DateTime.now().toString("yyyy-MM-dd HH:mm");
	}
	
	public String getIp(ServletRequest request){
		return request.getRemoteAddr();
	}
	
	public void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		StringBuffer webpath = new StringBuffer("/WEB-INF/views/");
		LoggerFactory.getLogger(Controller.class).debug("{}{}",webpath,path);
		request.getRequestDispatcher(webpath.append(path).toString()).forward(request, response);
	}
	
	public Object[] builderWhere(HttpServletRequest request) {
        StringBuffer where = new StringBuffer(); //where 语句
        List<Object> paramList = new ArrayList<Object>(); //?参数的值

        Enumeration<String> parameterNames  = request.getParameterNames(); //获取所有请求的参数名称(key)
        while (parameterNames.hasMoreElements()) {
            String param = parameterNames.nextElement(); //获取当前请求参数的名字 &q=1
            String svalue = request.getParameter(param); //获取对应参数的值
            String value = null;
			try {
				value = new String(svalue.getBytes("ISO8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            request.setAttribute(param,value);
            //q_eq_name=java  param:q_eq_name   value:java
            if(param.startsWith("q_") && StringUtils.isNotEmpty(value)) {
                String[] temp = param.split("_",3);
                if(temp.length != 3) {
                    throw new RuntimeException("查询参数构建错误");
                } else {
                    String columnName = temp[2]; //字段名称 name
                    String condition = temp[1]; //查询条件 eq
                    ////////////////////
                    //////////////
                    if("like".equalsIgnoreCase(condition)) {
                        value = "%"+value+"%"; // %java%
                        logger.info("{}输出结果", value);
                        // and name like ?
                        where.append(" and ").append(columnName).append(" like ?");
                    } else if("eq".equalsIgnoreCase(condition)) {
                        // and name = ?
                        where.append(" and ").append(columnName).append(" = ?");
                    } else if("gt".equalsIgnoreCase(condition)) {
                        // and name > ?
                        where.append(" and ").append(columnName).append(" > ?");
                    } else if("lt".equalsIgnoreCase(condition)) {
                        where.append(" and ").append(columnName).append(" < ?");
                    } else if("ge".equalsIgnoreCase(condition)) {
                        where.append(" and ").append(columnName).append(" >= ?");
                    } else if("le".equalsIgnoreCase(condition)) {
                        where.append(" and ").append(columnName).append(" <= ?");
                    } else if("ne".equalsIgnoreCase(condition)) {
                        where.append(" and ").append(columnName).append(" != ?");
                    } else {
                        continue;
                    }

                    paramList.add(value);
                }
            }
        }

        String sql = where.toString();
       /* if(sql.startsWith(" and ")) {
            sql = sql.substring(4);
            sql = "where" + sql;
        }
*/
        if(sql.startsWith(" and ")){
        	sql = " where 1 = 1 " + sql;
        }else if(!sql.startsWith(" where ")){
        	sql = " where 1 = 1 ";
        }
        
        logger.debug(sql);

        Object[] array = new Object[2];
        array[0] = sql;
        array[1] = paramList;
        /*
        List<Object>s = new ArrayList<Object>();
        List<List<Object>> list = new ArrayList<List<Object>>();
        s.add(sql);
        list.add(s);
        list.add(paramList);*/
        return array;
    }
	
	public void renderJson(HttpServletResponse response, Map<String, Object> map){
		response.setContentType("application/json; charset=UTF-8"); 
		Gson gson = new Gson();
		String json =  gson.toJson(map);
		print(json, response);
		
	}
	
	
	public void renderJson(HttpServletResponse response, List<Object> list){
		response.setContentType("application/json; charset=UTF-8"); 
		Gson gson = new Gson();
		String json =  gson.toJson(list);
		print(json, response);
		
	}

	public void print(String json, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
			logger.debug("json");
		} catch (IOException e) {
			logger.error("printWriter error{}", e);
			e.printStackTrace();
		}
		
		
	}
	
	
}
