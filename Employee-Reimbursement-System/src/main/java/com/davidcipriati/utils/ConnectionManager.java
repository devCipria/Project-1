//package com.davidcipriati.utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class ConnectionManager {
//    private static final String URL = "jdbc:postgresql://mypostgresdb.ctpoxfycgbg6.us-east-1.rds.amazonaws.com:5432/ers";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "pgdav9280";
//    private static Connection conn;
//
//    // Source --> https://www.postgresql.org/docs/7.4/jdbc-use.html
//    public static Connection getConnection() {
//        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // add logging
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            // add logging
//        }
//        return conn;
//    }
//
//}
