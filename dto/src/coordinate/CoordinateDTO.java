package coordinate;

public class CoordinateDTO {
    protected int row;
    protected int col;

    public CoordinateDTO(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Coordinate createCoordinate() {
        return new CoordinateImpl(this.row, this.col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return (char)('A' + col) + String.valueOf(row + 1);
    }
}
