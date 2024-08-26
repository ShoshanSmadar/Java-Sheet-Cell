package jaxbConvert.parser;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jaxb.STLSheet;
import jaxbConvert.classes.ConvertSheet;
import sheet.Sheet;

import java.io.File;

public abstract class XmlParser {
    public static Sheet sheetParser(String xmlPath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(STLSheet.class);
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        STLSheet XMLsheet = (STLSheet) jaxbUnmarshaller.unmarshal(new File(xmlPath));
        return  ConvertSheet.ConvertSheet(XMLsheet);
    }
}