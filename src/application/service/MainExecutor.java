package application.service;

import application.Main;
import application.dao.Entries;
import application.dao.Entry;
import application.dao.NewEntries;
import application.dao.NewEntry;
import application.other.Constants;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainExecutor {
    private static Connector connector;
    private static ManagerDB managerDB;
    private int countRecords;
    private final int MAX_SIZE_BATCH = 10000;

    public void setCountRecords(int cr) {
        this.countRecords = cr;
    }

    public Connector getConnector() {
        return connector;
    }

    public boolean checkingTable() {
        boolean status = true;
        try {
            managerDB.setStatement(connector.getConnection().createStatement());
            ResultSet rs = managerDB.executeWithResult("SELECT COUNT(field) FROM test");
            rs.next();
            int countRecords = rs.getInt(1);
            if (countRecords == 0) {
                status = true;
            } else {
                status = false;
            }
        } catch (SQLException e) {
            System.out.println("Error checking database: " + e.getMessage());
            Main.commonActionWithException();
        }
        return status;
    }

    public void clearingTable() {
        try {
            managerDB.setStatement(connector.getConnection().createStatement());
            managerDB.execute("DELETE FROM test");
            managerDB.getStatement().getConnection().commit();
        } catch (SQLException e) {
            System.out.println("Error clearing table: " + e.getMessage());
            Main.commonActionWithException();
        }

    }

    public void insertingData() {
        if (countRecords > MAX_SIZE_BATCH) {
            int countBatch = countRecords / MAX_SIZE_BATCH;
            int excess = countRecords % MAX_SIZE_BATCH;
            int begin = 0;
            int end = MAX_SIZE_BATCH;
            for (int i = 1;i <= countBatch;i++) {
                if (i == countBatch) {
                    end += excess;
                }
                insert(begin, end);
                begin = begin + MAX_SIZE_BATCH;
                end = end + MAX_SIZE_BATCH;
            }
        }
        else {
            insert(0, countRecords);
        }
    }

    public void creatingXmlFile() {
        try {
            managerDB.setStatement(connector.getConnection().createStatement());
            ResultSet resultSet = managerDB.executeWithResult("SELECT field FROM TEST");
            MasterXML masterXML = new MasterXML(Constants.WORK_DIR_ORIGINAL_FILE);
            Entries entries = new Entries();
            while (resultSet.next()) {
                entries.getEntry().add(Entry.getEntry(resultSet.getInt(1)));
            }
            masterXML.saveDocument(masterXML.marshaller(Entries.class), entries);
        } catch (Exception e) {
            System.out.println("Error creating file: " + e.getMessage());
            Main.commonActionWithException();
        }
    }

    public void convertingXmlFile() {
        createFile(Constants.WORK_DIR_XSLT_FILE);
        MasterXML.xmlTransform(Constants.WORK_DIR_ORIGINAL_FILE, Constants.WORK_DIR_XSLT_FILE, Constants.WORK_DIR_RESULT_FILE);
    }

    public void calculatingAmount() {
        try {
            long sum = 0;
            MasterXML xml = new MasterXML(Constants.WORK_DIR_RESULT_FILE);
            NewEntries entries = (NewEntries) xml.unmarshaller(NewEntries.class);
            for (NewEntry entry : entries.getEntry()) {
                sum = sum + entry.getField();
            }
            System.out.println("Completed: Calculating the amount");
            System.out.println("Arithmetic sum of all values: " + sum);
        } catch (Exception e) {
            System.out.println("Error calculating amount: " + e.getMessage());
            Main.commonActionWithException();
        }

    }

    public void start() {
        setManagerDB();
        connector.setConnection();
    }

    private void setManagerDB() {
        managerDB = new ManagerDB();
    }

    public void setConnector() {
        connector = new Connector();
    }

    private void createFile(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                FileOutputStream fos = new FileOutputStream(path);
                byte[] bs = Constants.TEXT_FILE_XSLT.getBytes();
                fos.write(bs, 0, bs.length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insert(int begin, int end) {
        try {
            managerDB.setPreparedStatement(connector.getConnection().prepareStatement("INSERT INTO test ( field ) VALUES ( ? )"));
            for (int i = begin; i < end; i++) {
                managerDB.setInputParameter(1, i + 1);
                managerDB.addBatch();
            }
            managerDB.executeBatch();
            managerDB.getPreparedStatement().getConnection().commit();
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
            Main.commonActionWithException();
        }
    }

}
