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

package io.github.esarbanis.roolr;

/**
 * Joins two predicates.
 * <p/>
 * Should be used to chain predicates together to form a rule.
 *
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public enum Join {

  /**
   * Joins two Predicates.
   * <p/>
   * Should break the predicate chain with <code>false</code>, if the left side is false.
   */
  AND,

  /**
   * Joins two Predicates.
   * <p/>
   * Should break the predicate chain with <code>true</code>, if the left side is false.
   */
  OR;

  /**
   * Short circuit mechanism.
   * <p/>
   * Makes the decision to move to the next evaluation or not, based on the following matrix:
   * <table>
   *     <thead>
   *         <tr>
   *             <td>Previous Evaluation\Join Type</td>
   *             <td>AND</td>
   *             <td>OR</td>             
   *         </tr>     
   *     </thead>
   *     <tbody>
   *         <tr>
   *             <td>true</td>
   *             <td>true</td>             
   *             <td>false</td>             
   *         </tr>
   *         <tr>
   *             <td>false</td>
   *             <td>false</td>             
   *             <td>true</td>             
   *         </tr>     
   *     </tbody>     
   * </table> 
   * @param previousEvaluation the evaluation of the previous predicate
   * @return the short circuit decision
   */
  public boolean shouldProceed(boolean previousEvaluation) {
    switch (this) {
      case AND:
        return previousEvaluation;
      case OR:
        return !previousEvaluation;
      default:
        throw new IllegalStateException(String.format(
            "Join %s is not included in the evaluation switch!", this.name()));
    }
  }
}
