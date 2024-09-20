package jaxbConvert.xmlTwo.parser;

import engine.Engine;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jaxb.xmlTwo.STLSheet;
import jaxbConvert.xmlTwo.classes.ConvertSheet;
import sheet.Sheet;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class XmlParser {
    public static Sheet sheetParser(String xmlPath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(jaxb.xmlTwo.STLSheet.class);
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        File file;
        try{
            file = new File(xmlPath);
        }
        catch(Exception e){
            throw new FileNotFoundException(xmlPath);
        }
        jaxb.xmlTwo.STLSheet XMLsheet = (jaxb.xmlTwo.STLSheet) jaxbUnmarshaller.unmarshal(file);
        return  ConvertSheet.ConvertSheetFromXML(XMLsheet);
    }

    public static Sheet sheetParser(File file) throws Exception {
        JAXBContext context = JAXBContext.newInstance(STLSheet.class);
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        jaxb.xmlTwo.STLSheet XMLsheet = (jaxb.xmlTwo.STLSheet) jaxbUnmarshaller.unmarshal(file);
        return  ConvertSheet.ConvertSheetFromXML(XMLsheet);

    }
}
