package expression.impl.string;

import exception.NumberIsNotAnIntException;
import expression.StringExpression;
import expression.MathematicalExpression;

public class SubExpression implements StringExpression {
    StringExpression source;
    MathematicalExpression startIndex;
    MathematicalExpression endIndex;

    SubExpression(StringExpression source, MathematicalExpression startIndex, MathematicalExpression endIndex) {
        this.source = source;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public String evaluate() {
        if (startIndex.evaluate() % 1 == 0 ) {
            throw new NumberIsNotAnIntException(startIndex.evaluate());
        }
        if (endIndex.evaluate() % 1 == 0) {
            throw new NumberIsNotAnIntException(endIndex.evaluate());
        }
        return source.evaluate().substring((int)startIndex.evaluate(), (int)endIndex.evaluate());
    }

}

