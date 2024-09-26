package range;


import coordinate.Coordinate;
import coordinate.CoordinateDTO;
import coordinate.CoordinateFactory;
import coordinate.CoordinateImpl;
import row.Row;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RangeImpl implements Range, Cloneable{
    private String name;
    private List<Coordinate> coordinates;
    private List<Coordinate> cellsDependingOnRange;

    @Override
    public int getRowStart() {
        return rowStart;
    }

    @Override
    public int getRowEnd() {
        return rowEnd;
    }

    @Override
    public int getColStart() {
        return colStart;
    }

    @Override
    public int getColEnd() {
        return colEnd;
    }

    private int rowStart, rowEnd, colStart, colEnd;


    public RangeImpl() {}

    public RangeImpl(String name, List<Coordinate> coordinates) {
        this.name = name;
        this.coordinates = coordinates;
        this.cellsDependingOnRange = new ArrayList<>();
    }

    public void addToDependingList(Coordinate coordinate) {
        this.cellsDependingOnRange.add(coordinate);
    }

    public RangeImpl(String name, String topAndLowerCoordinates) {
        this.name = name;
        coordinates = getAllCellsInRange(topAndLowerCoordinates);
        this.cellsDependingOnRange = new ArrayList<>();
    }

    public RangeImpl(String name, Coordinate from, Coordinate to) {
        this.name = name;
        this.coordinates = getAllCellsInRange(from, to);
        this.cellsDependingOnRange = new ArrayList<>();
    }

    @Override
    public RangeImpl clone(){
        try {
            RangeImpl range1 = (RangeImpl) super.clone();
            List<Coordinate> newCoordinates = new ArrayList<>();
            cellsDependingOnRange = new ArrayList<>();
            range1.name = this.name;
            for (Coordinate coordinate : coordinates) {
                newCoordinates.add(coordinate.clone());
            }
            range1.coordinates = newCoordinates;
            return range1;
        }
                catch(CloneNotSupportedException e){
            return null;
        }
    }

    @Override
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RangeDTO getRangeDTO() {
        List<CoordinateDTO> DTOCoordinates = new ArrayList<>();
        for (Coordinate coor : coordinates){
            DTOCoordinates.add(coor.convertToDTO());
        }
        return new RangeDTO(DTOCoordinates,this.name);
    }



    @Override
    public void checkIfRangeCanBeDeleted() throws RuntimeException{
        if(!cellsDependingOnRange.isEmpty()) {
            if (cellsDependingOnRange.size() == 1)
                throw new RuntimeException("The range " + name + " has the cell "
                        + cellsDependingOnRange
                        + " depending on it.\nCan not be deleted.");
            else
                throw new RuntimeException("The range " + name + " has the cells: "
                        + cellsDependingOnRange
                        + " depending on it.\nCan not be deleted.");
        }
    }

    @Override
    public void addCellToDependencies(Coordinate coordinate) {
        cellsDependingOnRange.add(coordinate);
    }

    @Override
    public void removeCellFromDependencies(Coordinate coordinate) {
        cellsDependingOnRange.remove(coordinate);
    }


    // Converts a column letter (e.g., 'A', 'B', 'C') to an index (e.g., 1, 2, 3)
    public static int letterToIndex(String letter) {
        int index = 0;
        for (int i = 0; i < letter.length(); i++) {
            index = index * 26 + (letter.charAt(i) - 'A' + 1);
        }
        return index;
    }

    // Converts an index (e.g., 1, 2, 3) back to a column letter (e.g., 'A', 'B', 'C')
    public static String indexToLetter(int index) {
        StringBuilder letter = new StringBuilder();
        while (index > 0) {
            index--; // adjust because 'A' starts at 1
            letter.insert(0, (char) ('A' + (index % 26)));
            index /= 26;
        }
        return letter.toString();
    }

    // Extracts all cells in the given range, for example "A1..B5"
    public List<Coordinate> getAllCellsInRange(String range) {
        // Split the range into the top-left and bottom-right parts
        String[] parts = range.split("\\.\\.");
        String topLeft = parts[0];
        String bottomRight = parts[1];

        // Extract the column letters and row numbers
        String topLeftColumn = topLeft.replaceAll("\\d", "");  // Extract the letters (column)
        int topLeftRow = Integer.parseInt(topLeft.replaceAll("\\D", "")) - 1;  // Extract the numbers (row)

        String bottomRightColumn = bottomRight.replaceAll("\\d", "");  // Extract the letters (column)
        int bottomRightRow = Integer.parseInt(bottomRight.replaceAll("\\D", "")) - 1;  // Extract the numbers (row)

        // Convert the column letters to indices
        int startColumnIndex = letterToIndex(topLeftColumn) - 1;
        int endColumnIndex = letterToIndex(bottomRightColumn) - 1;

        // List to store all cells in the range
        List<Coordinate> cellsInRange = new ArrayList<>();
        rowStart = topLeftRow;
        rowEnd = bottomRightRow;
        colStart = startColumnIndex;
        colEnd = endColumnIndex;

        // Loop through the rows and columns and add each cell to the list
        for (int row = topLeftRow; row <= bottomRightRow; row++) {
            List<Row> rowsInRange = new ArrayList<>();
            for (int col = startColumnIndex; col <= endColumnIndex; col++) {
                Coordinate coordinate = new CoordinateImpl(row, col);

                String cell = indexToLetter(col) + row;
                cellsInRange.add(coordinate);
            }
        }

        return cellsInRange;
    }


    public List<Coordinate> getAllCellsInRange(Coordinate coordinateFrom, Coordinate CoordinateTo ) {
        List<Coordinate> cellsInRange = new ArrayList<>();

        for(int i = coordinateFrom.getRow(); i<= CoordinateTo.getRow(); i++) {
            for(int j = coordinateFrom.getColumn(); j<= CoordinateTo.getColumn(); j++) {
                cellsInRange.add( CoordinateFactory.createCoordinate(i , j));
            }
        }
        return cellsInRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RangeImpl range = (RangeImpl) o;
        return Objects.equals(name, range.name) && Objects.equals(coordinates, range.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
