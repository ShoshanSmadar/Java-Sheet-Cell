package engine;

import cell.CellDTO;
import cell.cellType.CellType;
import coordinate.CoordinateDTO;
import jakarta.xml.bind.JAXBException;
import jaxbConvert.xmlTwo.parser.XmlParser;
import lineFiltter.LineFillter;
import lineFiltter.LineFilterImpl;
import lineSorter.LineSorter;
import lineSorter.LineSorterImpl;
import sheet.Sheet;
import sheet.SheetDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EngineImpl implements Engine{
    private List<Sheet> sheetList;
    private LineSorter lineSorter;
    private LineFillter lineFillter;

    public EngineImpl() {
        sheetList = new ArrayList<>();
    }

    public EngineImpl(SheetDTO sheet, String sheetName) {
        sheetList = new ArrayList<>();
        sheetList.add(sheet.createSheetFromDTO());
    }

    @Override
    public void startLineSorter(){
        lineSorter = new LineSorterImpl(sheetList.getLast());
    }

    @Override
    public void setSortingRange(String rangeValue){
        lineSorter.setRangeToSort(rangeValue);
    }

    @Override
    public List<Character> getPossibleSortingColumns(){
        return lineSorter.getPossibleColumnsToSortBy();
    }

    @Override
    public List<List<String>> sortColumns(){
        return lineSorter.sortByColumns();
    }

    @Override
    public void addColumnToSortingOrder(char column) {
        lineSorter.setColumnSortingOrder(column);
    }



    @Override
    public SheetDTO getSheetDTO() {
        if (!sheetList.isEmpty()) {
            return sheetList.getLast().convertToSheetDTO();
        }
        return null;
    }



    @Override
    public CellDTO getCellDTO(CoordinateDTO coordinate) {
        return sheetList.getLast().getCell(coordinate.getRow(), coordinate.getCol()).getConvertToCellDTO();
    }

    @Override
    public int getSheetCurrentVersion() {
        return sheetList.size();
    }

    @Override
    public List<Integer> getALLPastSheetNumberOfCellsChanged(){
        List<Integer> cellsChangedList = new ArrayList<>();
        for(Sheet sheet : sheetList){
            cellsChangedList.addLast(sheet.getNumberOfCellsChangedInVersion());
        }
        return cellsChangedList;
    }

    @Override
    public SheetDTO getOldVersionSheet(int versionWanted) {
        if(versionWanted < 1 || versionWanted > sheetList.size()){
            throw new IndexOutOfBoundsException("Invalid version wanted, version must be between 1-" + (sheetList.size()));
        }
        return sheetList.get(versionWanted - 1).convertToSheetDTO();
    }

    @Override
    public void changeCell(CoordinateDTO coordinateToChange, String expression) throws Exception{
        sheetList.add(sheetList.getLast().UpdateCellValueAndSheet(coordinateToChange.getRow(), coordinateToChange.getCol(), expression));
    }

    @Override
    public void enterNewSheetFromXML(File file) throws JAXBException, FileNotFoundException {
        try{
            Sheet newSheet = XmlParser.sheetParser(file);
            newSheet.calculateAllSheetAffectiveValue();
            sheetList.clear();
            sheetList.add(newSheet);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Error accrued while loading XML file.\n" +
                    "The error accrued because: file wasn't found in given path.");
        }
        catch(Exception e){
            throw new RuntimeException("Error accrued while loading XML file.\n" +
                    "The error accrued because: " + e.getMessage());
        }
    }

    @Override
    public void enterNewSheetFromXML(String xmlPath) {
        try{
            if(!xmlPath.endsWith(".xml"))
                throw new Exception("Invalid file format.\n");
            Sheet newSheet = XmlParser.sheetParser(xmlPath);
            newSheet.calculateAllSheetAffectiveValue();
            sheetList.clear();
            sheetList.add(newSheet);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Error accrued while loading XML file.\n" +
                    "The error accrued because: file wasn't found in given path.");
        }
        catch(Exception e){
            throw new RuntimeException("Error accrued while loading XML file.\n" +
                    "The error accrued because: " + e.getMessage());
        }
    }

    @Override
    public void addRange(String rangeName, String rangeValues){
        sheetList.getLast().addRange(rangeName, rangeValues);
    }

    @Override
    public void deleteRange(String rangeName){
        sheetList.getLast().deleteRange(rangeName);
    }

    @Override
    public void setLineFilter(){
        lineFillter = new LineFilterImpl(sheetList.getLast().convertToSheetDTO(), sheetList.getLast());
    }



    @Override
    public void setRowsToFilter(String rangeValue) {
        lineFillter.setRowsToFilter(rangeValue);
    }

    @Override
    public void setColToFilterBy(Character colToFilterBy) {
        lineFillter.setColToFilterBy(colToFilterBy);
    }

    @Override
    public List<String> getValueTypesPossibleInColumn() {
        return lineFillter.getValueTypesPossibleInColumn();
    }

    @Override
    public void setFilterBy(String cellType) {
        if(Objects.equals(cellType, "Numeric")){
            lineFillter.setFilterBy(CellType.NUMERIC);
        }
        else if(Objects.equals(cellType, "String")){
            lineFillter.setFilterBy(CellType.STRING);
        }
        else if(Objects.equals(cellType, "Boolean")){
            lineFillter.setFilterBy(CellType.BOOLEAN);
        }
    }

    @Override
    public void filter() {
        lineFillter.filter();
    }

    @Override
    public List<Character> getColumnsToFiltter() {
        return lineFillter.getPossibleColumns();
    }

    @Override
    public SheetDTO getNewFilterdSheet(){
        return lineFillter.getFilterdSheet();
    }
}