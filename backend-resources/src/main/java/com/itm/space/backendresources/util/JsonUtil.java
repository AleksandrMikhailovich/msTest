package com.itm.space.backendresources.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static <T> T getObjectFromJson(String filePath, Class<T> clazz) {
        try (InputStream stream = getInputStream(filePath)) {
            return objectMapper.readValue(stream, clazz);
        }
    }

    @SneakyThrows
    public static <T> List<T> getListFromJson(String filePath, Class<T> clazz) {
        try (InputStream stream = getInputStream(filePath)) {
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return objectMapper.readValue(stream, collectionType);
        }
    }

    @SneakyThrows
    public static <T> T getObjectFromJson(Resource resource, Class<T> clazz) {
        try (InputStream stream = resource.getInputStream()) {
            return objectMapper.readValue(stream, clazz);
        }
    }

    @SneakyThrows
    public static <T> List<T> getListFromJson(Resource resource, Class<T> clazz) {
        try (InputStream stream = resource.getInputStream()) {
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return objectMapper.readValue(stream, collectionType);
        }
    }

    @SneakyThrows
    public static String toJsonString(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public static String getStringFromJson(String filePath) {
        try (InputStream stream = getInputStream(filePath)) {
            return new String(stream.readAllBytes());
        }
    }

    private static InputStream getInputStream(String filePath) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        return classLoader.getResourceAsStream(filePath);
    }
}
