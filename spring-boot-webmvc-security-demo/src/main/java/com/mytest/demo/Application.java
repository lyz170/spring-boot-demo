package com.mytest.demo;

import com.mytest.demo.service.InitDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private InitDataService initDataService;

    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("<<<<< Start to run Application >>>>>");
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("<<<<< Start to init data >>>>>");
        initDataService.init();
        initDataService.find();
        logger.info("<<<<< Init data completed >>>>>");
    }
}