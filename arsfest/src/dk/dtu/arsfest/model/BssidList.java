package dk.dtu.arsfest.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BssidList {
	
	@SerializedName("bssids")
	private ArrayList<Bssid> bssids;

	public BssidList(ArrayList<Bssid> bssids) {
		super();
		this.bssids = bssids;
	}

	public ArrayList<Bssid> getBssids() {
		return bssids;
	}

	public void setBssids(ArrayList<Bssid> bssids) {
		this.bssids = bssids;
	}
	
	
	
}
