package com.example.demo.Data;

import com.example.demo.Data.Auto.Auto;
import com.example.demo.Data.User.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class MySessionFactory {
    private final SessionFactory sessionFactory;
    private static MySessionFactory instance;

    private MySessionFactory() {
        Properties props = new Properties();
        props.put(Environment.DRIVER, "org.postgresql.Driver");
        props.put(Environment.URL, "jdbc:postgresql://localhost:5432/mydb");
        props.put(Environment.USER, "ihor");
        props.put(Environment.PASS, "docker");
        props.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        props.put(Environment.HBM2DDL_AUTO, "update");
        props.put(Environment.SHOW_SQL, "true");
        sessionFactory = new Configuration()
                .setProperties(props)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Auto.class)
                .buildSessionFactory();
    }

    public static SessionFactory getInstance() {
        if (instance == null) {
            instance = new MySessionFactory();
        }
        return instance.sessionFactory;
    }
}