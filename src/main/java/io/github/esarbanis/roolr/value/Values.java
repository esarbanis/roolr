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

package io.github.esarbanis.roolr.value;

import java.util.Arrays;

/**
 * Utility class to construct {@link io.github.esarbanis.roolr.value.Value} instances
 * 
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class Values {

  private Values() {}

  /**
   * Creates a {@link io.github.esarbanis.roolr.value.StringValue} out of the given
   * {@link java.lang.String}
   * 
   * @param string the {@link java.lang.String} to be wrapped
   * @return the wrapped {@link java.lang.String}
   */
  public static StringValue string(String string) {
    return new StringValue(string);
  }

  /**
   * Creates a {@link io.github.esarbanis.roolr.value.StringArrayValue} out of the given
   * {@link String}s
   *
   * @param string the {@link java.lang.String}s to be wrapped
   * @return the wrapped {@link java.lang.String}s
   */
  public static StringArrayValue strings(String... string) {
    return new StringArrayValue(Arrays.asList(string));
  }

  /**
   * Creates a {@link io.github.esarbanis.roolr.value.NumberValue} out of the given
   * {@link java.lang.Double}
   * 
   * @param number the {@link java.lang.Double} to be wrapped
   * @return the wrapped {@link java.lang.Double}
   */
  public static NumberValue number(Double number) {
    return new NumberValue(number);
  }

  /**
   * Creates a {@link io.github.esarbanis.roolr.value.NumberArrayValue} out of the given
   * {@link java.lang.Double}s
   *
   * @param number the {@link java.lang.Double}s to be wrapped
   * @return the wrapped {@link java.lang.Double}s
   */
  public static NumberArrayValue numberArray(Double... number) {
    return new NumberArrayValue(Arrays.asList(number));
  }
}
