package dk.dtu.arsfest.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;


public class LocationList {
	
	@SerializedName("locations")
	private ArrayList<Location> locations;

	public LocationList(ArrayList<Location> locations) {
		super();
		this.locations = locations;
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}
	
	

}
