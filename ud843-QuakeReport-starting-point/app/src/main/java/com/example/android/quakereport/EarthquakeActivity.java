/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String USGS_REQUEST_URL ="http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        didTask task= new didTask();
        task.execute();}
        // Create a fake list of earthquake locations.

       /* earthquakes.add(new AndroidFlavor("7.1","San Francisco","Feb22016"));
        earthquakes.add(new AndroidFlavor("3.2","London","mar 3,2016"));
        earthquakes.add(new AndroidFlavor("1.1","Tokyo","oct 3,2017"));
        earthquakes.add(new AndroidFlavor("7.9","Mexico City","nov 4,2017"));
        earthquakes.add(new AndroidFlavor("9.9","Moscow","jun 7,2018"));
        earthquakes.add(new AndroidFlavor("3.8","Rio de Janeiro","jan 9,2013"));
        earthquakes.add(new AndroidFlavor("2.2","Paris","apl 8,2011"));*/

        // Find a reference to the {@link ListView} in the layout

        //ArrayList<AndroidFlavor> earthquakes=null;

        private void updateUI(ArrayList<AndroidFlavor>earthquakes) {
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            // Create a new {@link ArrayAdapter} of earthquakes
            AndroidFlavorAdapter flavorAdapter = new AndroidFlavorAdapter(this, earthquakes);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(flavorAdapter);
        }

        private class didTask extends AsyncTask<URL, Void, ArrayList<AndroidFlavor>> {

            @Override
            protected ArrayList<AndroidFlavor> doInBackground(URL... urls) {
                // Create URL object
                if (urls.length < 1 || urls[0] == null) {
                    return null;
                }
                URL url = createUrl(USGS_REQUEST_URL);

                // Perform HTTP request to the URL and receive a JSON response back
                String jsonResponse = null;
                try {
                    jsonResponse = makeHttpRequest(url);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing input stream", e);

                    e.printStackTrace();

                }

                // Extract relevant fields from the JSON response and create an {@link Event} object
                ArrayList<AndroidFlavor> earthquake = QueryUtils.extractEarthquakes(jsonResponse);

                // Return the {@link Event}
                return earthquake;
            }
            @Override
            protected void onPostExecute(ArrayList<AndroidFlavor> earthquake) {
                if (earthquake == null) {
                    return;
                }
               updateUI(earthquake);
            }

            private  URL createUrl(String stringUrl) {
                URL url = null;
                try {
                    url = new URL(stringUrl);
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, "Error with creating URL                              ", e);

                }
                return url;
            }

            /**
             * Make an HTTP request to the given URL and return a String as the response.
             */
            private  String makeHttpRequest(URL url) throws IOException {
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
                        jsonResponse = readFromStream(inputStream);
                    } else {
                        Log.e(LOG_TAG, "Error response code:                 " + urlConnection.getResponseCode());
                    }
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Problem retrieving the earthquake J  SON results.                     ", e);
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                return jsonResponse;
            }

            /**
             * Convert the {@link InputStream} into a String which contains the
             * whole JSON response from the server.
             */
            private  String readFromStream(InputStream inputStream) throws IOException {
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


        }

}
