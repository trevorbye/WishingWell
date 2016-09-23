package com.webartifact.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.webartifact.dao")
@PropertySource("application.properties")
public class DataConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(env.getProperty("well.entity.package"));
        factory.setDataSource(dataSource());
        factory.setJpaProperties(getHibernateProperties());
        return factory;
    }

    @Bean
    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.platform"));
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();

        ds.setDriverClassName(env.getProperty("well.db.driver"));
        ds.setUrl(env.getProperty("well.db.url"));
        ds.setUsername(env.getProperty("well.db.user"));
        ds.setPassword(env.getProperty("well.db.pass"));

        return ds;
    }
}
