package dk.dtu.arsfest.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable, Comparable<Event> {
	
	private String id;
	private String name;
	private String image;
	private Date startTime;
	private Date endTime;
	private String description;
	private String type;
	private String theme;
	private boolean remark = false;
	private Location parent;
	
	public Event (String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public boolean isRemark() {
		return remark;
	}

	public void setRemark(boolean remark) {
		this.remark = remark;
	}

	private ArrayList<Course> menu = new ArrayList<Course>();
	
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
	
	public Location getParent() {
		return parent;
	}

	public void setParent(Location parent) {
		this.parent = parent;
	}

	public Event(String id, String name, String image, Date startTime, Date endTime, 
			String location, String description, String type, String theme, ArrayList<Course> menu, boolean remark, Location parent) {
		
		this.id = id;
		this.name = name;
		this.image = image;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.type = type;
		this.theme = theme;
		this.menu = menu;
		this.remark = remark;
		this.parent = parent;
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
		dest.writeString(description);
		dest.writeString(type);
		dest.writeString(theme);
		dest.writeTypedList(menu);
		dest.writeParcelable(parent, flags);
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
		this.description = in.readString();
		this.type = in.readString();
		this.theme = in.readString();
		in.readTypedList(this.menu, Course.CREATOR);
		this.parent = in.readParcelable(Location.class.getClassLoader());
	}

	public boolean hasStarted(Date currentTime) {
		return (currentTime.after(startTime));
	}
	
	public boolean hasStarted() {
		Date date = new Date();
		return (date.after(startTime));
	}
	
	public boolean hasFinished() {
		Date date = new Date();
		return (date.after(endTime));
	}
	
	public boolean hasFinished(Date currentTime) {
		return (currentTime.after(endTime));
	}
	
	public boolean isHappeningNow(Date currentTime){
		if (hasStarted(currentTime) && !hasFinished(currentTime)) {
			return true;
		} 
		return false;
	}

	@Override
	public int compareTo(Event anotherInstance) {
		return this.getStartTime().compareTo(anotherInstance.getStartTime());
	}
	
	public static final Comparator<Event> START_TIME = new Comparator<Event>() {

		@Override
		public int compare(Event lhs, Event rhs) {
			return lhs.compareTo(rhs);
		}
		
	};
	
	public static final Comparator<Event> END_TIME = new Comparator<Event>() {

		@Override
		public int compare(Event lhs, Event rhs) {
			return lhs.getEndTime().compareTo(rhs.getEndTime());
		}
		
	};
	
}
