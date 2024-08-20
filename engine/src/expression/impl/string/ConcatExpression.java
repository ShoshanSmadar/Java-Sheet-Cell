package expression.impl.string;

import expression.StringExpression;

public class ConcatExpression implements StringExpression {
    StringExpression left;
    StringExpression right;

    ConcatExpression(StringExpression left, StringExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String evaluate() {
        return left.evaluate() + right.evaluate();
    }

}
