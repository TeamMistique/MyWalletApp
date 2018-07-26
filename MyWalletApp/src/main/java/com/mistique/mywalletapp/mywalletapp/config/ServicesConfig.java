package com.mistique.mywalletapp.mywalletapp.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {
    private static SessionFactory sessionFactory;

    static{
        sessionFactory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    @Bean
    public SessionFactory sessionFactory(){
        return sessionFactory;
    }
}
