package dk.dtu.arsfest.model;

public class BSSID {
	
	private String bssid;
	private Location location;
	//private int rssid; optional
	
	public BSSID (String bssid, Location location) {
		this.bssid = bssid;
		this.location = location;
	}

	public String getBssid() {
		return bssid;
	}

	public void setBssid(String ssid) {
		this.bssid = ssid;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
