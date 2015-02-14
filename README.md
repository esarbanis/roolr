Roolr
=========

Roolr is a very simple and lightweight business rule engine, with no dependencies, designed to be 
easily embedded in any application (mobile, web, desktop) which needs to enforce complex business 
rules.

Example
-------

The project's [test case](src/test/java/io/github/esarbanis/roolr/SchoolRulesTest.java) is of a real
world domain and can be used as a reference of its use.

For example, say you develop an application for a school and you want to make sure that only 
students of the correct age are enrolled. You can construct the following roolr engine.
 
```java
StringOutcome defaultOutcome = new StringOutcome(STUDENT_OK);
List<Rule<String>> rules = new ArrayList<>();
Rule theStudentIsOfSchoolAgeRule = new Rule<>(group(
                                                    when()
                                                    .field("student.age").is(LT).to(number(6.0))
                                                    .or()
                                                    .field("student.age").is(GT).to(number(18.0)).ok()
                                                    ).ok(),
                                              new StringOutcome(NOT_IN_SCHOOL_AGE));
rules.add(theStudentIsOfSchoolAgeRule);
Roolr roolr = new Roolr<>(rules, defaultOutcome);
```

When a student of age 20 tries to enroll then roolr will decide a NOT_IN_SCHOOL_AGE outcome

```java
Map<String, Object> fieldValues = Student.with().age(20.0).thatsIt();

assertEquals(NOT_IN_SCHOOL_AGE, roolr.decide(new SimpleEvaluationContext(fieldValues)).getOutput());
```

Evaluation Contexts
-------------------

Right now only `SimpleEvaluationContext` is provided by roolr, that takes field/value pairs as a `Map`.

But you can always use your own implementation of `EvaluationContext` interface.

Outcomes
--------

Right now only `StringOutcome` is provided by roolr, that provides decisions in `String`.

But you can always use your own implementation of `Outcome` interface.