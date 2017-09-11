package com.kelbr09.tests;
/**
 *
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class GitHubConnector {
    private static void printJson(String jsonString){
        jsonString = jsonString.replace(",", ",\n");
        System.out.println(jsonString);
    }

    private static JSONObject parseJson(String jsonString){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private static void getAllRepos(JSONArray jsonArray){

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                System.out.println(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
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
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                jsonArray = new JSONArray(output);
                System.out.println("Number of repos: " + jsonArray.length());

                getAllRepos(jsonArray);
            }


            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}