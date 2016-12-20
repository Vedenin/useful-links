package com.github.vedenin.atom.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for Atom class
 * This class is a Proxy/Facade pattern that have only minimal methods from original class
 * Using to light-reference one module to another (for example, for third party open-source libraries)
 *
 * Also Atom class may have Test and Contract annotation
 * Contract annotation - can be used to describe contract to this atom/proxy method
 *
 * Created by Slava Vedenin on 5/10/2016.
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface Atom {
    Class[] value() default {};
}
