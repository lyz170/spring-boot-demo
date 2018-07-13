package com.mytest.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


public class BeanConfiguration {

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "db1EntityManagerFactory",
            transactionManagerRef = "db1TransactionManager", basePackages = "com.mytest.demo.repository.db1")
    public static class DB1Configuration {

        @Autowired
        private JpaProperties jpaProperties;

        @Bean
        @Primary
        @ConfigurationProperties("spring.datasource.db1")
        public DataSourceProperties db1DataSourceProperties() {
            return new DataSourceProperties();
        }

        @Bean
        @Primary
        public DataSource db1DataSource(DataSourceProperties dataSourceProperties) {
            return dataSourceProperties.initializeDataSourceBuilder().build();
        }

        @Bean
        @Primary
        public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
                EntityManagerFactoryBuilder builder, DataSource dataSource) {
            return builder.dataSource(dataSource)
                    // .properties(jpaProperties.getProperties())
                    .properties(jpaProperties.getHibernateProperties(new HibernateSettings())) // IMPORTANT!!! diff from 1.5
                    .packages("com.mytest.demo.entity.db1")
                    .persistenceUnit("db1")
                    .build();
        }

        @Bean
        @Primary
        public PlatformTransactionManager db1TransactionManager(EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }

        @Bean
        @Primary
        public JdbcTemplate db1JdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "db2EntityManagerFactory",
            transactionManagerRef = "db2TransactionManager", basePackages = "com.mytest.demo.repository.db2")
    public static class DB2Configuration {

        @Autowired
        private JpaProperties jpaProperties;

        @Bean
        @ConfigurationProperties("spring.datasource.db2")
        public DataSourceProperties db2DataSourceProperties() {
            return new DataSourceProperties();
        }

        @Bean(destroyMethod = "") // dbcp2 datasource is closed twice(JMX server/springboot) when application closed.
        public DataSource db2DataSource(
                @Qualifier("db2DataSourceProperties") DataSourceProperties dataSourceProperties) {
            return dataSourceProperties.initializeDataSourceBuilder().build();
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
                EntityManagerFactoryBuilder builder, @Qualifier("db2DataSource") DataSource dataSource) {
            return builder.dataSource(dataSource)
                    // .properties(jpaProperties.getProperties())
                    .properties(jpaProperties.getHibernateProperties(new HibernateSettings())) // IMPORTANT!!! diff from 1.5
                    .packages("com.mytest.demo.entity.db2")
                    .persistenceUnit("db2")
                    .build();
        }

        @Bean
        public PlatformTransactionManager db2TransactionManager(
                @Qualifier("db2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }

        @Bean
        public JdbcTemplate db2JdbcTemplate(@Qualifier("db2DataSource") DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
}
