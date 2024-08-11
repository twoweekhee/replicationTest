package com.test.replicationtest.global.data;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    public Object determineCurrentLookupKey() {
        log.info("current datasource : {}" , DataSourceContextHolder.getDataSourceType());
        return DataSourceContextHolder.getDataSourceType();
    }

}
