package lineSorter;

import cell.cellType.CellType;
import coordinate.Coordinate;
import coordinate.CoordinateDTO;
import range.Range;
import range.RangeImpl;
import row.Row;
import row.RowImpl;
import sheet.Sheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineSorterImpl implements LineSorter{
    Sheet sortingSheet;
    List<Row> rows;
    List<Character> columnsToSortBy;
    List<Boolean> isNumericColumn;
    Range rangeToSort;



    public LineSorterImpl(Sheet sortingSheet){
        this.sortingSheet = sortingSheet;
    }

    @Override
    public void setRangeToSort(String rangeValue){
        rangeToSort = new RangeImpl("", rangeValue);
        List<Coordinate> coordinates = rangeToSort.getCoordinates();
        rows = new ArrayList<>();

        int numberOfColumns = coordinates.getLast().getColumn() - coordinates.get(0).getColumn() + 1;
        isNumericColumn = new ArrayList<>();
        for(int i = 0; i < numberOfColumns; i++){
            isNumericColumn.add(true);
        }
        int columnsAdded = 0;
        rows.add(new RowImpl());
        for(Coordinate coordinate : coordinates){
            if(columnsAdded == numberOfColumns){
                columnsAdded = 0;
                rows.add(new RowImpl());
            }
            if(sortingSheet.getCell(coordinate) == null){
                rows.getLast().addCell(null);
                columnsAdded ++;
                continue;
            }
            if(!(sortingSheet.getCell(coordinate).getEffectiveValue().getCellType() == CellType.NUMERIC)){
                isNumericColumn.set(columnsAdded, false);
            }
            rows.getLast().addCell(sortingSheet.getCell(coordinate).getEffectiveValue());
            columnsAdded++;
        }
    }

    @Override
    public void setColumnToSortBy(int column){
        if(columnsToSortBy == null){
            columnsToSortBy = new ArrayList<>();
        }
        columnsToSortBy.add((char) ('A' + column));
    }

    @Override
    public List<Character> getPossibleColumnsToSortBy(){
        int columnStart = rangeToSort.getCoordinates().getFirst().getColumn();
        for (Boolean column : isNumericColumn){
            if(column){
                setColumnToSortBy(columnStart);
            }
            columnStart++;
        }
        return columnsToSortBy;
    }


    @Override
    public List<List<String>> sortByColumns() {
        Collections.sort(rows, (list1, list2) -> {
            // Compare based on the indices in comparisonOrder
            for (int index : columnsToSortBy) {
                Double value1, value2;
                if(list1.getCell(index) == null){
                    value1 = Double.MAX_VALUE;
                }
                else{
                    value1 = (Double) (list1.getCell(index));
                }
                if(list2.getCell(index) == null){
                    value2 = Double.MAX_VALUE;
                }
                else{
                    value2 = (Double) (list2.getCell(index));
                }
                int compare = value1.compareTo(value2);
                if (compare != 0) {
                    return compare;  // Return non-zero if the comparison is not equal
                }
            }
            return 0;  // Return 0 if all compared values are equal
        });

        return getSortedSheetStrings();
    }

    @Override
    public List<List<String>> getSortedSheetStrings(){
        List<List<String>> sortedSheetStrings = new ArrayList<>();

        for(int i = 0; i < sortingSheet.getSizeOfRows(); i++){
            sortedSheetStrings.add(new ArrayList<>());
            for (int j = 0; j< sortingSheet.getSizeOfColumns(); j++){
                if(i >= rangeToSort.getRowStart() && i <= rangeToSort.getRowEnd()
                        && j >= rangeToSort.getColStart() && j <= rangeToSort.getColEnd()){
                    if(rows.get(i).getCell(j) == null){
                        sortedSheetStrings.get(i).add(null);
                    }
                    else {
                        sortedSheetStrings.getLast().add((String) rows.get(i).getCell(j));
                    }
                }
                else {
                    if(sortingSheet.getCell(i, j) == null){
                        sortedSheetStrings.get(i).add("");
                    }
                    else {
                        sortedSheetStrings.getLast().add((String) sortingSheet.getCell(i,j).getEffectiveValue().getValue());
                    }
                }
            }
        }
        return sortedSheetStrings;
    }
}
