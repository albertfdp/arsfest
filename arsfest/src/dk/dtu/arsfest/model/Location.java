/*******************************************************************************
 * Copyright 2013 Albert Fern�ndez de la Pe�a
 ******************************************************************************/
package dk.dtu.arsfest.model;

import java.util.ArrayList;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
	
	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private String description;
	private int image;
	private ArrayList<Event> events;
	
	public Location(String id){
		this.id = id;
	}
	
	public Location(String id, String name, ArrayList<Event> events) {
		this.id = id;
		this.name = name;
		this.events = events;
	}
	public Location(String id, String name, double latitude, double longitude, String description, int image, ArrayList<Event> events) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.image = image;
		this.events = events;
	}
	
	public Location(String id, String name, double latitude, double longitude, String description, int image) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.image = image;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getImage() {
		return this.image;
	}

	public void setImage(int image) {
		this.image = image;
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
	
	public ArrayList<Event> getEvents(){
		return this.events;
	}
	
	@Override
	public String toString(){
		return id+' '+name+' '+longitude+' '+latitude+' '+description+' '+image;
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
