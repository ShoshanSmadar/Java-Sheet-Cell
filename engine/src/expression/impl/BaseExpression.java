package expression.impl;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import expression.Expression;

public class BaseExpression implements Expression {
    private final Object value;
    private final CellType cellType;

    public BaseExpression(Object expression, CellType cellType) {
        this.value = expression;
        this.cellType = cellType;
    }

    @Override
    public EffectiveValue eval() {
        return new EffectiveValueImpl(cellType, value);
    }

    @Override
    public CellType getFunctionResultType() {
        return cellType;
    }

}
