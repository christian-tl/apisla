package com.dangdang.logtest;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlaJob implements Job{

	Logger LOG = LoggerFactory.getLogger(SlaJob.class);
	public void execute(JobExecutionContext context)throws JobExecutionException {
		SlaService service = SlaService.getInstance();
		String timestamp = service.getTime(-60*72);
		service.deleteSlaData("container", timestamp);
		service.deleteSlaData("app", timestamp);
		LOG.info("SlaJob to delete sla data before : "+timestamp);
	}

}
