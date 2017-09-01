package application.service;

import application.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector {
    private String nameServer;
    private String port;
    private String nameDatabase;
    private String user;
    private String password;
    private Connection connection;

    public void setNameServer(String nS) {
        this.nameServer = nS;
    }

    public void setPort(String p) {
        this.port = p;
    }

    public void setNameDatabase(String nDB) {
        this.nameDatabase = nDB;
    }

    public void setUser(String u) {
        this.user = u;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection() {
        try {
            connection = DriverManager.getConnection(getUrl(), user, password);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Error checking database: " + e.getMessage());
            Main.commonActionWithException();
        }
    }

    public String getUrl() {
        String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false", nameServer, port, nameDatabase);
        return url;
    }

}
