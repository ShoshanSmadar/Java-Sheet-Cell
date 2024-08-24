package coordinate;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinateDTO that = (CoordinateDTO) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
