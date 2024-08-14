package com.test.replicationtest.global.data;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(Source)")
    public void setSourceDataSource() {
        DataSourceContextHolder.setDataSourceType("source");
    }

    @Before("@annotation(Replica)")
    public void setReplicaDataSource() {
        DataSourceContextHolder.setDataSourceType("replica");
    }
}
