package edu.asu.heal.core.api.resources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class SchedulerMain implements ServletContextListener {

    private ScheduledExecutorService service;

//    public static void main(String args[]) throws InterruptedException {
//
//        Timer time = new Timer(); // Instantiate Timer Object
//        HourlyScheduledTask st = new HourlyScheduledTask(); // Instantiate HourlyScheduledTask class
//        time.schedule(st, 0, 10000); // Create Repetitively task for every 1 secs
//
//        //for demo only.
//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.scheduleAtFixedRate(new HourlyScheduledTask(), 0, 10, TimeUnit.SECONDS);
//
//    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new HourlyScheduledTask(), 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        service.shutdownNow();
    }
}
