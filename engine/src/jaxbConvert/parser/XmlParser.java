package jaxbConvert.parser;

import engine.Engine;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jaxb.STLSheet;
import jaxbConvert.classes.ConvertSheet;
import jaxbConvert.classes.ConvertToXML;
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

    public static Sheet sheetParser(File file) throws Exception {
        JAXBContext context = JAXBContext.newInstance(STLSheet.class);
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        STLSheet XMLsheet = (STLSheet) jaxbUnmarshaller.unmarshal(file);
        return  ConvertSheet.ConvertSheetFromXML(XMLsheet);

    }

    public static void ConvertSheetToXML (String filePath, Engine engine) throws Exception {
        JAXBContext context = JAXBContext.newInstance(STLSheet.class);
        Marshaller marshaller = context.createMarshaller();
        File directory = new File(filePath);
        CheckFilePath(directory);
        int i = 1;
        for(STLSheet sheet : ConvertToXML.convertSheetToXML(engine)){
            String fileName = sheet.getName() + "version" + i+".xml";
            File newFile = new File(directory, fileName);

            if(!newFile.exists()){
                newFile.createNewFile();
            }

            marshaller.marshal(sheet, newFile);
            i++;
        }
    }

    private static void CheckFilePath(File file) throws Exception{
        if(!file.exists()){
            throw new FileNotFoundException("File path given was not found, Please check path and try again.");
        }
    }
}
