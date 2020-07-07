package application.model;

import java.sql.ResultSet;

public interface ManagerStorage {
    ResultSet readingData(String textQuery) throws Exception;
    void initializePackage(String textQuery) throws Exception;
    void addValueInPackage(int position, int value) throws Exception;
    void executePackage() throws Exception;
    void modificationData(String textQuery) throws Exception;
}
