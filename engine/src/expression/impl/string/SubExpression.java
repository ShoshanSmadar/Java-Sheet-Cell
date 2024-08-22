package expression.impl.string;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;

import cell.cellType.EffectiveValueImpl;
import expression.Expression;
import sheet.Sheet;

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
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue sourceValue = source.eval(sheet);
        EffectiveValue startValue = startIndex.eval(sheet);
        EffectiveValue endValue = endIndex.eval(sheet);

        String subString = sourceValue.extractValueWithExpectation(String.class).substring(startValue.extractValueWithExpectation(Integer.class), endValue.extractValueWithExpectation(Integer.class));

        return new EffectiveValueImpl(CellType.STRING, subString);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.STRING;
    }
}

