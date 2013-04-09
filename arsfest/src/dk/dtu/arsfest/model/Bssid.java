package dk.dtu.arsfest.model;

public class Bssid {
	
	private String bssid;
	private String location;
	//private int rssid; optional
	
	public Bssid (String bssid, String location) {
		this.bssid = bssid;
		this.location = location;
	}

	public String getBssid() {
		return bssid;
	}

	public void setBssid(String ssid) {
		this.bssid = ssid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString(){
		return this.bssid+ ' ' + this.location;
	}
	
	public boolean compareBssid(String s){
		if(bssid.regionMatches(true, 0, s, 0, bssid.length()))
			return true;
		return false;
	}

}
