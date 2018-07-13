package com.mytest.demo;

import com.mytest.demo.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EmbeddedDatabaseType.java<br>
 * DataSourceConfiguration.java<br>
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private CountryService countryService;

    public static void main(String[] args) {
        logger.info("<<<<< Start to run Application >>>>>");
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("<<<<< Start to run CommandLineRunner >>>>>");
        countryService.init();
        countryService.find();
        System.exit(0);
    }

    // @Bean
    // public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    // return args -> {
    // String[] beanNames = ctx.getBeanDefinitionNames();
    // Arrays.sort(beanNames);
    // for (String beanName : beanNames) {
    // System.out.println(beanName);
    // }
    // };
    // }
}
