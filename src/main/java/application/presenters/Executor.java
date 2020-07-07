package application.presenters;

import application.model.FactoryLayerTwo;
import application.model.dao.Entries;
import application.model.dao.Entry;
import application.model.dao.NewEntries;
import application.model.dao.NewEntry;
import application.model.Constants;
import application.model.ManagerStorage;
import application.model.ManagerXML;
import application.views.MainView;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

class Executor implements MainPresenter {
    private final MainView _view;
    private ManagerStorage _managerStorage;
    private ManagerXML _managerXml;

    public Executor(MainView view) {
        this._view = view;
    }

    public void startExecute(String server, int port, String database, String user, String password, int countRecord) throws Exception {
        _view.printMessage(String.format("Start time: %s", getCurrentData()));
        Date begin = new Date();
        _managerStorage = FactoryLayerTwo.getManagerStorage(server, port, database, user, password);
        if (checkingTable() == 1) {
            _view.printMessage("Completed: Checking the table");
            clearingTable();
            _view.printMessage("Completed: Clearing the table");
        }
        insertingData(countRecord);
        _view.printMessage("Completed: Inserting the table");
        _managerXml = FactoryLayerTwo.getManagerXML();
        creatingXmlFile(getDataForCreatingFile());
        _view.printMessage("Completed: Creating an XML file");
        convertingXmlFile();
        _view.printMessage("Completed: Converting XML file");
        calculatingAmount();
        Date end = new Date();
        _view.printMessage(String.format("The time of work: %s", differenceInTime(begin, end)));
        _view.printMessage(String.format("Time of completion:: %s", getCurrentData()));
    }

    private String getCurrentData() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(now);
    }

    private int checkingTable() {
        try {
            ResultSet rs = _managerStorage.readingData("SELECT COUNT(field) FROM test");
            rs.next();
            int countRecords = rs.getInt(1);
            if (countRecords == 0) {
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            _view.printMessage(String.format("Error checking database: %s", e.getMessage()));
            _view.stopProgram();
            return -1;
        }
    }

    private void insertingData(int countRecords) {
        try {
            _managerStorage.initializePackage("INSERT INTO test ( field ) VALUES ( ? )");
            for (int i = 0; i < countRecords; i++) {
                _managerStorage.addValueInPackage(1, i + 1);
            }
            _managerStorage.executePackage();
        } catch (Exception e) {
            _view.printMessage(String.format("Error inserting data: %s", e.getMessage()));
            _view.stopProgram();
        }
    }

    private void clearingTable() {
        try {
            _managerStorage.modificationData("DELETE FROM test");
        } catch (Exception e) {
            _view.printMessage(String.format("Error deleting data: %s", e.getMessage()));
            _view.stopProgram();
        }
    }

    private void creatingXmlFile(ResultSet data) {
        try {
            Entries entries = new Entries();
            while (data.next()) {
                entries.getEntry().add(Entry.getEntry(data.getInt(1)));
            }
            _managerXml.saveDocument(entries, Entries.class);
        } catch (Exception e) {
            _view.printMessage(String.format("Error creating file: %s", e.getMessage()));
            _view.stopProgram();
        }
    }

    private void convertingXmlFile() {
        try {
            createFileXLST(Constants.WORK_DIR_XSLT_FILE);
            _managerXml.makeTransformXml(Constants.WORK_DIR_ORIGINAL_FILE, Constants.WORK_DIR_XSLT_FILE, Constants.WORK_DIR_RESULT_FILE);
        } catch (Exception e) {
            _view.printMessage(String.format("Error converting file: %s", e.getMessage()));
            _view.stopProgram();
        }
    }

    private void calculatingAmount() {
        try {
            long sum = 0;
            NewEntries entries = (NewEntries) _managerXml.unMarshaller(Constants.WORK_DIR_RESULT_FILE, NewEntries.class);
            for (NewEntry entry : entries.getEntry()) {
                sum = sum + entry.getField();
            }
            _view.printMessage("Completed: Calculating the amount");
            _view.printMessage(String.format("Arithmetic sum of all values: %d", sum));
        } catch (Exception e) {
            _view.printMessage(String.format("Error calculating amount: %s", e.getMessage()));
            _view.stopProgram();
        }
    }

    private ResultSet getDataForCreatingFile() {
        try {
            return _managerStorage.readingData("SELECT field FROM TEST");
        } catch (Exception e) {
            return null;
        }
    }

    private void createFileXLST(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            FileOutputStream fos = new FileOutputStream(path);
            byte[] bs = Constants.TEXT_FILE_XSLT.getBytes();
            fos.write(bs, 0, bs.length);
        }
    }

    private String differenceInTime(Date begin, Date end) {
        try {
            SimpleDateFormat simpleDateFormat;
            Date result;
            Calendar diffTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            diffTime.setTimeInMillis(end.getTime() - begin.getTime());
            String temp = diffTime.get(Calendar.HOUR_OF_DAY) + ":" + diffTime.get(Calendar.MINUTE) + ":" + diffTime.get(Calendar.SECOND);
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            result = simpleDateFormat.parse(temp);
            return simpleDateFormat.format(result);
        } catch (Exception e) {
            return "_";
        }
    }
}
