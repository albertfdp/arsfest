/*******************************************************************************
 * Copyright 2013 Albert Fern�ndez de la Pe�a
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package dk.dtu.arsfest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.parser.JsonParser;
import dk.dtu.arsfest.view.EventAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.app.Activity;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ArrayList<Location> locations;
	private EventAdapter eventAdapter;
	private ListView eventListView;
	private ArrayList<Event> events;
	private ViewPager viewPager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		// Read from data.JSON
		readJson();
		
		Log.i("ARSFEST", "List of events");
		if (this.events != null) {
			for (Event event : this.events) {
				Log.i("ARSFEST", event.getName());
			}
		} else {
			Log.i("ARSFEST", "Events null!!");
		}
			
		// inflate the list view with the events
		inflateView();
		
	}
	
	private void readJson() {
			
		JsonParser jsonParser;
		try {
			InputStream is = getAssets().open("data.JSON");
			jsonParser = new JsonParser(is);
			this.locations = jsonParser.readLocations();
			this.events = jsonParser.readEvents();
		} catch (IOException e) {
			Log.i("ARSFEST", e.getMessage());
		}
		
	}
	
	private void inflateView() {
		// check if the list of locations and events are not null
		if (this.locations != null && this.events != null) {
			eventAdapter = new EventAdapter(this, R.layout.event_row_item, this.events);
			eventListView = (ListView) findViewById(R.id.eventsListView);
			eventListView.setAdapter(eventAdapter);
		}
	}
	
	
	
}
