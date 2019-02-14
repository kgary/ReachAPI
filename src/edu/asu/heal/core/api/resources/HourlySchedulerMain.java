package edu.asu.heal.core.api.resources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class HourlySchedulerMain implements ServletContextListener {

    private ScheduledExecutorService service;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new HourlyScheduledTask(), 0, 1, TimeUnit.HOURS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        service.shutdownNow();
    }
}
