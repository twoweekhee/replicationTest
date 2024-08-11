package com.test.replicationtest.dbtest;

import com.test.replicationtest.global.data.Replica;
import com.test.replicationtest.global.data.RoutingDataSource;
import com.test.replicationtest.global.data.Source;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final DatabaseTestRepository databaseTestRepository;
    private final RoutingDataSource routingDataSource;

    @Source
    public String addData() {
       databaseTestRepository.save(new DatabaseTest("jooheee", 25));
        return (String) routingDataSource.determineCurrentLookupKey();
    }

    @Replica
    public String getData() {
        databaseTestRepository.findById(1L);
        return (String) routingDataSource.determineCurrentLookupKey();
    }

}
