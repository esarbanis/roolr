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
 * A {@link java.lang.String} value wrapper used when evaluating
 * a {@link io.github.esarbanis.roolr.Predicate} expression.
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class StringValue implements Value {

  private static final long serialVersionUID = 6407589293683667471L;
  private final String value;

  /**
   * Creates a {@link io.github.esarbanis.roolr.value.StringValue} out of the given
   * {@link java.lang.String}
   *
   * @param value the {@link java.lang.String} to be wrapped
   */
  public StringValue(String value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(Value other) {
    if (StringValue.class.isInstance(other)) {
      return value.trim().contains(StringValue.class.cast(other).getValue().trim()) ? 0 : -1;
    } else if (StringArrayValue.class.isInstance(other)) {
      if (StringArrayValue.class.cast(other).getArray().contains(value.trim())) {
        return 0;
      }
      return -1;
    }

    throw new IllegalArgumentException(String.format("Cannot compare a StringValue with a %s",
        other.getClass().getName()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRepresentation() {
    return value;
  }

  public String getValue() {
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

    StringValue that = (StringValue) other;

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
