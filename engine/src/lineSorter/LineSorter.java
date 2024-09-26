package lineSorter;

import java.util.List;

public interface LineSorter {

    void setRangeToSort(String rangeValue);

    void setColumnToSortBy(int column);

    List<Character> getPossibleColumnsToSortBy();

    List<List<String>> sortByColumns();

    List<List<String>> getSortedSheetStrings();
}
