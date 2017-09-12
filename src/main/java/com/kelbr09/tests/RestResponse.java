package com.kelbr09.tests;

/**
 * POJO of RestResponse object from REST API.
 * */

public class RestResponse {
    private int responseCode;
    private String responseBody;

    public RestResponse(final int responseCode, final String responseBody) {
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
