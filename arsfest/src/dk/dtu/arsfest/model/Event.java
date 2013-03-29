package dk.dtu.arsfest.model;

import java.util.ArrayList;
import java.util.Date;

public class Event {
	
	private String id;
	private String name;
	private String image;
	private Date startTime;
	private Date endTime;
	private ArrayList<Location> locations;
	private String description;
	//private String Type;
	
	public Event (String id, String name, String image, Date startTime, Date endTime, ArrayList<Location> locations, String description) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.startTime = startTime;
		this.endTime = endTime;
		this.locations = locations; 
		this.description = description;
	}

	public Event (String id, String name, String image, Date startTime, Date endTime, String description) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.startTime = startTime;
		this.endTime = endTime; 
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
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
