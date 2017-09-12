package com.kelbr09.tests;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private String url;
    private String name;
    private List<PullRequest> pullRequestList = new ArrayList<>();

    public Repository(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public List<PullRequest> getPullRequestList() {
        return pullRequestList;
    }

    public void setPullRequestList(List<PullRequest> pullRequestList){
        this.pullRequestList = pullRequestList;
    }
}
