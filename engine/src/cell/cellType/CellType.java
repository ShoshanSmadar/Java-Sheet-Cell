package cell.cellType;

import coordinate.Coordinate;

public enum CellType {
    NUMERIC(Double.class),
    STRING(String.class),
    BOOLEAN(Boolean.class),
    UNKNOWN(Object.class),
    NAN(Double.class);

    private Class<?> type;

    CellType(Class<?> type) {
        this.type = type;
    }

    public boolean isAssignableFrom(Class<?> aType) {
        return type.isAssignableFrom(aType);
    }

}

