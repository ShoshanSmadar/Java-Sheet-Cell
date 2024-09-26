package lineSorter;

import java.util.List;

public interface LineSorter {

    void setRangeToSort(String rangeValue);

    void setColumnToSortBy(int column);

    List<Character> getPossibleColumnsToSortBy();

    void setColumnSortingOrder(char column);

    List<List<String>> sortByColumns();

    List<List<String>> getSortedSheetStrings();
}
