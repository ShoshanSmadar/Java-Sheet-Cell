package expression.impl;

import expression.Expression;

public class Number implements Expression {
    private double number;

    public Number(double num) {
        this.number = num;
    }

    @Override
    public double evaluate() {
        return number;
    }
}
