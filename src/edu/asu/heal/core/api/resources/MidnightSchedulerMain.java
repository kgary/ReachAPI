package edu.asu.heal.core.api.resources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class MidnightSchedulerMain implements ServletContextListener {

    private ScheduledExecutorService service;
    private final static long fONCE_PER_DAY = 1000 * 60 * 60 * 24;

    //Set the initial delay. Ain't sure about that
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new MidnightScheduledTask(), 0, fONCE_PER_DAY, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        service.shutdownNow();
    }
}
