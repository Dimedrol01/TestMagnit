package application.presenters;

public interface MainPresenter {
    void startExecute(String server, int port, String database, String user, String password, int countRecord) throws Exception;
}
