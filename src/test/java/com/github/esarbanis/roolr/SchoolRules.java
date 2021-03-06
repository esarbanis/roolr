/*
 * The MIT License
 *
 * Copyright (c) 2014-2015 Efthymios Sarmpanis http://esarbanis.github.io/roolr
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.esarbanis.roolr;

import com.github.esarbanis.roolr.expression.Expression;
import com.github.esarbanis.roolr.expression.ExpressionBuilder;
import com.github.esarbanis.roolr.value.Values;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class SchoolRules {

    public static final String NOT_IN_SCHOOL_AGE = "notInSchoolAge";
    public static final String FAILING_GRADE = "FAILING_GRADE";
    public static final String FAILING_ABSENCES = "FAILING_ABSENCES";

    public static Rule<String> theStudentIsOfSchoolAge() throws NoSuchMethodException {
        return toRule(ExpressionBuilder.when()
                        .field("student.age").lessThan(Values.number(6.0))
                        .or()
                        .field("student.age").greaterThan(Values.number(18.0)).ok(),
                NOT_IN_SCHOOL_AGE);
        
    }

    public static Rule<String> theStudentFailForAverageGrade() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return toRule(ExpressionBuilder.when()
                        .field("student.averageGrade").lessThan(Values.number(5.0)).ok(),
                        FAILING_GRADE);

    }

    public static Rule<String> theStudentFailForAbsences() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return toRule(ExpressionBuilder.when()
                        .field("student.absences").greaterThan(Values.number(30.0)).ok(),
                        FAILING_ABSENCES);

    }

    private static Rule<String> toRule(Expression expression, String outcome) {
        return new Rule<>(expression, outcome);
    }
}
