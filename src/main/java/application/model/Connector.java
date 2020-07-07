package application.model;

import java.sql.Connection;
import java.sql.DriverManager;


class Connector {

    private static Connection connection;

    public static Connection getConnection(String nameServer, String port, String nameDatabase, String user, String password) throws Exception {
        if (connection == null) {
            connection = DriverManager.getConnection(getUrl(nameServer, port, nameDatabase), user, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }

    private static String getUrl(String nameServer, String port, String nameDatabase) {
        return String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC", nameServer, port, nameDatabase);
    }
}
