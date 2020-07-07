package application.model;

public interface ManagerXML {
    void saveDocument(Object root, Class<?> contextPath) throws Exception;
    void makeTransformXml(String originalFile, String xslt, String resultFile) throws Exception;
    Object unMarshaller(String pathToFile, Class<?> contextPath) throws Exception;
}
