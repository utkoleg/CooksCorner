package com.example.cookscorner.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ObjectMapperUtils {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> fromObjectToMap(Object object) {
        return objectMapper.convertValue(object, new TypeReference<>() {
        });
    }

    public static String getJson(Map<String, Object> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
