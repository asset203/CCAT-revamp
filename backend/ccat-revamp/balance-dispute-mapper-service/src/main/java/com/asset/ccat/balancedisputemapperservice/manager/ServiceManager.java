package com.asset.ccat.balancedisputemapperservice.manager;


import com.asset.ccat.balancedisputemapperservice.factories.CustomThreadFactory;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.tasks.RefreshBalanceDisputeMapperCacheTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
public class ServiceManager {

  private final ScheduledThreadPoolExecutor poolTaskScheduler;
  private final RefreshBalanceDisputeMapperCacheTask refreshBalanceDisputeMapperCacheTask;

  public ServiceManager(
      RefreshBalanceDisputeMapperCacheTask refreshBalanceDisputeMapperCacheTask) {
    this.poolTaskScheduler = new ScheduledThreadPoolExecutor(1,
        new CustomThreadFactory("RefreshPool", "RefreshTask"));
    this.refreshBalanceDisputeMapperCacheTask = refreshBalanceDisputeMapperCacheTask;
  }

  private void init() {
    poolTaskScheduler.scheduleWithFixedDelay(refreshBalanceDisputeMapperCacheTask, 0, 30000,
        TimeUnit.MILLISECONDS);
  }

  @EventListener
  public void startupEvent(ContextRefreshedEvent event) {
    CCATLogger.DEBUG_LOGGER.debug("Starting BD-Mapper service");
    try {
      init();
    } catch (Exception ex) {
      CCATLogger.ERROR_LOGGER.error("Failed to start BD-Mapper service", ex);
    }
  }
}
