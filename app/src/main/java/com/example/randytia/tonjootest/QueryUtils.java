package com.example.randytia.tonjootest;

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
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Randytia on 08/08/2017.
 */

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    private static ArrayList<Tonjoo> extractTonjoo(String tonjooJSON) {

        if (TextUtils.isEmpty(tonjooJSON)) {
            return null;
        }

        ArrayList<Tonjoo> tonjoos = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(tonjooJSON);
            JSONArray features = jsonObj.getJSONArray("data");

            for (int i = 0; i < features.length(); i++) {
                JSONObject tonjoo = features.getJSONObject(i);

                String firstName = tonjoo.getString("first_name");
                String lastName = tonjoo.getString("last_name");
                String gender = tonjoo.getString("gender");
                String email = tonjoo.getString("email");
                String avatar = tonjoo.getString("avatar");

                tonjoos.add(new Tonjoo(firstName, lastName, gender, email, avatar));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the tonjoo JSON results", e);
        }
        return tonjoos;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

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

    public static List<Tonjoo> fetchTonjooData(String requestUrl) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Tonjoo> tonjoo = extractTonjoo(jsonResponse);

        // Return the {@link Event}
        return tonjoo;
    }
}
