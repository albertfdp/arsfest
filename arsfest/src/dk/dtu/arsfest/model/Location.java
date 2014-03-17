package dk.dtu.arsfest.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
	
	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private String description;
	private ArrayList<Event> events;
	
	public Location(String id, String name, double latitude, double longitude,
			String description, ArrayList<Event> events) {
		super();
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.events = events;
	}
	
	/**
	 * Custom clone method.
	 * @param location
	 */
	public Location(Location location) {
		super();
		this.id = location.id;
		this.name = location.name;
		this.latitude = location.latitude;
		this.longitude = location.longitude;
		this.description = location.description;
		this.events = new ArrayList<Event>();
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public void sortEventsByTime() {
		Collections.sort(this.events, Event.START_TIME);
	}
	
	public ArrayList<Event> getEvents(){
		return this.events;
	}
	
	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	@Override
	public String toString(){
		return id+' '+name+' '+longitude+' '+latitude+' '+description;
	}
	
	public ArrayList<Event> happeningNow(Date currentTime){
		
		ArrayList<Event> now = new ArrayList<Event>();
		
		for(Event event : this.getEvents()){
			
			if (event.isHappeningNow(currentTime))
				now.add(event);
		}
		return now;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeDouble(longitude);
		dest.writeDouble(latitude);
	}
	
	public static final Comparator<Location> TIME = new Comparator<Location>() {

		@Override
		public int compare(Location lhs, Location rhs) {
			return 0;
		}
		
	};
	
	public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>()  {

		@Override
		public Location createFromParcel(Parcel source) {
			return new Location(source);
		}

		@Override
		public Location[] newArray(int size) {
			return new Location[size];
		}
		
	};
	
	private Location(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.longitude = in.readDouble();
		this.latitude = in.readDouble();
	}

}
