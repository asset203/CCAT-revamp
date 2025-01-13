package com.asset.ccat.gateway.tasks;

import com.asset.ccat.gateway.logger.CCATLogger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class
ScheduledTask {

    private static AtomicInteger tpsCounter = new AtomicInteger(0);
    private static AtomicInteger dailyCounter = new AtomicInteger(0);

    public void incrementTPSCount() {
        tpsCounter.incrementAndGet();
        dailyCounter.incrementAndGet();
    }

    public void decrementCount() {
        tpsCounter.decrementAndGet();
    }

    @Scheduled(fixedRate = 1000)
    public void resetTpsCount() {
        if (tpsCounter.get() != 0) {
            CCATLogger.TPS_LOGGER.info("TPS count is : " + tpsCounter.get());
            tpsCounter = new AtomicInteger(0);
        }
    }

    @Scheduled(fixedRate = 60000)
    public void printDailyCount() {
        CCATLogger.TPS_LOGGER.info("Daily count is : " + dailyCounter.get());
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Africa/Cairo")
    public void resetCountAtMidnight() {
        CCATLogger.TPS_LOGGER.info("Daily count at midnight is : " + dailyCounter.get());
        dailyCounter = new AtomicInteger(0);
    }
}
