package expression.impl.mathematical;

import expression.MathematicalExpression;

public class Number implements MathematicalExpression {
    private double number;

    public Number(double num) {
        this.number = num;
    }

    @Override
    public double evaluate() {
        return number;
    }
}
