package com.mytest.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryService {

    private final static Logger logger = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private com.mytest.demo.repository.db1.CountryRepository db1CountryRepository;

    @Autowired
    private com.mytest.demo.repository.db2.CountryRepository db2CountryRepository;

    public void init() {
        initDB1();
        initDB2();
    }

    public void find() {
        findDB1();
        findDB2();
    }

    @Transactional(value = "db1TransactionManager")
    public void initDB1() {
        db1CountryRepository.save(new com.mytest.demo.entity.db1.Country("Russia", 1707D, "Moscow"));
        db1CountryRepository.save(new com.mytest.demo.entity.db1.Country("Canada", 997D, "Ottawa"));
        db1CountryRepository.flush();
        logger.info("***** DB1 init completed. *****");
    }

    @Transactional(value = "db2TransactionManager")
    public void initDB2() {
        db2CountryRepository.save(new com.mytest.demo.entity.db2.Country("China", 960D, "Beijing"));
        db2CountryRepository.save(new com.mytest.demo.entity.db2.Country("America", 937D, "Washington"));
        db2CountryRepository.flush();
        logger.info("***** DB2 init completed. *****");
    }

    @Transactional(value = "db1TransactionManager")
    public void findDB1() {
        logger.info("***** Find DB1 Country data: *****");
        db1CountryRepository.findAll().forEach(p -> logger.info(p.toString()));
    }

    @Transactional(value = "db2TransactionManager")
    public void findDB2() {
        logger.info("***** Find DB2 Country data: *****");
        db2CountryRepository.findAll().forEach(p -> logger.info(p.toString()));
    }
}