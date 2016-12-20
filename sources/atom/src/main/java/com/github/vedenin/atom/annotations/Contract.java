package com.github.vedenin.atom.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * NOT NECESSARY ANNOTATION!
 *
 * Contract annotation for Atom class
 * This annotation can be used to describe contract to this atom/proxy method
 * Using just for describe developer how want to use another libraries/class/method
 *
 * Created by Slava Vedenin on 5/10/2016.
 */
@Target({ TYPE, METHOD, CONSTRUCTOR})
@Retention(SOURCE)
public @interface Contract {
    String value();
}
