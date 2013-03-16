package dk.dtu.indoor;

public class Ssids {
	
	private String BSSID;
	private String SSID;
	
	public Ssids (String bbsid, String ssid) {
		this.BSSID = bbsid;
		this.SSID = ssid;
	}
	
	public String getBSSID() {
		return BSSID;
	}
	public void setBSSID(String bSSID) {
		BSSID = bSSID;
	}
	public String getSSID() {
		return SSID;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
	}
	
}
