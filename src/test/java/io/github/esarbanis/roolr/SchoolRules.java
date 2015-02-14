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

package io.github.esarbanis.roolr;

import static io.github.esarbanis.roolr.Operator.GT;
import static io.github.esarbanis.roolr.Operator.LT;
import static io.github.esarbanis.roolr.Operator.NOT_EQUAL;
import static io.github.esarbanis.roolr.PredicateBuilder.when;
import static io.github.esarbanis.roolr.PredicateGroupBuilder.group;
import static io.github.esarbanis.roolr.value.Values.number;

/**
 * @author <a href="mailto:e.sarbanis@gmail.com">Efthymios Sarmpanis</a>
 */
public class SchoolRules {

    public static final String NOT_IN_SCHOOL_AGE = "notInSchoolAge";
    public static final String FAILING_GRADE = "FAILING_GRADE";
    public static final String FAILING_ABSENCES = "FAILING_ABSENCES";

    public static Rule<String> theStudentIsOfSchoolAge() {
        return new Rule<>(group(
                                when()
                                .field("student.age").is(LT).to(number(6.0))
                                .or()
                                .field("student.age").is(GT).to(number(18.0)).ok()
                                ).ok(),
                new StringOutcome(NOT_IN_SCHOOL_AGE));
        
    }
    
    public static Rule<String> theStudentFailForAverageGrade() {
        return new Rule<>(group(
                                when()
                                .field("student.averageGrade").is(LT).to(number(5.0))
                                .ok()
                                ).ok(),
                new StringOutcome(FAILING_GRADE));
        
    }
    
    public static Rule<String> theStudentFailForAbsences() {
        return new Rule<>(group(
                                when()
                                .field("student.absences").is(GT).to(number(30.0))
                                .ok()
                                ).ok(),
                new StringOutcome(FAILING_ABSENCES));
        
    }
}
