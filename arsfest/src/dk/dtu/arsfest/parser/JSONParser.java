package dk.dtu.arsfest.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import dk.dtu.arsfest.model.Bssid;
import dk.dtu.arsfest.model.Course;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

import android.util.Log;

public class JSONParser {
	
	private String json = "";
	private JSONObject jObj = null;
	private JSONArray jsonLocations;
	
	public JSONParser (InputStream is) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String readLine = null;
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			// while the BufferedReader readLine is not null
			while ((readLine = br.readLine()) != null) {
				stringBuilder.append(readLine + "\n");
			}
			is.close();
			json = stringBuilder.toString();
		} catch (Exception e) {
			Log.e(Constants.TAG, "Error converting result to " + e.toString());
		}
		
		// try parsing the string to an object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e(Constants.TAG, "Error parsing data " + e.toString());
		}
		
	}
	
	/*
	 * Read data.JSON and stores the information of each location 
	 * @Return ArrayList<Locations>
	 */
	public ArrayList<Location> readLocations() {
		ArrayList<Location> locations = new ArrayList<Location>();
		
		try {
			// get array of locations
			jsonLocations = jObj.getJSONArray(Constants.JSON_TAG_LOCATIONS);
			
			for (int i = 0; i < jsonLocations.length(); i++) {
				JSONObject location = jsonLocations.getJSONObject(i);
				
				String id = location.getString(Constants.JSON_TAG_LOCATION_ID);
				String name = location.getString(Constants.JSON_TAG_LOCATION_NAME);
				Double latitude = location.getDouble(Constants.JSON_TAG_LOCATION_LATITUDE);
				Double longitude = location.getDouble(Constants.JSON_TAG_LOCATION_LONGITUDE);
				String description = location.getString(Constants.JSON_TAG_LOCATION_DESCRIPTION);
				
				// get all locations of the events
				ArrayList<Event> events = readEvents(location.getJSONArray(Constants.JSON_TAG_LOCATION_EVENTS));
				locations.add(new Location(id, name, latitude, longitude, description, events));
			}
			
		} catch (JSONException e) {
			Log.e(Constants.TAG, "Error parsing data " + e.toString());
			e.printStackTrace();
		}
		return locations;
	}
	
	public ArrayList<Event> readEvents(JSONArray jsonEvents) throws JSONException {
		ArrayList<Event> events = new ArrayList<Event>();
		for (int i = 0; i < jsonEvents.length(); i++) {
			JSONObject event = jsonEvents.getJSONObject(i);
			
			String id = event.getString(Constants.JSON_TAG_EVENT_ID);
			String name = event.getString(Constants.JSON_TAG_EVENT_NAME);
			String image = event.getString(Constants.JSON_TAG_EVENT_IMAGE);
			String startTime = event.getString(Constants.JSON_TAG_EVENT_START_TIME);
			String endTime = event.getString(Constants.JSON_TAG_EVENT_END_TIME);
			String description = event.getString(Constants.JSON_TAG_EVENT_DESCRIPTION);
			String location = event.getString(Constants.JSON_TAG_EVENT_LOCATION);
			Date startTimeDate = Utils.getFormattedDate(startTime);
			Date endTimeDate = Utils.getFormattedDate(endTime);
			String theme = event.getString(Constants.JSON_TAG_EVENT_THEME);
			String type = event.getString(Constants.JSON_TAG_EVENT_TYPE);
			boolean remark = false;
			if (event.has(Constants.JSON_TAG_EVENT_REMARK))
				remark = event.getBoolean(Constants.JSON_TAG_EVENT_REMARK);
			ArrayList<Course> dinnerMenu = null;
			if(name.equals(Constants.JSON_TAG_MENU_DINNER)){
				dinnerMenu = new ArrayList<Course>();	
				JSONArray jsonMenu = event.getJSONArray(Constants.JSON_TAG_EVENT_MENU);
				for (int j = 0; j < jsonMenu.length(); j++){
					JSONObject menu = jsonMenu.getJSONObject(j);
					String course = menu.getString(Constants.JSON_TAG_MENU_COURSE);
					String course_name = menu.getString(Constants.JSON_TAG_MENU_COURSE_NAME);
					String course_description = menu.getString(Constants.JSON_TAG_MENU_COURSE_DESCRIPTION);
					
					dinnerMenu.add(new Course(course,course_name,course_description));
				}
				
				
			}

			events.add(new Event(id, name, image, startTimeDate, endTimeDate, location, description, type, theme,dinnerMenu, remark));
		}
		return events;
	}
	
	public ArrayList<Bssid> readBssid() {
		ArrayList<Bssid> bssids = new ArrayList<Bssid>();
		try {
			JSONArray jsonBssids = jObj.getJSONArray(Constants.JSON_TAG_BSSIDS);
			for (int i = 0; i < jsonBssids.length(); i++) {
				JSONObject bssid = jsonBssids.getJSONObject(i);
				String name = bssid.getString(Constants.JSON_TAG_BSSID_NAME);
				String location = bssid.getString(Constants.JSON_TAG_BSSID_LOCATION);
				bssids.add(new Bssid(name, location));
			}
		} catch (JSONException e) {
			Log.e(Constants.TAG, "Error parsing data " + e.toString());
			e.printStackTrace();
		}
		return bssids;
	}

}
