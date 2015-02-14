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


import com.github.esarbanis.roolr.value.Value;

/**
 * Provides Conditional Operators for the Predicate class.
 *
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public enum Operator {

  /**
   * Tests whether two expressions are equal
   */
  EQUAL,

  /**
   * Tests whether two expressions are not equal
   */
  NOT_EQUAL,

  /**
   * Tests whether the first expression is greater to the second expression
   */
  GT,

  /**
   * Tests whether the first expression is greater to or equal to the second expression
   */
  GE,

  /**
   * Tests whether the first expression is less to the second expression
   */
  LT,

  /**
   * Tests whether the first expression is less to or equals to the second expression
   */
  LE,

  /**
   * Tests whether the first expression is included in the second expression
   */
  IN,

  /**
   * Tests whether the expression matches the second expression, for a given pattern
   */
  LIKE;

  /**
   * It makes the actual {@link Predicate} or
   * {@link PredicateGroup} evaluation.
   * <p/>
   * May throw an {@link IllegalStateException} for unknown
   * {@link Operator} objects. This should never happen.
   * 
   * @param left the value on the left side of the operator
   * @param right the value on the right side of the operator
   * @return true if the expression holds, false otherwise
   */
  public boolean isValid(Value left, Value right) {
    if (!left.getClass().equals(right.getClass())) {
      throw new IllegalArgumentException(String.format("Cannot compare %s with %s", left.getClass()
          .getSimpleName(), right.getClass().getSimpleName()));
    }
    switch (this) {
      case IN:
      case LIKE:
      case EQUAL:
        return left.compareTo(right) == 0;
      case NOT_EQUAL:
        return left.compareTo(right) != 0;
      case GT:
        return left.compareTo(right) > 0;
      case GE:
        return left.compareTo(right) >= 0;
      case LT:
        return left.compareTo(right) < 0;
      case LE:
        return left.compareTo(right) <= 0;
      default:
        throw new IllegalStateException(String.format(
            "DracoPredicateOperator %s is not included in the evaluation switch!", this.name()));
    }
  }

}
