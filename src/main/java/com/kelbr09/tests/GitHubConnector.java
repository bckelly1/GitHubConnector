package com.kelbr09.tests;
/**
 *
 * */

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class GitHubConnector {
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final String NAME = "url";


    private List<Repository> getAllRepos(String username){
        List<Repository> repoArray = new ArrayList<>();

        String repositoryUrl = "https://api.github.com/users/bckelly1/repos";
        try{
            RestResponse restResponse = new RestClient().restCall(repositoryUrl);

            if(restResponse.getResponseCode() == HttpURLConnection.HTTP_OK){
                JSONArray jsonArray = new JSONArray(restResponse.getResponseBody());

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String name = jsonObject.get(NAME) + "";
                    String url = jsonObject.get(URL) + "";

                    repoArray.add(new Repository(url, name));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return repoArray;
    }

    private List<PullRequest> getAllPullRequests(String repoName){

        List<PullRequest> jsonObjects = new ArrayList<>();

        String pullRequestUrl = "https://api.github.com/repos/bckelly1/" + repoName + "/pulls";
        try {
            RestResponse restResponse = new RestClient().restCall(pullRequestUrl);

            if(restResponse.getResponseCode() == HttpURLConnection.HTTP_OK){
                JSONArray jsonArray = new JSONArray(restResponse.getResponseBody());

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    String title = jsonObject.getString(TITLE);
                    String url = jsonObject.getString(URL);
                    jsonObjects.add(new PullRequest(url, title));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObjects;
    }

    public static void main(String[] args) {
        final String username = "bckelly1";

        GitHubConnector gitHubConnector = new GitHubConnector();
        List<Repository> allRepositories = gitHubConnector.getAllRepos(username);

        for(Repository repository : allRepositories){
            System.out.println("polling repo: " + repository.getName());
            System.out.println("polling repo url: " + repository.getUrl());
            List<PullRequest> pullRequests = gitHubConnector.getAllPullRequests(repository.getName());
            repository.setPullRequestList(pullRequests);
        }


        System.out.println(username + "'s Repositories: " + allRepositories.size());
        for(Repository repository : allRepositories){
            System.out.print("\t|-- " + repository.getName());
            for(PullRequest pullRequest : repository.getPullRequestList()){
                System.out.println("\t\t |-- " + pullRequest.getTitle());
                System.out.println("\t\t\t " + pullRequest.getUrl());

            }
        }
    }
}