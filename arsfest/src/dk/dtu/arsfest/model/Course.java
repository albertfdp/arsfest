package dk.dtu.arsfest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {
	
	private String course;
	private String name;
	
	public Course(String course, String name){
		
		this.course = course;
		this.name = name;
		
	}
	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Course (Parcel read) {
		this.course = read.readString();
		this.name = read.readString();

	}
	
	public static final Parcelable.Creator<Course> CREATOR = 
			new Parcelable.Creator<Course>() {

				@Override
				public Course createFromParcel(Parcel source) {
					return new Course(source);
				}

				@Override
				public Course[] newArray(int size) {
					return new Course[size];
				}
		
			};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(course);
		dest.writeString(name);
	}


}
