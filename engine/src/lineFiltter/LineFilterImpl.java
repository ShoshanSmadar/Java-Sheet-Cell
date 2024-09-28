package lineFiltter;

import cell.CellDTO;
import cell.cellType.CellType;
import coordinate.Coordinate;
import coordinate.CoordinateDTO;
import range.Range;
import range.RangeImpl;
import row.Row;
import row.RowImpl;
import sheet.Sheet;
import sheet.SheetDTO;

import java.util.ArrayList;
import java.util.List;

public class LineFilterImpl implements LineFillter{
    Character colToFilterBy;
    List<Row> rowsToFilter;
    SheetDTO sheetDTO;
    Sheet sheet;
    Range rangeToFilter;
    List<CellType> cellTypeInColumn;
    Boolean isNumericInColumn = false, isBooleanInColumn = false, isStringInColumn = false;
    Boolean filterByNumeric = false, filterByBoolean = false, filterByString = false;

    public LineFilterImpl(SheetDTO sheetDTO, Sheet sheet){
        this.sheetDTO = sheetDTO;
        this.sheet = sheet;
    }

    @Override
    public List<Character> getPossibleColumns() {
        List<Character> possibleColumns = new ArrayList<>();
        for(int i = rangeToFilter.getColStart(); i <= rangeToFilter.getColEnd(); i++){
            possibleColumns.add((char) (i + 'A'));
        }
        return possibleColumns;
    }

    @Override
    public void setRowsToFilter(String rangeValue){
        rangeToFilter = new RangeImpl("", rangeValue, sheet.getSizeOfRows(), sheet.getSizeOfColumns());
        List<Coordinate> coordinates = rangeToFilter.getCoordinates();
        List<CoordinateDTO> coordinateDTOs = new ArrayList<CoordinateDTO>();
        for (Coordinate coordinate : coordinates) {
            coordinateDTOs.add(coordinate.convertToDTO());
        }
        rowsToFilter = new ArrayList<>();

        int numberOfColumns = coordinates.getLast().getColumn() - coordinates.getFirst().getColumn() + 1;
        int columnsAdded = 0;
        rowsToFilter.add(new RowImpl());
        for(Coordinate coordinate : coordinates){
            if(columnsAdded == numberOfColumns){
                columnsAdded = 0;
                rowsToFilter.add(new RowImpl());
            }
            if(sheetDTO.getCell(coordinate.convertToDTO()) == null){
                rowsToFilter.getLast().addCell(null);
                columnsAdded ++;
                continue;
            }
            rowsToFilter.getLast().addCell(sheet.getCell(coordinate).getEffectiveValue());
            columnsAdded++;
        }
    }

    @Override
    public void setColToFilterBy(Character colToFilterBy) {
        this.colToFilterBy = colToFilterBy;
    }

    @Override
    public List<String> getValueTypesPossibleInColumn(){
        setCellTypeInColumn();
        List<String> possibleToFilterBy = new ArrayList<>();
        if(isNumericInColumn){
            possibleToFilterBy.add("Numeric");
        }
        if(isBooleanInColumn){
            possibleToFilterBy.add("Boolean");
        }
        if(isStringInColumn){
            possibleToFilterBy.add("String");
        }
        return possibleToFilterBy;
    }

    private void setCellTypeInColumn(){
        cellTypeInColumn = new ArrayList<>();
        for(int i = rangeToFilter.getRowStart(); i <= rangeToFilter.getRowEnd(); i++){
            CellDTO cell = sheetDTO.getCell(new CoordinateDTO(i, colToFilterBy - 'A'));
            if(cell == null){
                cellTypeInColumn.add(null);
            }
            else {
                if(cell.getEffectiveValue() instanceof Double){
                    cellTypeInColumn.add(CellType.NUMERIC);
                    isNumericInColumn = true;
                }
                 else if(cell.getEffectiveValue() instanceof Boolean){
                     cellTypeInColumn.add(CellType.BOOLEAN);
                     isBooleanInColumn = true;
                }
                 else{
                     cellTypeInColumn.add(CellType.STRING);
                     isStringInColumn = true;
                }
            }
        }
    }

    @Override
    public void setFilterBy(CellType cellType){
        if(cellType == CellType.NUMERIC){
            filterByNumeric = true;
        }
        else if(cellType == CellType.BOOLEAN){
            filterByBoolean = true;
        }
        else if(cellType == CellType.STRING){
            filterByString = true;
        }
    }

    @Override
    public void filter(){
        for(int i = 0; i < cellTypeInColumn.size(); i++){
            boolean deleteRow = false;
            if(!filterByNumeric && cellTypeInColumn.get(i) == CellType.NUMERIC){
                deleteRow = true;
            }
            if(!filterByBoolean && cellTypeInColumn.get(i) == CellType.BOOLEAN){
                deleteRow = true;
            }
            if(!filterByString && cellTypeInColumn.get(i) == CellType.STRING){
                deleteRow = true;
            }
            if(deleteRow){
                    for(int col = rangeToFilter.getColStart(); col <= rangeToFilter.getColEnd(); col++){
                        if(sheetDTO.getCell(new CoordinateDTO(i + rangeToFilter.getRowStart(), col)) != null){
                            sheetDTO.getCell(new CoordinateDTO(i + rangeToFilter.getRowStart(), col)).setEffectiveValue("");
                        }
                    }
            }
        }
    }

    @Override
    public SheetDTO getFilterdSheet(){
        return sheetDTO;
    }
}
