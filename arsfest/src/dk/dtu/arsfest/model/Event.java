package dk.dtu.arsfest.model;

import java.util.ArrayList;

public class Event {
	
	private int id;
	private String name;
	private String image;
	private int startTime;
	private int endTime;
	private ArrayList<Location> locations;
	private String description;
	
	public Event (int id, String name, String image, int startTime, int endTime, ArrayList<Location> locations, String description) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.startTime = startTime;
		this.endTime = endTime;
		this.locations = locations;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public ArrayList<Location> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
