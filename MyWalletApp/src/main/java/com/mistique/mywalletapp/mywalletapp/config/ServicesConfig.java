package com.mistique.mywalletapp.mywalletapp.config;

import com.mistique.mywalletapp.mywalletapp.data.GenericSqlRepository;
import com.mistique.mywalletapp.mywalletapp.models.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {
    private static SessionFactory sessionFactory;
    private static GenericSqlRepository<Type> typeRepository;
    private static GenericSqlRepository<Wallet> walletRepository;
    private static GenericSqlRepository<Transaction> transactionRepository;
    private static GenericSqlRepository<Category> categoryRepository;

    static {
        sessionFactory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Transaction.class)
                .addAnnotatedClass(Type.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Wallet.class)
                .buildSessionFactory();

        typeRepository = new GenericSqlRepository<>();
        walletRepository = new GenericSqlRepository<>();
        transactionRepository = new GenericSqlRepository<>();
        categoryRepository = new GenericSqlRepository<>();
    }

    @Bean
    public SessionFactory sessionFactory() {
        return sessionFactory;
    }

    @Bean
    public GenericSqlRepository<Type> typeRepository() {
        return typeRepository;
    }

    @Bean
    public GenericSqlRepository<Category> categoryRepository() {
        return categoryRepository;
    }

    @Bean
    public GenericSqlRepository<Transaction> transactionRepository() {
        return transactionRepository;
    }

    @Bean
    public GenericSqlRepository<Wallet> walletRepository() {
        return walletRepository;
    }
}
