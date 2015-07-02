/*
 * The MIT License
 *
 * Copyright (c) 2014-2014 Efthymios Sarmpanis http://esarbanis.github.io/roolr
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

import static com.github.esarbanis.roolr.SchoolRules.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolRulesTest {

    public static final String STUDENT_OK = "STUDENT_OK";
    private Roolr<String> roolr;

    @Before
    public void setUp() throws Exception {
        List<Rule<String>> rules = new ArrayList<>();

        rules.add(theStudentIsOfSchoolAge());
        rules.add(theStudentFailForAverageGrade());
        rules.add(theStudentFailForAbsences());

        roolr = new Roolr<>(rules, STUDENT_OK);
    }

    @Test
    public void testStudentTooOld() throws Exception {
        Map<String, Object> fieldValues = Student.with().age(20.0).averageGrade(5.0).absences(0.0).thatsIt();

        assertEquals(NOT_IN_SCHOOL_AGE, roolr.decide(new SimpleEvaluationContext(fieldValues)));
    }
    
    @Test
    public void testStudentTooYoung() throws Exception {
        Map<String, Object> fieldValues = Student.with().age(5.0).averageGrade(5.0).thatsIt();

        assertEquals(NOT_IN_SCHOOL_AGE, roolr.decide(new SimpleEvaluationContext(fieldValues)));
    }
    
    @Test
    public void testStudentInSchoolAge() throws Exception {
        Map<String, Object> fieldValues = Student.with().age(10.0).averageGrade(5.0).absences(0.0).thatsIt();

        assertEquals(STUDENT_OK, roolr.decide(new SimpleEvaluationContext(fieldValues)));
    }
    
    @Test
    public void testStudentInFirstYear() throws Exception {
        Map<String, Object> fieldValues = Student.with().age(6.0).averageGrade(5.0).absences(0.0).thatsIt();

        assertEquals(STUDENT_OK, roolr.decide(new SimpleEvaluationContext(fieldValues)));
    }
    
    @Test
    public void testStudentInLastYear() throws Exception {
        Map<String, Object> fieldValues = Student.with().age(18.0).averageGrade(5.0).absences(0.0).thatsIt();

        assertEquals(STUDENT_OK, roolr.decide(new SimpleEvaluationContext(fieldValues)));
    }
    
    @Test
    public void testStudentHasFailingGrade() throws Exception {
        Map<String, Object> fieldValues = Student.with().age(18.0).averageGrade(4.0).thatsIt();

        assertEquals(FAILING_GRADE, roolr.decide(new SimpleEvaluationContext(fieldValues)));
    }
    
    @Test(expected = EvaluationException.class)
    public void testStudentIncompleteData() throws Exception {
        Map<String, Object> fieldValues = new HashMap<>();
        roolr.decide(new SimpleEvaluationContext(fieldValues));
    }
    
    @Test
    public void testStudentInvalidData() throws Exception {
        Map<String, Object> fieldValues = Student.with().averageGrade(4.0).thatsIt();
        fieldValues.put("student.age", "eighteen");
        try {
            roolr.decide(new SimpleEvaluationContext(fieldValues));
        } catch (EvaluationException e) {
            assertEquals("Cannot compare StringValue with NumberValue", e.getCause().getMessage());
        }
    }

    @Test
    public void testStudentFailingAbsences() throws Exception {
        Map<String, Object> fieldValues = Student.with().age(18.0).averageGrade(6.0).absences(31).thatsIt();

        assertEquals(FAILING_ABSENCES, roolr.decide(new SimpleEvaluationContext(fieldValues)));

    }
}
