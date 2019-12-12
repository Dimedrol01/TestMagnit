package application.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManagerDB {
    private PreparedStatement preparedStatement;
    private Statement statement;


    public ResultSet executeWithResult(String query) {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void execute(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBatch() throws SQLException {
        preparedStatement.addBatch();
    }

    public void executeBatch() {
        try {
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void setInputParameter(int position, int value) throws SQLException {
        preparedStatement.setInt(position, value);
    }

    public void setStatement(Statement s) {
        this.statement = s;
    }

    public void setPreparedStatement(PreparedStatement ps) {
        this.preparedStatement = ps;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public Statement getStatement() {
        return statement;
    }
}
