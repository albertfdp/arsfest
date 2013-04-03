package dk.dtu.arsfest.model;

import java.util.ArrayList;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Event implements Parcelable {
	
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
		dest.writeString(name);
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
		this.name = in.readString();
	}
	
	public boolean happeningNow(Date currentTime){
		
		if((currentTime.after(this.getStartTime())) && (currentTime.before(this.getEndTime()))){
			
			return true;
		}
			
		
		return false;
	}
	
}
