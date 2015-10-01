package com.saman.jsf.com.saman.jsf.activetable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Saman on 2015-07-22.
 */


    public class GenerateActiveTable implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent event) {

            Timer timer = new Timer();
            Date time =new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
          /*  calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 10);*/
            time = calendar.getTime();

            timer.schedule(new UpdateATable(),time);

        }
        @Override
        public void contextDestroyed(ServletContextEvent event) {
            UpdateATable uat = new UpdateATable();
            uat.scheduler.shutdownNow();

        }

    }
 class UpdateATable extends TimerTask {
    ScheduledExecutorService scheduler;

    public void run(){
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new ActiveTable(), 0, 20, TimeUnit.SECONDS);
    }
}
