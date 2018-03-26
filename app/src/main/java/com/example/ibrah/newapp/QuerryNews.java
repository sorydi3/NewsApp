package com.example.ibrah.newapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrah on 18/07/2017.
 */

public class QuerryNews {
    private static String LOG_TAG = "QuerryUtils.class";

    /**
     * Query the USGS dataset and return a list of {} objects.
     */
    public static List<New> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);
        Log.i(LOG_TAG, " THE URL IS : " + url);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<New> News = extractFeatureFromJson(jsonResponse);
        // Log.i(LOG_TAG,"Object Earquaquake is : "+ News.get(1).getMagnitud());
        // Return the list of {@link Earthquake}s
        return News;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                Log.i(LOG_TAG, "show input stream" + inputStream);
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<New> extractFeatureFromJson(String NewsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(NewsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding News to
        List<New> News = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(NewsJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or News).
            JSONObject baseJsonResponseJ = baseJsonResponse.getJSONObject("response");
            JSONArray jsonArray = baseJsonResponseJ.getJSONArray("results");
            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.i(LOG_TAG, " INSIDE THE FOIR LOOP : " + i);

                // Get a single New at position i within the list of News
                JSONObject currentObjectInJasonArray = jsonArray.getJSONObject(i);


                ///////////////////////
                String title = null;
                if (currentObjectInJasonArray.has("webTitle")) {
                    title = currentObjectInJasonArray.getString("webTitle");
                }
                String section = null;
                if (currentObjectInJasonArray.has("sectionName")) {
                    section = currentObjectInJasonArray.getString("sectionName");
                }
                String date = null;
                if (currentObjectInJasonArray.has("webPublicationDate")) {
                    date = currentObjectInJasonArray.getString("webPublicationDate");
                }

                String link = null;
                if (currentObjectInJasonArray.has("webUrl")) {
                    link = currentObjectInJasonArray.getString("webUrl");
                }

                String author = null;
                if (currentObjectInJasonArray.has("author")) {
                    author = currentObjectInJasonArray.getString("author");
                }
                News.add(new New(title, author, date, section, link));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of News
        return News;
    }
}
