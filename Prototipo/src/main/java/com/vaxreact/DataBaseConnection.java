package com.vaxreact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "";
        String databaseUser = "";
        String databasePassword = "";
        String url = "jdbc:postgresql://localhost:5432/" + databaseName;

        try {
            Class.forName("org.postgresql.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

        public Connection closeConnection() {
            try{
                databaseLink.close();
            } catch (SQLException sqlE){
                System.out.println("Error: " + sqlE.getMessage());
            }
            return databaseLink;
    }
}
