package dk.dtu.arsfest.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	 * Not finished, just reads, it is necessary to store 
	 * Get every location
	 * @Return ArrayList<Locations>
	 */
	public void readLocations(){
		
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
	            String image = location.getString("image");
	            // HERE Should be the constructor of location
	            

	            // Here the location should be added to an arrayList
	        }
	    } catch (JSONException e) {
	        // JSON Parsing error
	        e.printStackTrace();
	    }	
		
        // return the array list
	}
	
	/*
	 * Not finished, just reads, it is necessary to store 
	 * Get every event
	 * @Return ArrayList<Events>
	 */
	public void readEvents(){
		
		try {
	        JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject
	        JSONArray events = rootObject.getJSONArray("events"); // Get all JSONArray events

	        for(int i=0; i < events.length(); i++) { // Loop over each each event
	            JSONObject event = events.getJSONObject(i); 
	           
	            String id = event.getString("id"); 
	            String name = event.getString("name"); 
	            String image = event.getString("image");
	            int startTime = event.getInt("startTime");
	            int endTime = event.getInt("endTime");
	            
	            //get all locations of the events
	            JSONArray locations = event.getJSONArray("locations");
	            for(int j=0; j < locations.length(); j++) {
	            	
	            	JSONObject location =  locations.getJSONObject(j);
	            	String location_id = location.getString("id");
	            	//Add location_id to the ArrayList of locations of the event
	            }
	            
	        }
	        
	     // Here the event should be added to an arrayList
	        
	    } catch (JSONException e) {
	        // JSON Parsing error
	        e.printStackTrace();
	    }
		
        // return the array list
	}

}
