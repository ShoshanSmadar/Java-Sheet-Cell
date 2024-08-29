package jaxbConvert.parser;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jaxb.STLSheet;
import jaxbConvert.classes.ConvertSheet;
import sheet.Sheet;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class XmlParser {
    public static Sheet sheetParser(String xmlPath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(STLSheet.class);
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        File file;
        try{
            file = new File(xmlPath);
        }
        catch(Exception e){
            throw new FileNotFoundException(xmlPath);
        }
        STLSheet XMLsheet = (STLSheet) jaxbUnmarshaller.unmarshal(file);
        return  ConvertSheet.ConvertSheetFromXML(XMLsheet);
    }
}
