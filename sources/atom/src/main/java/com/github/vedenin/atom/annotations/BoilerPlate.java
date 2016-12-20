package com.github.vedenin.atom.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * NOT NECESSARY ANNOTATION!
 *
 * Contract annotation for Atom class
 * This annotation can be used to describe BoilerPlate methods
 *
 * It's source annotation and can be using only for programmer (not for compiler)
 * Created by Slava Vedenin on 5/10/2016.
 */
@Target({ METHOD, CONSTRUCTOR})
@Retention(SOURCE)
public @interface BoilerPlate {
}
