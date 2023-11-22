package com.cda.utils.ObjectToMapConverter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMapConverter {
    public static Map<String, Object> convertObjectToMap(Object obj){
        Map<String, Object> data = new HashMap<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            String fieldName = field.getName();
            try{
                Object value = field.get(obj);
                data.put(fieldName, value);
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return data;
    }
}
