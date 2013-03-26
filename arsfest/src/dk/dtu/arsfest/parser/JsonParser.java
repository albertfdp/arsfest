package dk.dtu.arsfest.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dk.dtu.arsfest.model.*;

public class JsonParser {
	
	private String jsonStr;
	
	/*
	 * Transforms the JSON file into a String. Stores this String in jsonStr
	 */
	public JsonParser() throws IOException{
		
		BufferedReader reader = new BufferedReader( new FileReader ("/arsfest/assets/data.JSON"));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");
	    
	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }
		this.jsonStr = stringBuilder.toString(); 
	}
	
	/*
	 * Read data.JSON and stores the information of each location 
	 * @Return ArrayList<Locations>
	 */
	public ArrayList<Location> readLocations(){
		
		ArrayList<Location> location_list = new ArrayList<Location>();
		
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
	            
	            // Create location
	            Location loc = new Location(id,name,latitude,longitude,description,image);

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
	 * times are set to 0
	 * @Return ArrayList<Event>
	 */
	public ArrayList<Event> readEvents(){
		
		ArrayList<Event> event_list = new ArrayList<Event>();
		
		try {
	        JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject
	        JSONArray events = rootObject.getJSONArray("events"); // Get all JSONArray events

	        for(int i=0; i < events.length(); i++) { // Loop over each each event
	            JSONObject event = events.getJSONObject(i); 
	           
	            String id = event.getString("id"); 
	            String name = event.getString("name"); 
	            String image = event.getString("image");
	            
	            /*Rethink the types for the times
	            int startTime = event.getInt("startTime");
	            int endTime = event.getInt("endTime");*/
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
	            
	            Event new_event = new Event(id,name,image,0,0,event_location_list,description);
	            
	            event_list.add(new_event);
	        }
	        
	     // Here the event should be added to an arrayList
	        
	    } catch (JSONException e) {
	        // JSON Parsing error
	        e.printStackTrace();
	    }
		
		return event_list;
	}

}
