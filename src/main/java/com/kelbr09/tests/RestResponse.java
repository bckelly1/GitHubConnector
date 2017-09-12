package com.kelbr09.tests;

public class RestResponse {
    private int responseCode;
    private String responseBody;

    public RestResponse(int responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
