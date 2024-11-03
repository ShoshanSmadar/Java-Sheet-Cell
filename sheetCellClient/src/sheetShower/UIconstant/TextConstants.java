package sheetShower.UIconstant;

public class TextConstants {
    public static final String VERSION_LABEL = "Current version: ";
    public static final String SELECTED_CELL = "Selected Cell: ";
    public static final String LAST_UPDATED_VERSION = "Last updated version: ";
    public static final String ORIGINAL_VALUE = "Original value: ";
    public static final String ACTION_LINE_EXPLANATION = """
            Add a value or a function to the Cell.
            A function should be written in the following order:
            {<function name>,<first argument>,...,<last argument>}
            For example: {PLUS,1,2} or {MINUS,{PLUS,1,2},1}
            Following functions are possible:
            Mathematicl functions: PLUS, MINUS, TIMES, DIVIDE, MOD, POW, ABS, SUM, AVERAGE, PERCENT.
            Boolean(true/false) functions: EQUAL, NOT, OR, AND, BIGGER, LESS, IF.
            Sentence functions: CONCAT, SUB.
            Refrence another cell: REF.
            If you want to clear the cell Leave line empty and press Update value""";
    public static final String EREA_CHOOSING_EXPLANATION = """
            To choose a range write:
            <top-left-cell>..<bottom-right-cell>
            for example: A2..D6
            """;

}
