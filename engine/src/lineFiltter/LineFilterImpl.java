package lineFiltter;

import cell.CellDTO;
import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import coordinate.Coordinate;
import coordinate.CoordinateDTO;
import range.Range;
import range.RangeImpl;
import row.Row;
import row.RowImpl;
import sheet.SheetDTO;

import java.util.ArrayList;
import java.util.List;

public class LineFilterImpl implements LineFillter{
    Character colToFilterBy;
    List<Row> rowsToFilter;
    SheetDTO sheet;
    Range rangeToFilter;
    List<CellType> cellTypeInColumn;
    Boolean isNumericInColumn = false, isBooleanInColumn = false, isStringInColumn = false;
    Boolean filterByNumeric = false, filterByBoolean = false, filterByString = false;

    public LineFilterImpl(SheetDTO sheet){
        this.sheet = sheet;
    }

    @Override
    public void setColToFilter(Character colToFilterBy){
        this.colToFilterBy = colToFilterBy;
    }

    @Override
    public void setRowsToFilter(String rangeValue){
        rangeToFilter = new RangeImpl("", rangeValue);
        List<Coordinate> coordinates = rangeToFilter.getCoordinates();
        List<CoordinateDTO> coordinateDTOs = new ArrayList<CoordinateDTO>();
        for (Coordinate coordinate : coordinates) {
            coordinateDTOs.add(coordinate.convertToDTO());
        }
        rowsToFilter = new ArrayList<>();

        int numberOfColumns = coordinates.getLast().getColumn() - coordinates.getFirst().getColumn() + 1;
        int columnsAdded = 0;
        rowsToFilter.add(new RowImpl());
        for(CoordinateDTO coordinate : coordinateDTOs){
            if(columnsAdded == numberOfColumns){
                columnsAdded = 0;
                rowsToFilter.add(new RowImpl());
            }
            if(sheet.getCell(coordinate) == null){
                rowsToFilter.getLast().addCell(null);
                columnsAdded ++;
                continue;
            }
            rowsToFilter.getLast().addCell((EffectiveValue) sheet.getCell(coordinate).getEffectiveValue());
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
            CellDTO cell = sheet.getCell(new CoordinateDTO(i, colToFilterBy));
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
        for(int i = 0; i <= rangeToFilter.getColEnd() - rangeToFilter.getColStart(); i++){
            Boolean deleteRow = false;
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
                for(int row = rangeToFilter.getRowStart(); row <= rangeToFilter.getRowEnd(); row++){
                    for(int col = rangeToFilter.getColStart(); col <= rangeToFilter.getColEnd(); col++){
                        sheet.getCell(new CoordinateDTO(row, col)).setEffectiveValue("");
                    }
                }
            }
        }
    }
}
