package com.gurpster.facapemobile.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Williaan Lopes (d3x773r) on 29/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped {
}
