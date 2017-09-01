package application;

import application.service.MainExecutor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Main {
    private static MainExecutor mainExecutor;

    public static void main(String[] args) throws ParseException {
        mainExecutor = new MainExecutor();
        mainExecutor.setConnector();
        dataInput();
        mainExecutor.start();
        System.out.println("Start time: " + getCurrentData());
        System.out.println();
        Date begin = new Date();
        System.out.println("Completed: Data input");
        if (mainExecutor.checkingTable()) {
            System.out.println("Completed: Checking the table");
            mainExecutor.insertingData();
        } else {
            System.out.println("Completed: Checking the table");
            mainExecutor.clearingTable();
            System.out.println("Completed: Clearing a table");
            mainExecutor.insertingData();
            System.out.println("Completed: Inserting data");
        }
        mainExecutor.creatingXmlFile();
        System.out.println("Completed: Creating an XML file");
        mainExecutor.convertingXmlFile();
        System.out.println("Completed: Converting an XML file");
        mainExecutor.calculatingAmount();
        System.out.println();
        Date end = new Date();
        System.out.println("The time of work: " + differenceInTime(begin, end));
        System.out.println();
        System.out.println("Time of completion: " + getCurrentData());
    }

    public static void commonActionWithException() {
        System.out.println("The work of the program will be completed.");
        System.exit(0);
    }

    private static String differenceInTime(Date begin, Date end) {
        SimpleDateFormat simpleDateFormat = null;
        Date result = null;
        try {
            Calendar diffTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            diffTime.setTimeInMillis(end.getTime() - begin.getTime());
            String temp = diffTime.get(Calendar.HOUR_OF_DAY) + ":" + diffTime.get(Calendar.MINUTE) + ":" + diffTime.get(Calendar.SECOND);
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            result = simpleDateFormat.parse(temp);
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
            commonActionWithException();
        }
        return simpleDateFormat.format(result);
    }

    private static void dataInput() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Name server or IP-address:");
            mainExecutor.getConnector().setNameServer(scanner.next());
            System.out.print("Port:");
            mainExecutor.getConnector().setPort(scanner.next());
            System.out.print("Name database:");
            mainExecutor.getConnector().setNameDatabase(scanner.next());
            System.out.print("User:");
            mainExecutor.getConnector().setUser(scanner.next());
            System.out.print("Password:");
            mainExecutor.getConnector().setPassword(scanner.next());
            System.out.print("N:");
            mainExecutor.setCountRecords(scanner.nextInt());

        } catch (Exception e) {
            System.out.println("Input error N: " + e.getMessage());
            commonActionWithException();
        }
    }

    private static String getCurrentData() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(now);
    }
}
