package com.micaalle.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public class Response {

    public static ResponseEntity<HashMap<String, Object>> success() {
        return ResponseEntity.ok(new HashMap<>());
    }

    public static ResponseEntity<HashMap<String, Object>> success(Map<String, Object> body) {
        return ResponseEntity.ok(new HashMap<>(body));
    }

    public static Map<String, Object> createBody(String key, Object value) {
        Map<String, Object> body = new HashMap<>();
        body.put(key, value);
        return body;
    }
}
