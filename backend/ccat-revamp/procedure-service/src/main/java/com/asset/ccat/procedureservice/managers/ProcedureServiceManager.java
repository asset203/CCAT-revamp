package com.asset.ccat.procedureservice.managers;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProcedureServiceManager {

    private final PGDatasourceManager pgDatasourceManager;
    private final FlexShareDatasourceManager flexShareDatasourceManager;

    public static HikariDataSource PG_DATASOURCE;
    public static HikariDataSource FLEX_SHARE_DATASOURCE;

    public ProcedureServiceManager(PGDatasourceManager pgDatasourceManager, FlexShareDatasourceManager flexShareDatasourceManager) {
        this.pgDatasourceManager = pgDatasourceManager;
        this.flexShareDatasourceManager = flexShareDatasourceManager;
    }

    @EventListener
    public void startUpEvent(ApplicationReadyEvent event){
        PG_DATASOURCE = pgDatasourceManager.register();
        FLEX_SHARE_DATASOURCE = flexShareDatasourceManager.register();
    }
}
