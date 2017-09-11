package com.kelbr09.tests;
/**
 *
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class GitHubConnector {
    private static void printJson(String jsonString){
        jsonString = jsonString.replace(",", ",\n");
        System.out.println(jsonString);
    }

    private JSONObject parseJson(String jsonString){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private List<JSONObject> getAllRepos(JSONArray jsonArray){
        List<JSONObject> repoArray = new ArrayList<JSONObject>();
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                repoArray.add(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return repoArray;
    }

    private List<JSONObject> getAllPullRequests(String repoName){
        List<JSONObject> jsonObjects = new ArrayList<>();

        String urlString = "https://api.github.com/repos/bckelly1/" + repoName + "/pulls";
        try {
            URL url = new URL(urlString);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            JSONArray jsonArray = null;
            while ((output = br.readLine()) != null) {
                jsonArray = new JSONArray(output);
            }

            if(jsonArray != null){
                for(int i = 0; i < jsonArray.length(); i++){
                    jsonObjects.add((JSONObject) jsonArray.get(i));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonObjects;
    }

    public static void main(String[] args) {
        GitHubConnector gitHubConnector = new GitHubConnector();
        try {
            URL url = new URL("https://api.github.com/users/bckelly1/repos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            JSONArray jsonArray;
            List<JSONObject> allRepos = new ArrayList<JSONObject>();
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                jsonArray = new JSONArray(output);

                allRepos = gitHubConnector.getAllRepos(jsonArray);
            }

            System.out.println("bckelly1 Repos: " + allRepos.size());
            for(JSONObject jsonObject : allRepos){
                System.out.print("\t|-- " + jsonObject.get("name"));

                List<JSONObject> pullRequests = gitHubConnector.getAllPullRequests(jsonObject.getString("name"));
                System.out.println(" - " + pullRequests.size());
                for(JSONObject pullRequest : pullRequests){
                    System.out.println("\t\t |-- " + pullRequest.getString("name"));
                    System.out.println("\t\t\t " + pullRequest.getString("url"));
                }
            }
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}