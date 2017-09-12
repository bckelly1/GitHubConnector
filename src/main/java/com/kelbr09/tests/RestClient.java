package com.kelbr09.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient {
    public RestResponse restCall(String urlString){
        RestResponse restResponse = null;
        try{
            URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Rest Call Failed: HTTP error code: " + urlConnection.getResponseCode());
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));

            String output;
            while ((output = bufferedReader.readLine()) != null) {
                restResponse = new RestResponse(urlConnection.getResponseCode(), output);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return restResponse;
    }
}
