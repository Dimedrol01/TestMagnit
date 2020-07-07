package application.model;

public final class FactoryLayerTwo {
    private FactoryLayerTwo() {}

    public static ManagerStorage getManagerStorage(String server, int port, String database, String user, String password) throws Exception {
        return new MySql(Connector.getConnection(server, String.valueOf(port), database, user, password));
    }

    public static ManagerXML getManagerXML() {
        return new ParserXml(Constants.WORK_DIR_ORIGINAL_FILE);
    }
}
