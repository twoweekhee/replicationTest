package com.test.replicationtest.global;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        String lookupKey = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "replica" : "source";
        log.info("Current DataSource is {}", lookupKey);
        return lookupKey;
    }

}
