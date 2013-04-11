package dk.dtu.arsfest.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Bssid;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;

public class ContextAwareHelper {
	
	Context context;
	ArrayList<Bssid> bssids;
	ArrayList<Location> locations;
	String currentLocation;
	
	public ContextAwareHelper(Context context,ArrayList<Bssid> bssids,ArrayList<Location> locations){
		
		this.context = context;
		this.bssids = bssids;
		this.locations = locations;
		this.currentLocation = "l1";
		
	}
	
	
	public String startContextAwareness() {

		String result = "";
		String location = currentLocation;
		List<String> myWifiList;
		
		
		// get current location
		result = readWifi();
		
		if (result != null){
			myWifiList = stringSsidsToList(result);
			location = findLocation(myWifiList, bssids);
			
		}
		
		return location;
		
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

}
