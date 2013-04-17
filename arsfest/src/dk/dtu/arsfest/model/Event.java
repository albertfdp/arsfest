package dk.dtu.arsfest.model;

import java.util.ArrayList;
import java.util.Date;

import dk.dtu.arsfest.utils.Constants;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import dk.dtu.arsfest.model.Course;

public class Event implements Parcelable, Comparable<Event> {
	
	private String id;
	private String name;
	private String image;
	private Date startTime;
	private Date endTime;
	private String location;
	private String description;
	private String type;
	private String theme;
	private ArrayList<Course> menu;
	
	public ArrayList<Course> getMenu() {
		return menu;
	}

	public void setMenu(ArrayList<Course> menu) {
		this.menu = menu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Event (String id, String name, String image, Date startTime, Date endTime, 
			String location, String description, String type, String theme) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location; 
		this.description = description;
		this.type = type;
		this.theme = theme;
	}
	
	public Event(String id, String name, String image, Date startTime, Date endTime, 
			String location, String description, String type, String theme, ArrayList<Course> menu) {
		
		this.id = id;
		this.name = name;
		this.image = image;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location; 
		this.description = description;
		this.type = type;
		this.theme = theme;
		this.menu = menu;
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
	public String getLocation() {
		return location;
	}
	public void setLocations(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString(){
		return id+' '+name+' '+startTime+' '+endTime+' '+description+' '+image;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(image);
		dest.writeLong(startTime.getTime());
		dest.writeLong(endTime.getTime());
		dest.writeString(location);
		dest.writeString(description);
		dest.writeString(type);
		dest.writeString(theme);
	}
	
	public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {

		@Override
		public Event createFromParcel(Parcel source) {
			return new Event(source);
		}

		@Override
		public Event[] newArray(int size) {
			return new Event[size];
		}
	};
	
	private Event(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.image = in.readString();
		this.startTime = new Date(in.readLong());
		this.endTime = new Date(in.readLong());
		this.location = in.readString();
		this.description = in.readString();
		this.type = in.readString();
		this.theme = in.readString();
	}

	public boolean hasStarted(Date currentTime) {
		return (currentTime.after(startTime));
	}
	
	public boolean hasFinished(Date currentTime) {
		return (currentTime.after(endTime));
	}
	
	public boolean isHappeningNow(Date currentTime){
		//return (hasStarted(currentTime) && !hasFinished(currentTime));
		if (hasStarted(currentTime) && !hasFinished(currentTime)) {
			Log.i(Constants.TAG, this.name + " hasStarted, but not finished");
			return true;
		} else if (hasStarted(currentTime) && hasFinished(currentTime)) {
			Log.i(Constants.TAG, this.name + " hasStarted, and finished");
		}
		return false;
	}

	@Override
	public int compareTo(Event anotherInstance) {
		return this.getStartTime().compareTo(anotherInstance.getStartTime());
	}
	
	
	
	
}
