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

import com.github.esarbanis.roolr.expression.Expression;

/**
 * Rule contains an {@link Expression} to evaluate
 * against the provided {@link EvaluationContext}.
 * <p/>
 * If the evaluation passes the provided {@link Outcome} will be returned.
 * 
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class Rule<T> {

  private final Expression ruleExpression;
  private final Outcome<T> outcome;

  /**
   * Constructs a {@link Rule} with a given
   * {@link Expression} chain and a given
   * {@link Outcome}
   * 
   * @param ruleExpression A {@link Expression} to be evaluated
   * @param outcome THe {@link Outcome} to be returned if this rule holds.
   */
  public Rule(Expression ruleExpression, Outcome<T> outcome) {
    this.ruleExpression = ruleExpression;
    this.outcome = outcome;
  }

  /**
   * Will run the {@link EvaluationContext} against the
   * {@link Expression} and will return the provided
   * {@link Outcome} in case it's evaluated successfully.
   * 
   * @param context The context to be evaluated
   * @return The outcome of the evaluation
   */
  public Outcome<T> apply(EvaluationContext context) {
    if (ruleExpression.evaluate(context)) {
      return outcome;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    Rule that = (Rule) other;

    return outcome.equals(that.outcome) && ruleExpression.equals(that.ruleExpression);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int result = ruleExpression.hashCode();
    result = 31 * result + outcome.hashCode();
    return result;
  }

}
