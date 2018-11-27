package com.dangdang.logtest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManageApiServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final Logger LOG = LoggerFactory.getLogger(ManageApiServlet.class);
	
	 public void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
		 doPost(req, resp);
		 
	 }
	 
	 public void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 String json = "{'code':200}";
		 try {
			 
			 SlaService service = SlaService.getInstance();
			 
			 String apiUrl = request.getParameter("apiurl");
			 String apiName = request.getParameter("apiname");
			 String apiId = request.getParameter("apiId"); 
			 String sysId = request.getParameter("sysId"); 
			 String category = request.getParameter("category"); 
			 String logs = request.getParameter("logs"); 
			 String statType = request.getParameter("stattype");
			 
			 if(logs == null || logs.trim().length() == 0){
				 logs = "3";
			 }
			 if(statType == null || statType.trim().length() == 0){
				 statType = "provider";
			 }
			 ApiBean bean = new ApiBean();
			 bean.setApiUrl(apiUrl);
			 bean.setApiName(apiName);
			 bean.setApiId(apiId);
			 bean.setSysId(sysId);
			 bean.setCategory(category);
			 bean.setLogs(logs);
			 
			 service.saveApi(bean);
			  
		 }catch (Exception e) {
			 LOG.error("",e);
			 json = "{'code':500}";
			 e.printStackTrace();
		 }
		 //LOG.info(json);
		 response.setContentType("text/html; charset=utf-8"); 
		 response.getWriter().print(json);
	 }
	
}
