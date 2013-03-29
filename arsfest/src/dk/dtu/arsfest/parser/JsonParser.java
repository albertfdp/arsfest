package dk.dtu.arsfest.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dk.dtu.arsfest.model.*;

public class JsonParser {
	
	private String jsonStr;
	
	/*
	 * Transforms the JSON file into a String. Stores this String in jsonStr
	 */
	public JsonParser(InputStream is) throws IOException{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");
	    
	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }
	    this.jsonStr = stringBuilder.toString(); 
	    
	    Log.i("ARSFEST", this.jsonStr);
	}
	
	/*
	 * Read data.JSON and stores the information of each location 
	 * @Return ArrayList<Locations>
	 */
	public ArrayList<Location> readLocations(){
		
		ArrayList<Location> location_list = new ArrayList<Location>();
		SimpleDateFormat formatter = null;
		
		try {
	        JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject
	        JSONArray locations = rootObject.getJSONArray("locations"); // Get all JSONArray locations

	        for(int i=0; i < locations.length(); i++) { // Loop over each each location
	            JSONObject location = locations.getJSONObject(i); 
	           
	            String id = location.getString("id"); 
	            String name = location.getString("name"); 
	            Double latitude = location.getDouble("latitude");
	            Double longitude = location.getDouble("longitude");
	            String description = location.getString("description");
	            int image = location.getInt("image");
	           
	            //get all locations of the events
	            ArrayList<Event> event_location_list = new ArrayList<Event>();
	            
	            JSONArray events = location.getJSONArray("events");
	            for(int j=0; j < events.length(); j++) {
	            	
	            	JSONObject event = events.getJSONObject(i); 
	 	           
		            String event_id = event.getString("id"); 
		            String event_name = event.getString("name"); 
		            String event_image = event.getString("image");
		            String startTime_string = event.getString("startTime");
		            formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
		            Date startTime_Date;
					try {
						startTime_Date = (Date) formatter.parse(startTime_string);
					 
			            String endTime_string = event.getString("endTime");
			            formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
			            Date endTime_Date = (Date) formatter.parse(endTime_string);
						
			            String event_description = event.getString("description");
					            
			            Event new_event = new Event(event_id,event_name,event_image,startTime_Date,endTime_Date,event_description);
			            
			            event_location_list.add(new_event);
		            
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	
	            }
	           // Create location
	            Location loc = new Location(id,name,latitude,longitude,description,image,event_location_list);

	            // Add new location to arraylist
	            location_list.add(loc);
	            
	        }
	    } catch (JSONException e) {
	        // JSON Parsing error
	        e.printStackTrace();
	    }	
		
        return location_list;
	}
	
	/*
	 * Read data.JSON and stores the information of each event
	 * @Return ArrayList<Event>
	 */
	public ArrayList<Event> readEvents(){
		
		ArrayList<Event> event_list = new ArrayList<Event>();
		SimpleDateFormat formatter = null;
        

		
		try {
	        JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject
	        JSONArray events = rootObject.getJSONArray("events"); // Get all JSONArray events

	        for(int i=0; i < events.length(); i++) { // Loop over each each event
	            JSONObject event = events.getJSONObject(i); 
	           
	            String id = event.getString("id"); 
	            String name = event.getString("name"); 
	            String image = event.getString("image");
	            String startTime_string = event.getString("startTime");
	            formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
	            Date startTime_Date;
				try {
					startTime_Date = (Date) formatter.parse(startTime_string);
				 
		            String endTime_string = event.getString("endTime");
		            formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
		            Date endTime_Date = (Date) formatter.parse(endTime_string);
					
					
		            
		            String description = event.getString("description");
	
		            //get all locations of the events
		            ArrayList<Location> event_location_list = new ArrayList<Location>();
		            
		            JSONArray locations = event.getJSONArray("locations");
		            for(int j=0; j < locations.length(); j++) {
		            	
		            	JSONObject location =  locations.getJSONObject(j);
		            	String location_id = location.getString("id");
		            	Location loc_event = new Location(location_id);
		            	event_location_list.add(loc_event);
		            	
		            }
		            
		            Event new_event = new Event(id,name,image,startTime_Date,endTime_Date,event_location_list,description);
		            
		            event_list.add(new_event);
	            
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	     // Here the event should be added to an arrayList
	        
	    } catch (JSONException e) {
	        // JSON Parsing error
	        e.printStackTrace();
	    }
		
		return event_list;
	}

}
