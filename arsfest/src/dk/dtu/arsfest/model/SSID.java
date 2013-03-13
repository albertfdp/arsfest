package dk.dtu.arsfest.model;

public class SSID {
	
	private String ssid;
	private Location location;
	
	public SSID (String ssid, Location location) {
		this.ssid = ssid;
		this.location = location;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
