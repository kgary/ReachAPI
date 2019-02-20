package edu.asu.heal.core.api.resources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@WebListener
public class SchedulerMain implements ServletContextListener {

    private ScheduledExecutorService service;
    private final static int fONE_DAY = 1;
    private final static int fTWELVE_AM = 12;
    private final static int fTHIRTY_MINUTES = 30;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new HourlyScheduledTask(), 0, 1, TimeUnit.HOURS);
        service.scheduleAtFixedRate(new MidnightScheduledTask(), getTomorrowMorning4am().getTime(), 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        service.shutdownNow();
    }

    private static Date getTomorrowMorning4am() {
        Calendar tomorrow = new GregorianCalendar();
        tomorrow.add(Calendar.DATE, fONE_DAY);
        Calendar result = new GregorianCalendar(tomorrow.get(Calendar.YEAR),
                tomorrow.get(Calendar.MONTH), tomorrow.get(Calendar.DATE), fTWELVE_AM,
                fTHIRTY_MINUTES);
        return result.getTime();
    }
}
