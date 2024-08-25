package expression.impl;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import expression.Expression;
import sheet.Sheet;

public class RefExpression implements Expression {
    private final int row;
    private final int col;

    public RefExpression(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public EffectiveValue eval(Sheet sheet) {
        return new EffectiveValueImpl(CellType.UNKNOWN, sheet.getCell(row, col).getEffectiveValue().getValue());
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.UNKNOWN;
    }
}
