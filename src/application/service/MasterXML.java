package application.service;

import application.Main;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class MasterXML {
    private String nameFile;

    public MasterXML(String nameFile) {
        this.nameFile = nameFile;
    }

    public void saveDocument(Marshaller marshaller, Object root) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(nameFile);
            marshaller.marshal(root, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    public Marshaller marshaller(Class<?> contextPath) {
        Marshaller marshaller = null;
        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return marshaller;
    }

    public Object unmarshaller(Class<?> contextPath) throws ParserConfigurationException, IOException, SAXException, JAXBException {
        FileInputStream file = new FileInputStream (nameFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(contextPath);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(file);
    }

    public static void xmlTransform(String originalFile, String xsl, String resultFile) {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File(xsl));
            Transformer transformer = tFactory.newTransformer(xslt);
            Source original = new StreamSource(new File(originalFile));
            transformer.transform(original, new StreamResult(new File(resultFile)));
        } catch (Exception e) {
            System.out.println("Error converting XML file: " + e.getMessage());
            Main.commonActionWithException();
        }
    }
}
