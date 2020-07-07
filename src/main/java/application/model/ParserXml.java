package application.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class ParserXml implements ManagerXML {
    private String nameFile;

    public ParserXml(String nameFile) {
        this.nameFile = nameFile;
    }

    @Override
    public void saveDocument(Object root, Class<?> contextPath) throws Exception {
        Marshaller marshaller = getMarshaller(contextPath);
        FileOutputStream fileOutputStream = new FileOutputStream(nameFile);
        marshaller.marshal(root, fileOutputStream);
    }

    @Override
    public void makeTransformXml(String originalFile, String xslt, String resultFile) throws Exception {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Source xsltTemp = new StreamSource(new File(xslt));
        Transformer transformer = tFactory.newTransformer(xsltTemp);
        Source original = new StreamSource(new File(originalFile));
        transformer.transform(original, new StreamResult(new File(resultFile)));
    }

    @Override
    public Object unMarshaller(String pathToFile, Class<?> contextPath) throws Exception {
        FileInputStream file = new FileInputStream(pathToFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(contextPath);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(file);
    }

    private Marshaller getMarshaller(Class<?> contextPath) throws Exception {
        Marshaller marshaller;
        JAXBContext context = JAXBContext.newInstance(contextPath);
        marshaller = context.createMarshaller();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        return marshaller;
    }
}
