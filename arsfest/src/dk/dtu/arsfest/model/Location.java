/*******************************************************************************
 * Copyright 2013 Albert Fern�ndez de la Pe�a
 ******************************************************************************/
package dk.dtu.arsfest.model;

import java.util.ArrayList;

public class Location {
	
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

}
