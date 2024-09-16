package range;


import coordinate.Coordinate;
import coordinate.CoordinateImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RangeImpl implements Range, Cloneable{
    private String name;
    private List<Coordinate> coordinates;

    public RangeImpl() {}

    public RangeImpl(String name, List<Coordinate> coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public RangeImpl(String name, String topAndLowerCoordinates) {
        this.name = name;
        coordinates = getAllCellsInRange(topAndLowerCoordinates);
    }

    @Override
    public RangeImpl clone(){
        try {
            RangeImpl range1 = (RangeImpl) super.clone();
            List<Coordinate> newCoordinates = new ArrayList<>();
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
    public static List<Coordinate> getAllCellsInRange(String range) {
        // Split the range into the top-left and bottom-right parts
        String[] parts = range.split("\\.\\.");
        String topLeft = parts[0];
        String bottomRight = parts[1];

        // Extract the column letters and row numbers
        String topLeftColumn = topLeft.replaceAll("\\d", "");  // Extract the letters (column)
        int topLeftRow = Integer.parseInt(topLeft.replaceAll("\\D", ""));  // Extract the numbers (row)

        String bottomRightColumn = bottomRight.replaceAll("\\d", "");  // Extract the letters (column)
        int bottomRightRow = Integer.parseInt(bottomRight.replaceAll("\\D", ""));  // Extract the numbers (row)

        // Convert the column letters to indices
        int startColumnIndex = letterToIndex(topLeftColumn);
        int endColumnIndex = letterToIndex(bottomRightColumn);

        // List to store all cells in the range
        List<Coordinate> cellsInRange = new ArrayList<>();

        // Loop through the rows and columns and add each cell to the list
        for (int row = topLeftRow; row <= bottomRightRow; row++) {
            for (int col = startColumnIndex; col <= endColumnIndex; col++) {
                Coordinate coordinate = new CoordinateImpl(row, col);

                String cell = indexToLetter(col) + row;
                cellsInRange.add(coordinate);
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
