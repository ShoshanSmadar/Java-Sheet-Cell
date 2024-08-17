package cell.cellType;

public enum CellType {
    NUMERIC(Double.class),
    STRING(String.class),
    BOOLEAN(Boolean.class);

    private Class<?> type;

    CellType(Class<?> type) {
        this.type = type;
    }

    public boolean isAssinableForm(Class<?> aType) {
        return type.isAssignableFrom(aType);
    }

}

