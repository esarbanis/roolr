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

import com.github.esarbanis.roolr.EvaluationContext;
import com.github.esarbanis.roolr.value.BooleanValue;
import com.github.esarbanis.roolr.value.NumberValue;
import com.github.esarbanis.roolr.value.StringValue;
import com.github.esarbanis.roolr.value.Value;

/**
 * Base class for Conditional expressions.
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymis Sarmpanis</a>
 */
public abstract class Operator implements Expression {

    private final String fieldName;
    private final Value comparisonValue;

    protected Operator(String fieldName, Value comparisonValue) {
        this.fieldName = fieldName;
        this.comparisonValue = comparisonValue;
    }

    @Override
    public boolean evaluate(EvaluationContext context) {
        Value fieldValue = resolveValue(context.getField(fieldName));
        if (!fieldValue.getClass().equals(comparisonValue.getClass())) {
            throw new IllegalArgumentException(String.format("Cannot compare %s with %s", fieldValue.getClass()
                    .getSimpleName(), comparisonValue.getClass().getSimpleName()));
        }
        return doEvaluate(fieldValue.compareTo(comparisonValue));
    }

    private Value resolveValue(Object value) {
        if (Number.class.isInstance(value)) {
            return new NumberValue(Number.class.cast(value).doubleValue());
        } else if (Boolean.class.isInstance(value)) {
            return new BooleanValue(Boolean.class.cast(value));
        } else if (String.class.isInstance(value)) {
            return new StringValue(String.class.cast(value));
        } else if (Enum.class.isInstance(value)) {
            return new StringValue(Enum.class.cast(value).name());
        }
        throw new IllegalArgumentException(String.format("%s is an unknown type", value.getClass()
                .getName()));
    }

    protected abstract boolean doEvaluate(int comparison);
}
