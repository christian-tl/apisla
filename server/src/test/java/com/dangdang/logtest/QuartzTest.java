package com.dangdang.logtest;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.quartz.CronScheduleBuilder.*;

class MyJob implements org.quartz.Job {
	Logger LOG = LoggerFactory.getLogger(MyJob.class);
    public MyJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("Hello World!  MyJob is executing.");
        LOG.info("SlaJob");
    }
}

public class QuartzTest {
	
	
//	@Test
	public void test1() throws InterruptedException{
		try {
       	 
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = newJob(SlaJob.class)
                .withIdentity("job1", "group1")
                .build();

            Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(cronSchedule("0/1 * * * * ?"))
                .build();

            scheduler.scheduleJob(job, trigger);
            scheduler.start();
            Thread.sleep(10*1000);
            //scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
	}
	
}
