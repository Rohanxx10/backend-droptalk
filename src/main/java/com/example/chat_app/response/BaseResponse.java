package com.example.chat_app.response;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse {

    private int statusCode;
    private String description;
    private Object data;
    private Map<String, Object> additionalData;

    // ✅ Default constructor
    public BaseResponse() {
        this.additionalData = new HashMap<>();
    }

    // ✅ Status only
    public BaseResponse(int statusCode) {
        this.statusCode = statusCode;
        this.additionalData = new HashMap<>();
    }

    // ✅ Status + message
    public BaseResponse(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
        this.additionalData = new HashMap<>();
    }

    // ✅ Status + message + data (MOST IMPORTANT)
    public BaseResponse(int statusCode, String description, Object data) {
        this.statusCode = statusCode;
        this.description = description;
        this.data = data != null ? data : Map.of(); // ✅ default value
        this.additionalData = new HashMap<>();
    }

    // getters & setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getData() {
        return data == null ? Map.of() : data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalData() {
        return additionalData == null ? new HashMap<>() : additionalData;
    }

    public void setAdditionalData(Map<String, Object> additionalData) {
        this.additionalData = additionalData;
    }
}
