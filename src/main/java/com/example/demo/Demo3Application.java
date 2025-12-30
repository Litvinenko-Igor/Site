package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.DriverManager;

@SpringBootApplication
public class Demo3Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo3Application.class, args);

    }
}


//public class HikariCPDataSource {
//
//    private static HikariConfig config = new HikariConfig();
//    private static HikariDataSource ds;
//
//    static {
//        config.setJdbcUrl("jdbc:h2:mem:test");
//        config.setUsername("user");
//        config.setPassword("password");
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//        ds = new HikariDataSource(config);
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return ds.getConnection();
//    }
//
//    private HikariCPDataSource(){}


//Properties properties = new Properties();
//properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
//properties.put(Environment.URL, "jdbc:mysql://localhost:3306/supershop") ;
//properties.put(Environment.USER, "root");
//properties.put(Environment.PASS, "password");
//
//SessionFactory sessionFactory = new Configuration()
//        .setProperties(properties)
//        .buildSessionFactory();