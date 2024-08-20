package expression.impl;

import cell.Cell;
import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import coordinate.Coordinate;
import expression.Expression;
import sheet.SheetImpel;

public class RefExpression implements Expression {
    private final Cell cell;
    private final CellType cellType;

    public RefExpression(int row, int col, CellType cellType) {
        //TODO!!!!!!!!!!!!!!!
        this.cell = null;
        this.cellType = cellType;
    }

    @Override
    public EffectiveValue eval() {
        return new EffectiveValueImpl(cell.getCellType(), cell.getEffectiveValue());
    }

    @Override
    public CellType getFunctionResultType() {
        return cellType;
    }
}
