package program.function;

public class FunctionImpl implements ProgramFunctions{

    @Override
    public boolean addFile() {
        return false;
    }

    @Override
    public void showSheet() {
        //int row = sheetDTO.getRowSize();
        //int col = sheetDTO.getColSize();
        //int rowHeight = sheetDTO.getRowHeight();
        //int colWidth = sheetDTO.getColWidth();


    }

    public void printFirstRow(int colLength, int numberOfCols, int firstColLength)
    {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append(" ".repeat(firstColLength)).append("|");

        // Iterate through the number of columns
        for (int i = 0; i < numberOfCols; i++) {
            // Calculate the letter to print (e.g., 'A', 'B', 'C', ...)
            char letter = (char) ('A' + (i % 26));

            // Calculate spaces needed before and after the letter
            int spacesBefore = (colLength - 1) / 2;
            int spacesAfter = colLength - 1 - spacesBefore;

            // Append spaces, letter, and more spaces
            rowBuilder.append(" ".repeat(spacesBefore))
                    .append(letter)
                    .append(" ".repeat(spacesAfter))
                    .append("|");
        }

        // Print the final row
        System.out.println(rowBuilder.toString());
    }

    public void printCellRow(int colLength, int numberOfCols, int firstColLength, int colNumber)
    {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append(" ".repeat(firstColLength)).append("|");

        // Iterate through the number of columns
        for (int i = 0; i < numberOfCols; i++) {
            // Calculate the letter to print (e.g., 'A', 'B', 'C', ...)
            char letter = (char) ('A' + (i % 26));

            // Calculate spaces needed before and after the letter
            int spacesBefore = (colLength - 1) / 2;
            int spacesAfter = colLength - 1 - spacesBefore;

            // Append spaces, letter, and more spaces
            rowBuilder.append(" ".repeat(spacesBefore))
                    .append(letter)
                    .append(" ".repeat(spacesAfter))
                    .append("|");
        }

        // Print the final row
        System.out.println(rowBuilder.toString());
    }

    @Override
    public void showCell() {

    }

    @Override
    public boolean updateCell() {
        return false;
    }

    @Override
    public void showVersion() {

    }

    @Override
    public void exitProgram() {

    }
}
