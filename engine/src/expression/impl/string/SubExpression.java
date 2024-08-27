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

        String sourseStringValue = sourceValue.extractValueWithExpectation(String.class);
        Double startIndexValue = startValue.extractValueWithExpectation(Double.class);
        Double endIndexValue = endValue.extractValueWithExpectation(Double.class);

        if(startIndexValue < 0 || startIndexValue > endIndexValue || endIndexValue > sourseStringValue.length()
                || Math.round(startIndexValue) != startIndexValue || Math.round(endIndexValue) != endIndexValue) {
            return new EffectiveValueImpl(CellType.UNDEFINED, "!UNDEFINED!");
        }
        String subString = sourceValue.extractValueWithExpectation(String.class).substring((int)Math.round(startIndexValue),(int) Math.round(endIndexValue));

        return new EffectiveValueImpl(CellType.STRING, subString);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.STRING;
    }
}

