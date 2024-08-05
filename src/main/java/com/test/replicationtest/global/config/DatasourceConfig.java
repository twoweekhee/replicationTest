package com.test.replicationtest.global.config;

import com.test.replicationtest.global.RoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;

@Slf4j
@Configuration
public class DatasourceConfig {

    private static final String SOURCE_SERVER = "source";
    private static final String REPLICA_SERVER = "replica";

    @Bean
    @Qualifier(SOURCE_SERVER)
    @ConfigurationProperties("spring.datasource.source")
    public DataSource masterDataSource() {
        log.info("source register");
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier(REPLICA_SERVER)
    @ConfigurationProperties("spring.datasource.replica")
    public DataSource replicaDataSource() {
        log.info("replica register");
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource(@Qualifier(SOURCE_SERVER) DataSource masterDataSource,
                                        @Qualifier(REPLICA_SERVER) DataSource slaveDataSource) {

        RoutingDataSource routingDataSource = new RoutingDataSource();

        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(SOURCE_SERVER, masterDataSource);
        dataSourceMap.put(REPLICA_SERVER, slaveDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSource determinedDataSource = routingDataSource(masterDataSource(), replicaDataSource());
        return new LazyConnectionDataSourceProxy(determinedDataSource);
    }
}
