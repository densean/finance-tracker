package com.example.finance_tracker.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseMapping {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj, Object errorResponseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("success", status.is2xxSuccessful());
        map.put("status", status.value());
        map.put("errors", errorResponseObj);
        map.put("payload", responseObj);
        return new ResponseEntity<Object>(map, status);
    }
}
