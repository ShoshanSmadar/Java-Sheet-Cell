package expression.impl.string;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import exception.NumberIsNotAnIntException;

import expression.Expression;

public class SubExpression implements Expression {
    Expression source;
    Expression startIndex;
    Expression endIndex;

    public SubExpression(Expression source, Expression startIndex, Expression endIndex) {
        this.source = source;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }


    @Override
    public EffectiveValue eval() {
        return null;
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.STRING;
    }
}

