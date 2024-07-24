package com.example.finance_tracker.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj, Object errorResponseObj, String functionName) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("success", status.is2xxSuccessful());
        map.put("status", status.value());
        map.put("message", message);
        map.put("service", functionName);
        map.put("errors", errorResponseObj);
        map.put("payload", responseObj);

        return new ResponseEntity<>(map, status);
    }
}
