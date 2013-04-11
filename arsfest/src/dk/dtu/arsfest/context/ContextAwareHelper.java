package dk.dtu.arsfest.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Bssid;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

public class ContextAwareHelper {
	
	Context context;
	ArrayList<Bssid> bssids;
	ArrayList<Location> locations;
	String currentLocation;
	ArrayList<Event> eventsNow;
	
	public ContextAwareHelper(Context context,ArrayList<Bssid> bssids,ArrayList<Location> locations){
		
		this.context = context;
		this.bssids = bssids;
		this.locations = locations;
		this.currentLocation = "l2";
		
		
	}
	
	public String getCurrentLocation(){
		return currentLocation;
	}
	
	public ArrayList<Event> getEventsNow(){
		return eventsNow;
	}
	
	public void startContextAwareness() {

		String result = "";
		List<String> myWifiList;
		
		//Location Awareness
		result = readWifi();
		
		if (result != null){
			myWifiList = stringSsidsToList(result);
			currentLocation = findLocation(myWifiList, bssids);
			
		}
		
		//Time Awareness
		/*if(isBeforeArsfest()){
			eventsNow = 
		}
		else if(isAfterArsfest()){
			
		}
		else
			eventsNow = getEventsHappeningNow();*/
		
	}
	
	public ArrayList<Event> getEventsHappeningNow(){
		
		ArrayList<Event> happeningNow = new ArrayList<Event>();
		Date currentDate = Utils.getCurrentDate();
		
		for (Location loc : this.locations) {
			if(!loc.getName().equals("ALL"))
				happeningNow.addAll(loc.happeningNow(currentDate));
		}

		return happeningNow;
	}
	
	private String readWifi(){
		
		String bssidsList;
		SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, 0);
		bssidsList = settings.getString("SSIDs", null);
		
		return bssidsList;
		
	}
	
	private List<String> stringSsidsToList(String s){
		
		String s_reduced = s.substring(1, s.length() - 1); 
		List<String> list =	new ArrayList<String>(Arrays.asList(s_reduced.split(", ")));
		return list;
		
	}
	
	private String findLocation(List<String> wifiList, ArrayList<Bssid> bssids){
		
		ArrayList<String> posLocations = new ArrayList<String>();
		
		for (String s : wifiList) {
			for (Bssid bssid : this.bssids) {
				if (bssid.compareBssid(s))
					posLocations.add(bssid.getLocation());
			}
		}

		if(posLocations.size()!=0)
			currentLocation = chooseLocation(posLocations);
			
		return currentLocation;
	
	}
	
	private String chooseLocation(ArrayList<String> posLocations) {

		int final_count = -1;
		int count = 0, i = 0, j = 0;
		String loc = "", elem = "", elem_j = "";

		for (i = 0; i < posLocations.size(); i++) {
			count = 0;
			elem = posLocations.get(i);
			for (j = i; j < posLocations.size(); j++) {
				elem_j = posLocations.get(j);
				if (elem.equals(elem_j))
					count++;
			}

			if (count > final_count) {
				loc = elem;
			}
		}

		return loc;

	}
	
	private boolean isBeforeArsfest(){
		
		Date currentTime = Utils.getCurrentDate();
		
		for(Location loc : locations){
			ArrayList<Event> events = loc.getEvents();
			for(Event e : events){
				if(currentTime.after(e.getStartTime()))
					return true;
			}
		}
		return false;
	}
	
	private boolean isAfterArsfest(){
		
		Date currentTime = Utils.getCurrentDate();
		
		for(Location loc : locations){
			ArrayList<Event> events = loc.getEvents();
			for(Event e : events){
				if(currentTime.before(e.getEndTime()))
					return false;
			}
		}
		return true;
	}
	
	public int getLocationArrayPosition(String location_id){
		
		int i=0;
		
		while(!locations.get(i).getId().equals(location_id))
			i++;
		
		return i;
	}
	


}
