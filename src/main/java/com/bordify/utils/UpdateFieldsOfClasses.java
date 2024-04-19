package com.bordify.utils;

import java.lang.reflect.Field;

public class UpdateFieldsOfClasses{
    public static void updateFields(Object obj, Object obj2) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj2);
                if (value != null) {
                    field.set(obj, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}