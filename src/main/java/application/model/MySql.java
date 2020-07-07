package application.model;

import java.sql.*;

class MySql implements ManagerStorage {
    private PreparedStatement _preparedStatement;
    private Statement _statement;

    private final Connection _connection;

    public MySql(Connection connection) {
        this._connection = connection;
    }

    public ResultSet readingData(String query) throws Exception {
        _statement = _connection.createStatement();
        return _statement.executeQuery(query);
    }

    public void initializePackage(String textQuery) throws Exception {
        _preparedStatement = _connection.prepareStatement(textQuery);
    }

    public void addValueInPackage(int position, int value) throws Exception {
        _preparedStatement.setInt(position, value);
        _preparedStatement.addBatch();
    }

    public void executePackage() throws Exception {
        _preparedStatement.executeBatch();
        _preparedStatement.getConnection().commit();
    }

    public void modificationData(String textQuery) throws Exception {
        _statement = _connection.createStatement();
        _statement.execute(textQuery);
        commit();
    }

    private void commit() throws Exception {
        _statement.getConnection().commit();
    }
}
