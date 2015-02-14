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

package com.github.esarbanis.roolr;

import static com.github.esarbanis.roolr.Join.AND;
import static com.github.esarbanis.roolr.Join.OR;

import com.github.esarbanis.roolr.value.Value;

import java.util.Stack;

/**
 * Use this class to build a {@link Predicate} using a fluent api.
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class PredicateBuilder {

  private final Stack<PredicateObject> predicateStack;

  private PredicateObject current;

  private PredicateBuilder() {
    predicateStack = new Stack<>();
    current = new PredicateObject();
  }

  public static PredicateBuilder when() {
    return new PredicateBuilder();
  }

  public PredicateBuilder field(String fieldName) {
    current.fieldName = fieldName;
    return this;
  }

  public PredicateBuilder is(Operator operator) {
    current.operator = operator;
    return this;
  }

  public PredicateBuilder to(Value value) {
    current.value = value;
    return this;
  }

  public PredicateBuilder and() {
    current.join = AND;
    predicateStack.push(current);
    current = new PredicateObject();
    return this;
  }

  public PredicateBuilder or() {
    current.join = OR;
    predicateStack.push(current);
    current = new PredicateObject();
    return this;
  }

  public Predicate ok() {
    predicateStack.push(current);
    Predicate predicate = null;
    Predicate next = null;
    PredicateObject predicateObject = null;
    while (!predicateStack.empty()) {
      predicateObject = predicateStack.pop();
      predicate =
          new Predicate(predicateObject.fieldName, predicateObject.operator, predicateObject.value,
              next, predicateObject.join);
      next = predicate;
    }
    return predicate;
  }

  private class PredicateObject {
    private String fieldName;
    private Operator operator;
    private Value value;
    private Join join;

  }
}
