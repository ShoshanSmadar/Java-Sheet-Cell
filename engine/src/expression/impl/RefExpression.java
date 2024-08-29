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
        if(sheet.getCell(row, col) == null){
               return new EffectiveValueImpl(CellType.UNDEFINED, "!UNDEFINED!");
        }
        EffectiveValue value = sheet.getCell(row, col).getEffectiveValue();
        if(value.getCellType() == CellType.UNDEFINED)
            return value;
        return new EffectiveValueImpl(CellType.UNKNOWN, value.getValue());
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.UNKNOWN;
    }
}
