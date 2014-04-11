package dk.dtu.arsfest.sensors;

import java.util.HashMap;
import java.util.Map;

import dk.dtu.arsfest.utils.Constants;

import android.content.Context;

public class BSSIDService {

	private NetworkHelper mNetworkHelper;
	private boolean isNetworkAvailable = false;
	private String currentLocation = null;
	private Map<String, String> mMap = new HashMap<String, String>();

	public BSSIDService(Context c) {
		mNetworkHelper = new NetworkHelper(c);
		populateMap();
	}

	public void getUpdate() {
		setNetworkAvailable(mNetworkHelper.isWiFiTurnedOn());
		if (isNetworkAvailable()) {
			mNetworkHelper.resetNetwork();
			setCurrentLocation(mNetworkHelper.getScanResults());
		}
	}

	public boolean isNetworkAvailable() {
		return isNetworkAvailable;
	}

	private void setNetworkAvailable(boolean isNetworkAvailable) {
		this.isNetworkAvailable = isNetworkAvailable;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	private void setCurrentLocation(String currentLocation) {
		if (mMap.containsKey(currentLocation)) {
			this.currentLocation = mMap.get(currentLocation);	
		} else {
			this.currentLocation = null;
		}
	}

	private void populateMap() {
		mMap.put("00:27:0d:5f:17", Constants.Library);
		mMap.put("00:27:0d:ed:18", Constants.SportsHall);
		mMap.put("00:27:0d:ed:1a", Constants.SportsHall);
		mMap.put("00:27:0d:5f:42", Constants.SportsHall);
		mMap.put("00:27:0d:5f:4c", Constants.SportsHall);
		mMap.put("00:1c:0e:42:70", Constants.Oticon);
		mMap.put("00:1c:0e:42:d4", Constants.Oticon);
		mMap.put("00:1a:30:7d:89", Constants.Canteen);
		mMap.put("00:1c:0e:42:7e", Constants.Canteen);
		mMap.put("00:23:5e:1f:20", Constants.Canteen);
		mMap.put("00:1c:0e:42:d4", Constants.Canteen);
	}
}