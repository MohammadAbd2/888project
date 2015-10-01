package com.saman.jsf.com.saman.jsf.report;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Saman on 2015-06-28.
 */
public class RepeatUpdateWaiting extends TimerTask {


        ScheduledExecutorService scheduler;

        public void run(){
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new UpdateIncomeWaitingRecord(), 0, 5*60, TimeUnit.SECONDS);
        }
    }

