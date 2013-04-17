package dk.dtu.arsfest.model;

public class Course {
	
	private String course;
	private String name;
	private String description;
	
	public Course(String course, String name, String description){
		
		this.course = course;
		this.name = name;
		this.description = description;
		
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
