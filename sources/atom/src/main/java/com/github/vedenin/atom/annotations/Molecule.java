package com.github.vedenin.atom.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * NOT NECESSARY ANNOTATION!
 *
 * Molecule annotation for Atom class
 * This annotation can be used to describe that this Atom return other Atoms or methods this Atom using other Atom
 * as parameters
 *
 * Created by Slava Vedenin on 5/10/2016.
 */
@Target({ TYPE})
@Retention(SOURCE)
public @interface Molecule {
    Class[] value();
}
