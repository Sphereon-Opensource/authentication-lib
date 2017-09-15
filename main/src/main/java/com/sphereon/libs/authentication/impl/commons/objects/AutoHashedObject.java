package com.sphereon.libs.authentication.impl.commons.objects;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
public abstract class AutoHashedObject implements Serializable {

    @Override
    public final int hashCode() {
        return Arrays.deepHashCode(collectFieldValues(this));
    }


    @Override
    public final boolean equals(Object obj) {
        return Arrays.deepEquals(collectFieldValues(this), collectFieldValues(obj));
    }


    private Object[] collectFieldValues(Object object) {
        List<Object> fieldValues = new ArrayList<>();
        if (object != null) {
            FieldUtils.getAllFieldsList(object.getClass()).forEach(field -> {
                if (!Modifier.isTransient(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                    try {
                        fieldValues.add(FieldUtils.readField(field, object, true));
                    } catch (IllegalAccessException ignored) {
                    }
                }
            });
        }
        return fieldValues.toArray();
    }
}
