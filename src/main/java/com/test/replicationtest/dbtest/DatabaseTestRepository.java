package com.test.replicationtest.dbtest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseTestRepository extends JpaRepository<DatabaseTest, Long> {

    public DatabaseTest findByName(String name);
}
