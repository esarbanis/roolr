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

/**
 * It is used to group a {@link Predicate} chain and join it with another.
 * It forms its own short circuit {@link PredicateGroup} chain.
 *
 * The evaluation of the next predicate group is based on the evaluation of the current.
 * 
 * @see Join
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class PredicateGroup {

  private final Predicate predicate;
  private final PredicateGroup next;
  private final Join join;

  /**
   * Constructs an immutable {@link Predicate} in a short circuit
   * predicate chain.
   *
   * @param next The next {@link Predicate} to be evaluated
   * @param join The join type with the next Predicate
   */
  public PredicateGroup(Predicate predicate, PredicateGroup next, Join join) {
    this.predicate = predicate;
    this.next = next;
    this.join = join;
  }

  /**
   * Evaluates whether the predicate group holds for the given
   * {@link EvaluationContext}
   *
   * @param context the context in which this predicate must hold or not
   * @return the result of the evaluation
   */
  public boolean evaluate(EvaluationContext context) {
    boolean evaluation = predicate.evaluate(context);
    if (join == null) {
      return evaluation;
    }
    return join.shouldProceed(evaluation) ? next.evaluate(context) : evaluation;
  }
}
