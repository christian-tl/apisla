package com.dangdang.logtest;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class StartupListener implements ServletContextListener {

    Logger LOG = LoggerFactory.getLogger(StartupListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("***** StartupListener start *****");

        delete();

       // backup();
    }

    private void delete() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = newJob(SlaJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(cronSchedule("0 0/10 * * * ?"))
                    .build();

            scheduler.scheduleJob(job, trigger);
            scheduler.start();
//            scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    private void backup() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = newJob(BackupJob.class)
                    .withIdentity("backupJob", "backupGroup")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("backupTrigger", "backupGroup")
                    .startNow()
                    .withSchedule(cronSchedule(BackupJob.getBackupCron()))
                    .build();

            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
