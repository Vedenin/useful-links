package com.github.vedenin.atoms.io;

import com.github.vedenin.atom.annotations.AtomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/19/2016.
 */
@AtomUtils(FieldUtils.class)
public class ReflectUtilsAtom {
    public static List<Field> getAllFieldsList(final Class<?> cls) {
        return FieldUtils.getAllFieldsList(cls);
    }

}
