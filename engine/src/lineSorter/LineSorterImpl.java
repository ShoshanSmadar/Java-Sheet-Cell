package lineSorter;

import cell.cellType.CellType;
import coordinate.Coordinate;
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
    List<Character> orderOfColumnsToSortBy;
    List<Boolean> isNumericColumn;
    Range rangeToSort;



    public LineSorterImpl(Sheet sortingSheet){
        this.sortingSheet = sortingSheet;
    }

    @Override
    public void setRangeToSort(String rangeValue){
        rangeToSort = new RangeImpl("", rangeValue,
                sortingSheet.getSizeOfRows(), sortingSheet.getSizeOfColumns());
        List<Coordinate> coordinates = rangeToSort.getCoordinates();
        rows = new ArrayList<>();

        int numberOfColumns = coordinates.getLast().getColumn() - coordinates.getFirst().getColumn() + 1;
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
        List<Character> possibleColumnsToSort = new ArrayList<>();
        for (Boolean column : isNumericColumn){
            if(column){
                possibleColumnsToSort.add((char) (columnStart + 'A'));
            }
            columnStart++;
        }
        return possibleColumnsToSort;
    }

    @Override
    public void setColumnSortingOrder(char column){
        if(orderOfColumnsToSortBy == null){
            orderOfColumnsToSortBy = new ArrayList<>();
        }
        orderOfColumnsToSortBy.addLast(column);
    }


    public static void sortByColumns(List<Row> rows, List<Character> columnsToSortBy, int colStart) {
        Collections.sort(rows, (list1, list2) -> {
            // Compare based on the indices in comparisonOrder
            for (char column : columnsToSortBy) {
                int index = column - 'A' - colStart;
                Double value1, value2;
                if(list1.getCell(index) == null){
                    value1 = Double.MAX_VALUE;
                }
                else{
                    value1 = (Double) (list1.getCell(index).getValue());
                }
                if(list2.getCell(index) == null){
                    value2 = Double.MAX_VALUE;
                }
                else{
                    value2 = (Double) (list2.getCell(index).getValue());
                }
                int compare = value1.compareTo(value2);
                if (compare != 0) {
                    return compare;  // Return non-zero if the comparison is not equal
                }
            }
            return 0;  // Return 0 if all compared values are equal
        });
    }

    @Override
    public List<List<String>> sortByColumns() {
        sortByColumns(rows, orderOfColumnsToSortBy, rangeToSort.getColStart());
        return getSortedSheetStrings();
    }

    @Override
    public List<List<String>> getSortedSheetStrings() {
        List<List<String>> sortedSheetStrings = new ArrayList<>();

        // Iterate over the rows of the sorting sheet
        for (int i = 0; i < sortingSheet.getSizeOfRows(); i++) {
            // Initialize a new row list
            List<String> rowList = new ArrayList<>();

            for (int j = 0; j < sortingSheet.getSizeOfColumns(); j++) {
                // Check if the current cell is within the range to sort
                if (i >= rangeToSort.getRowStart() && i <= rangeToSort.getRowEnd()
                        && j >= rangeToSort.getColStart() && j <= rangeToSort.getColEnd()) {
                    // Handle the cell's value
                    if (rows.get(i -  rangeToSort.getRowStart()).getCell(j - rangeToSort.getColStart()) == null) {
                        rowList.add(""); // or add some placeholder if needed
                    } else {
                        rowList.add(String.valueOf(rows.get(i -  rangeToSort.getRowStart())
                                .getCell(j - rangeToSort.getColStart()).getValue()));
                    }
                }
                else {
                    // Outside the sorting range, fill with values accordingly
                    if (sortingSheet.getCell(i, j) == null) {
                        rowList.add(""); // Or use null if that's your intent
                    } else {
                        rowList.add(String.valueOf( sortingSheet.getCell(i, j).getEffectiveValue().getValue()));
                    }
                }
            }

            // Add the constructed row list to the sorted sheet strings
            sortedSheetStrings.add(rowList);
        }

        return sortedSheetStrings;
    }


}
