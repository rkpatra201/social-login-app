package com.java.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.errors.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toString( Object o) {
        try {
             return mapper.writeValueAsString(o);
        } catch (IOException e) {
            throw new AppException("error while converting object to string", e);
        }
    }
    public static void writeObject(HttpServletResponse response, Object o) {
        response.setContentType("application/json");
        String result = null;
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            response.getWriter().write(result);
        } catch (IOException e) {
            throw new AppException("error while writing response", e);
        }
    }

    public static <T> T readObject(HttpServletRequest request, Class<T> classType) {
        try {
            return mapper.readValue(request.getInputStream(), classType);
        } catch (IOException e) {
            throw new AppException("error while reading the body from stream", e);
        }
    }

    public static <T> T readFile(String filePath, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(new File(filePath), typeReference);
        } catch (IOException e) {
            throw new AppException("error while reading the body from stream", e);
        }
    }

    public static <T> T readString(String value, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(value, typeReference);
        } catch (IOException e) {
            throw new AppException("error while reading the body from stream", e);
        }
    }

    public static <T> T readObject(String filePath, Class<T> classType) {
        try {
            File file = new File(filePath);
            return mapper.readValue(file, classType);
        } catch (IOException e) {
            throw new AppException("error while reading the body from file", e);
        }
    }

    public static void writeObject(String filePath, Object o)
    {
        File file = new File(filePath);
        try {
            mapper.writeValue(file,o);
        } catch (IOException e) {
            throw new AppException("error while reading auth metadata file");
        }
    }

    public static ObjectMapper getMapper()
    {
        return mapper;
    }
}
