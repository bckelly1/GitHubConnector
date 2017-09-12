package com.kelbr09.tests;

public class PullRequest {
    private String url;
    private String title;

    public PullRequest(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
