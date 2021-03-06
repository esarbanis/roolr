/*
 * The MIT License
 *
 * Copyright (c) 2014-2014 Efthymios Sarmpanis http://esarbanis.github.io/roolr
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.esarbanis.roolr.expression;

import com.github.esarbanis.roolr.value.ArrayValue;
import com.github.esarbanis.roolr.value.NumberValue;
import com.github.esarbanis.roolr.value.Value;

/**
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymis Sarmpanis</a>
 */
public final class ExpressionBuilder {

    private ExpressionObject current;
    private String fieldName;

    private ExpressionBuilder() {
        current = new ExpressionObject();
    }

    public static ExpressionBuilder when() {
        return new ExpressionBuilder();
    }

    public ExpressionBuilder field(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public ExpressionBuilder lessThan(NumberValue number) {
        current.addExpression(new LessThan(fieldName, number));
        return this;
    }

    public ExpressionBuilder greaterThan(NumberValue number) {
        current.addExpression(new GreaterThan(fieldName, number));
        return this;
    }

    public ExpressionBuilder lessThanOrEquals(NumberValue number) {
        current.addExpression(new LessThanEquals(fieldName, number));
        return this;
    }

    public ExpressionBuilder greaterThanOrEquals(NumberValue number) {
        current.addExpression(new GreaterThanEquals(fieldName, number));
        return this;
    }

    public ExpressionBuilder like(Value value) {
        current.addExpression(new Like(fieldName, value));
        return this;
    }

    public ExpressionBuilder equals(Value value) {
        current.addExpression(new Equals(fieldName, value));
        return this;
    }

    public ExpressionBuilder in(ArrayValue<?> value) {
        current.addExpression(new In(fieldName, value));
        return this;
    }

    public ExpressionBuilder or() throws NoSuchMethodException {
        current.join = Join.OR;
        return this;
    }

    public ExpressionBuilder and() throws NoSuchMethodException {
        current.join = Join.AND;
        return this;
    }

    public Expression ok() {
        return current.left;
    }


    private class ExpressionObject {
        private Expression left;
        private Expression right;
        private Join join;

        private void addExpression(Operator expression) {
            if (current.left == null) {
                current.left = expression;
            } else {
                current.right = expression;
                reset();
            }
        }

        private void reset() {
            if (current.join != null) {
                addJoinExpression(current.join);
                current.right = null;
                current.join = null;
            }
        }

        private void addJoinExpression(Join join) {
            switch (current.join) {
                case AND:
                    current.left = new And(current.left, current.right);
                    break;
                case OR:
                default:
                    current.left = new Or(current.left, current.right);
            }
        }
    }

    private enum Join {
        AND, OR
    }
}
