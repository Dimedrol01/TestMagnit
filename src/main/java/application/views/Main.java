package application.views;

import application.presenters.FactoryLayerOne;
import application.presenters.MainPresenter;

import java.util.Scanner;

public class Main implements MainView {

    public static void main(String[] args) {
        MainPresenter presenter = FactoryLayerOne.getMainPresenter(new Main());
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Name server or IP-address:");
            String server = scanner.next();
            System.out.print("Port:");
            int port = scanner.nextInt();
            System.out.print("Name database:");
            String database = scanner.next();
            System.out.print("User:");
            String user = scanner.next();
            System.out.print("Password:");
            String password = scanner.next();
            System.out.print("N:");
            int countRecord = scanner.nextInt();
            presenter.startExecute(server, port, database, user, password, countRecord);
        } catch (Exception e) {
            System.out.println("Input incorrect: " + e.getMessage());
            System.out.println("The work of the program will be completed.");
            System.exit(0);
        }
    }

    public void printMessage(String text) {
        System.out.println(text);
    }

    public void stopProgram() {
        System.out.println("The work of the program will be completed.");
        System.exit(0);
    }
}
