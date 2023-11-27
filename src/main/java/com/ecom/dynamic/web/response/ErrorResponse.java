package com.ecom.dynamic.web.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private boolean success;
    private String error;

    public ErrorResponse(String error) {
        this.success = false;
        this.error = error;
    }
}

