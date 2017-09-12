package com.kelbr09.tests;


/**
 * POJO of Pull Request objects.
 * */

public class PullRequest {
    private String url;
    private String title;

    public PullRequest(final String url, final String title) {
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
