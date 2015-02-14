/*
 * The MIT License
 * 
 * Copyright (c) 2014-2014 Efthymios Sarmpanis http://esarbanis.github.io/roolr
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.esarbanis.roolr;

import com.github.esarbanis.roolr.value.NumberValue;
import com.github.esarbanis.roolr.value.BooleanValue;
import com.github.esarbanis.roolr.value.StringValue;
import com.github.esarbanis.roolr.value.Value;

/**
 * A node in a short circuit {@link Predicate} chain.
 * 
 * The evaluation of the next predicate is based on the evaluation of the current.
 * 
 * @see Join
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class Predicate {

  private final String fieldKey;
  private final Operator operator;
  private final Value value;
  private final Predicate next;
  private final Join join;

  /**
   * Constructs an immutable {@link Predicate} in a short circuit
   * predicate chain.
   * 
   * @param fieldKey The field, this predicate is about
   * @param operator The expression operator
   * @param value The value of the field as extracted from
   *        {@link EvaluationContext}
   * @param next The next {@link Predicate} to be evaluated
   * @param join The join type with the next Predicate
   */
  public Predicate(String fieldKey, Operator operator, Value value, Predicate next, Join join) {
    this.fieldKey = fieldKey;
    this.operator = operator;
    this.value = value;
    this.next = next;
    this.join = join;
  }

  /**
   * Evaluates whether the predicate holds for the given
   * {@link EvaluationContext}
   * 
   * @param context the context in which this predicate must hold or not
   * @return the result of the evaluation
   */
  public boolean evaluate(EvaluationContext context) {
    Value fieldValue = resolveValue(context.getField(fieldKey));
    boolean evaluation = operator.isValid(fieldValue, value);
    if (join == null) {
      return evaluation;
    }
    return join.shouldProceed(evaluation) ? next.evaluate(context) : evaluation;
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
}
