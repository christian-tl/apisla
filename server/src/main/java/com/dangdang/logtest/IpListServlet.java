package com.dangdang.logtest;

import java.io.IOException;
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

public class IpListServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1339780876689634462L;
	
	final Logger LOG = LoggerFactory.getLogger(IpListServlet.class);
	
	 public void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
		 doPost(req, resp);
		 
	 }
	 
	 public void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 String json = "{}";
		 try {
			 
			 SlaService service = SlaService.getInstance();
			 
			 String logs = request.getParameter("logs");
			 String apiId = request.getParameter("apiId"); 
			 String sysId = request.getParameter("sysId"); 
			 
			 if(apiId == null || apiId.trim().length() == 0){
				apiId = "1";
			 }
			 if(sysId == null || sysId.trim().length() == 0){
			  	sysId = "1";
			 }
			 
			 if(logs == null || logs.trim().length() == 0){
				 logs = "1";
			 }
			 
			 List<String> list = service.getIpList(sysId, apiId, logs);
			 
			 Gson gson = new Gson();  
			 Map<String,List<String>> map = new HashMap<>();
			 map.put("ipList", list);
			 json = gson.toJson(map);
			  
		 }catch (Exception e) {
			 LOG.error("",e);
			 e.printStackTrace();
		 }
		 //LOG.info(json);
		 response.setContentType("text/html; charset=utf-8"); 
		 response.getWriter().print(json);
	 }
	
}
