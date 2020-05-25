package com.esst.ts.annotate;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ConditionCheck {
    boolean user() default false;

    boolean token() default false;

    int role() default -1;
}