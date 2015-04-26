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

package com.github.esarbanis.roolr.value;

/**
 * A {@link java.lang.Boolean} value wrapper used when evaluating a
 * {@link com.github.esarbanis.roolr.expression.Expression expression}.
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class BooleanValue implements Value {

  private static final long serialVersionUID = 3178603278934065685L;
  private final Boolean value;

  /**
   * Creates a {@link BooleanValue} out of the given
   * {@link Boolean}
   *
   * @param value the {@link java.lang.String}s to be wrapped
   */
  public BooleanValue(Boolean value) {
    this.value = value;
  }

  public Boolean getValue() {
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(Value other) {
    if (!BooleanValue.class.isInstance(other)) {
      throw new IllegalArgumentException(String.format("Cannot compare a BooleanValue with a %s",
          other.getClass().getName()));
    }
    return Boolean.compare(value, BooleanValue.class.cast(other).getValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRepresentation() {
    return value.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    BooleanValue that = (BooleanValue) other;

    return value.equals(that.value);

  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
