package com.example.samfisher.lifecycleaware.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Samfisher on 10/09/2017.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
@interface PerActivity {
}
