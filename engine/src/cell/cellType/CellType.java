package cell.cellType;

import coordinate.Coordinate;

public enum CellType {
    NUMERIC(Double.class),
    STRING(String.class),
    BOOLEAN(Boolean.class),
    COORDINATE(Coordinate.class);

    private Class<?> type;

    CellType(Class<?> type) {
        this.type = type;
    }

    public boolean isAssignableFrom(Class<?> aType) {
        return type.isAssignableFrom(aType);
    }

}

