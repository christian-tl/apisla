package com.dangdang.logtest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class SlaCollectServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3696897619648211563L;
	
	final Logger LOG = LoggerFactory.getLogger(SlaCollectServlet.class);
	
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
		 doPost(req, resp);
		 
	}
	 
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse response)
	            throws ServletException, IOException {
		 try{
			 String json1 = req.getParameter("sla");
			 String json2 = req.getParameter("app_sla");
			 String ip = req.getRemoteAddr();
			 String real_ip = (String) req.getHeader("x-real-ip");
			 if(real_ip != null && real_ip.trim().length() > 0){
				 ip = real_ip;
			 }
			 
			 //LOG.info("json1 :"+json1); LOG.info("json2 :"+json2);
			 if(json1 == null){
				 json1 = "{}";
			 }
			 if(json2 == null){
				 json2 = "{}";
			 }
			 Gson gson = new Gson();  
			 HashMap<String ,Object> map1 = gson.fromJson(json1, HashMap.class);
			 HashMap<String ,Object> map2 = gson.fromJson(json2, HashMap.class);
			
			 List<Map<String,Object>> list1 = (List<Map<String,Object>> ) map1.get("result");
			 List<Map<String,Object>> list2 = (List<Map<String,Object>> ) map2.get("result");
			
			
			 SlaService service = SlaService.getInstance();
			 //String timestamp = (String) map1.get("timestamp");
			 String timestamp = service.getTime(-1);
			 
			 if(list1 != null){
				//LOG.info("["+ip+"] container sla data ("+list1.size()+")");
				for(Map<String,Object> map : list1){
					List<Map<String,String>> list = (List<Map<String, String>>) map.get("apisla");
					//LOG.info("=========container api size : "+list.size());
					service.saveSla(list,ip,"container",timestamp);
				}	
			 }
			 if(list2 != null){
				//LOG.info("["+ip+"] app sla data ("+list2.size()+")");
				for(Map<String,Object> app : list2){
					List<Map<String,String>> list = (List<Map<String, String>>) app.get("apisla");
					//LOG.info("=========app api size : "+list.size());
					service.saveSla(list,ip,"app",timestamp);
				}
			 }
			  
		 }catch (Exception e) {
			 LOG.error("",e);
			 e.printStackTrace();
		 }
		 response.getWriter().print("ok");
	 }
	
}
