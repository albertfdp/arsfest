package dk.dtu.indoor;

public class Ssids {
	
	private String BSSID;
	private String SSID;
	private int RSS;
	
	public Ssids (String ssid, String bbsid, int rSS) {
		this.BSSID = bbsid;
		this.SSID = ssid;
		this.RSS = rSS;
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
	
	public int getRSS() {
		return RSS;
	}

	public void setRSS(int rSS) {
		RSS = rSS;
	}	
}