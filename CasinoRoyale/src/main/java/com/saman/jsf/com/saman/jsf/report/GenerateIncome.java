package com.saman.jsf.com.saman.jsf.report;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by Saman on 2015-06-26.
 */


public class GenerateIncome implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        Timer timer = new Timer();
        Date time =new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DATE,1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
       calendar.set(Calendar.SECOND, 3);
         time = calendar.getTime();
        Timer timer3 = new Timer();
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(Calendar.HOUR_OF_DAY, 0);
        calendar3.set(Calendar.MINUTE, 0);
        calendar3.set(Calendar.SECOND, 5);
        Date time3 = calendar3.getTime();
        Timer timer2 = new Timer();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 55);
        Date time2 = calendar2.getTime();
        timer.schedule(new RepeatInsert(),time);
        timer2.schedule(new RepeatUpdate(),time2);
        timer3.schedule(new RepeatUpdateWaiting(),time3);
    }
    @Override
     public void contextDestroyed(ServletContextEvent event) {
        RepeatInsert ri = new RepeatInsert();
        RepeatUpdate ru = new RepeatUpdate();
        RepeatUpdateWaiting ruw = new RepeatUpdateWaiting();
            ri.scheduler.shutdownNow();
        ru.scheduler.shutdownNow();
        ruw.scheduler.shutdownNow();
        }

}

