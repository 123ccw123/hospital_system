package com.ccw.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new
                Date());
        System.out.println("PrintWordsJob start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100));
    }

    public static void main(String[] args) throws SchedulerException {
        //创建job对象
        JobDetail detail = JobBuilder.newJob(MyJob.class).withIdentity("myJob","myGroup").build();
        //创建触发器
        /*SimpleTriggerImpl trigger = new SimpleTriggerImpl();
        trigger.setName("myTrigger");
        trigger.setStartTime(new Date());*/
        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
                .build();
        //创建调度器工厂
        StdSchedulerFactory factory = new StdSchedulerFactory();
        //创建调度器对象
        Scheduler scheduler = factory.getScheduler();
        //通过调度器绑定触发器和job对象
        scheduler.scheduleJob(detail,trigger);
        //开启调度器
        scheduler.start();
    }
}

