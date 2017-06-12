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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        ArrayList<AndroidFlavor> earthquakes =  QueryUtils.extractEarthquakes();
       /* earthquakes.add(new AndroidFlavor("7.1","San Francisco","Feb22016"));
        earthquakes.add(new AndroidFlavor("3.2","London","mar 3,2016"));
        earthquakes.add(new AndroidFlavor("1.1","Tokyo","oct 3,2017"));
        earthquakes.add(new AndroidFlavor("7.9","Mexico City","nov 4,2017"));
        earthquakes.add(new AndroidFlavor("9.9","Moscow","jun 7,2018"));
        earthquakes.add(new AndroidFlavor("3.8","Rio de Janeiro","jan 9,2013"));
        earthquakes.add(new AndroidFlavor("2.2","Paris","apl 8,2011"));*/

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        AndroidFlavorAdapter flavorAdapter = new AndroidFlavorAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(flavorAdapter);
    }
}
