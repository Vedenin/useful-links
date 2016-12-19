package com.github.vedenin.thirdpartylib.io;

import com.github.vedenin.thirdpartylib.annotations.AtomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by vvedenin on 12/19/2016.
 */
@AtomUtils
public class ReflectUtilsAtom {
    public static List<Field> getAllFieldsList(final Class<?> cls) {
        return FieldUtils.getAllFieldsList(cls);
    }

}
