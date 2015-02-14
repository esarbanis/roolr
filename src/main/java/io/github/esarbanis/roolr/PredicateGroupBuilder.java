/*
 * The MIT License
 * 
 * Copyright (c) 2014-2015 Efthymios Sarmpanis http://esarbanis.github.io/roolr
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


import static io.github.esarbanis.roolr.Join.AND;
import static io.github.esarbanis.roolr.Join.OR;

import java.util.Stack;

/**
 * Use this class to build a {@link io.github.esarbanis.roolr.PredicateGroup} using a fluent api.
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class PredicateGroupBuilder {

  private final Stack<PredicateGroupObject> predicateGroupStack;
  private PredicateGroupObject current;

  private PredicateGroupBuilder(Predicate predicate) {
    predicateGroupStack = new Stack<>();
    current = new PredicateGroupObject(predicate);
  }

  public static PredicateGroupBuilder group(Predicate predicate) {
    return new PredicateGroupBuilder(predicate);
  }

  public PredicateGroupBuilder and(Predicate predicate) {
    current.join = AND;
    predicateGroupStack.push(current);
    current = new PredicateGroupObject(predicate);
    return this;
  }

  public PredicateGroupBuilder or(Predicate predicate) {
    current.join = OR;
    predicateGroupStack.push(current);
    current = new PredicateGroupObject(predicate);
    return this;
  }

  public PredicateGroup ok() {
    predicateGroupStack.push(current);

    PredicateGroup predicateGroup = null;
    PredicateGroup next = null;
    PredicateGroupObject predicateGroupObject;
    while (!predicateGroupStack.empty()) {
      predicateGroupObject = predicateGroupStack.pop();
      predicateGroup =
          new PredicateGroup(predicateGroupObject.predicate, next, predicateGroupObject.join);
      next = predicateGroup;
    }
    return predicateGroup;
  }

  private class PredicateGroupObject {

    private Predicate predicate;
    private Join join;

    public PredicateGroupObject(Predicate predicate) {
      this.predicate = predicate;
    }

  }
}
