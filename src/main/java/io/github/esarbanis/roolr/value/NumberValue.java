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

package io.github.esarbanis.roolr.value;

/**
 * A {@link java.lang.Double} value wrapper used when evaluating
 * a {@link io.github.esarbanis.roolr.Predicate} expression.
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class NumberValue implements Value {

  private static final long serialVersionUID = -9018375712460863787L;
  private final Double value;

  /**
   * Creates a {@link io.github.esarbanis.roolr.value.NumberValue} out of the given
   * {@link java.lang.Double}
   *
   * @param value the {@link java.lang.Double} to be wrapped
   */
  public NumberValue(Double value) {
    this.value = value;
  }

  public static NumberValue valueOf(Double value) {
    return new NumberValue(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(Value other) {
    if (NumberValue.class.isInstance(other)) {
      return Double.compare(value, NumberValue.class.cast(other).getValue());
    } else if (NumberArrayValue.class.isInstance(other)) {
      for (Double val : NumberArrayValue.class.cast(other).getArray()) {
        if (Double.compare(value, val) == 0) {
          return 0;
        }
      }
      return -1;
    }
    throw new IllegalArgumentException(String.format("Cannot compare a NumberValue with a %s",
        other.getClass().getName()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRepresentation() {
    return value.toString();
  }

  public Double getValue() {
    return value;
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

    NumberValue that = (NumberValue) other;

    return value.equals(that.value);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
