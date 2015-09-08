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

import java.util.Collection;


/**
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class Roolr<T> {

  private final Collection<Rule<T>> rules;
  private final T defaultOutcome;

  public Roolr(Collection<Rule<T>> rules, T defaultOutcome) {
    this.rules = rules;
    this.defaultOutcome = defaultOutcome;
  }

  /**
   * Evaluates the {@link Rule} object list and resolves a predefined
   * type.
   * <p>
   * If the evaluation fails an {@link EvaluationException} will be
   * thrown.
   * </p>
   *
   * @param context the {@link EvaluationContext context} of the
   *        evaluation.
   * @return the resolved type of the rule list evaluation.
   * @throws EvaluationException Thrown when the evaluation has failed, wrapping the original exception.
   */
  public T decide(EvaluationContext context) throws EvaluationException {
    try {
        return rules
                .stream()
                .map((Rule<T> rule) -> rule.apply(context))
                .filter((T t) -> t != null)
                .findFirst()
                .orElse(defaultOutcome);
    } catch (Exception e) {
        throw new EvaluationException("Cannot make a decision. There was an error.", e);
    }
  }
}
