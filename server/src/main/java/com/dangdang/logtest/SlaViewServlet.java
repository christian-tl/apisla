package com.dangdang.logtest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class SlaViewServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1339780876689634462L;
	
	final Logger LOG = LoggerFactory.getLogger(SlaViewServlet.class);
	
	 public void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
		 doPost(req, resp);
		 
	 }
	 
	 public void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 String json = "{}";
		 try {
			 
			 SlaService service = SlaService.getInstance();
			 
			 String type = request.getParameter("type");
			 String statType = request.getParameter("stattype");
			 String apiId = request.getParameter("apiId"); 
			 String sysId = request.getParameter("sysId"); 
			 String start = request.getParameter("start"); 
			 String end = request.getParameter("end"); 
			 String ip = request.getParameter("ip"); 
			 
			 if(apiId == null || apiId.trim().length() == 0){
				apiId = "1";
			 }
			 if(sysId == null || sysId.trim().length() == 0){
			  	sysId = "1";
			 }
			 
			 if(type == null || type.trim().length() == 0){
				 type = "container";
			 }
			 
			 if(statType == null || statType.trim().length() == 0){
				 statType = "provider";
			 }
			 
			 if(ip == null || ip.trim().length() == 0){
				 ip = "0";
			 }
			 //LOG.info("start : "+start+" end :"+end);
			 if(start == null || start.trim().length() == 0){
				 start = service.getTime(-11);
			 }
			 if(end == null || end.trim().length() == 0){
				 end = service.getTime(-2);
			 }
			 JsonBean jsonBean = service.getSlaData(apiId, sysId , start, end, type,ip, statType);
			 
			 Gson gson = new Gson();  
			 json = gson.toJson(jsonBean);
			  
		 }catch (Exception e) {
			 LOG.error("",e);
			 e.printStackTrace();
		 }
		 //LOG.info(json);
		 response.setContentType("text/html; charset=utf-8"); 
		 response.getWriter().print(json);
	 }
	
}
