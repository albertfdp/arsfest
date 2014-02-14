package dk.dtu.arsfest.context;

import java.util.LinkedList;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class ContextAwareListener implements LocationListener {
	
	private LinkedList<dk.dtu.arsfest.model.Location> locations;
	
	public ContextAwareListener(LinkedList<dk.dtu.arsfest.model.Location> locations) {
		this.locations = locations;
	}

	@Override
	public void onLocationChanged(Location location) {
		Location closestLocation = null;
		for (dk.dtu.arsfest.model.Location loc : locations) {
			
			Location otherLocation = new Location("otherLocation");
			otherLocation.setLatitude(loc.getLatitude());
			otherLocation.setLongitude(loc.getLongitude());
			
			if (closestLocation == null || (location.distanceTo(otherLocation) < location.distanceTo(closestLocation))) {
				closestLocation = otherLocation;
			}
			
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
