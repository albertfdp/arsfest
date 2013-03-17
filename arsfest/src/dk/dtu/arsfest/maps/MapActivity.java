/*******************************************************************************
 * Copyright 2013 Albert Fernández de la Peña
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package dk.dtu.arsfest.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class MapActivity extends FragmentActivity implements OnInfoWindowClickListener {
	
	private static final LatLng DTU_101 = new LatLng(55.786365, 12.524393);
	private ArrayList<Location> locations;
	
	private GoogleMap map;
	private Map<Marker, Location> spotMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		locations = new ArrayList<Location>();
		locations.add(new Location("Oticon Salen", 55.786902,12.525932, "nfaf", R.drawable.fastfood));
		locations.add(new Location("Library", 55.786908, 12.523352, "afaf", R.drawable.restaurant));
		
		// add events for restaurant
		ArrayList<Event> events = new ArrayList<Event>();
		events.add(new Event(0, "Official dinner", "img/library.jpg", 1367618400, 1367618400 + 3000, locations, "baafafkan"));
		
		setUpMapIfNeeded();
	}
	
	private void setUpMapIfNeeded() {
		// do a null check to confirm that we have not already instantiated the map
		if (map == null) {
			map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			
			// check if we were successful in obtaining the map
			if (map != null) {
				// the map is verified, now is safe to manipulate the map
				
				map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				
				// move camera to DTU building 101, with a zoom of 15
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(DTU_101, 17));
				
				// enable my-location button
				map.setMyLocationEnabled(true);
				
				spotMap = new HashMap<Marker, Location>();
				
				for (Location location : locations) {
					
					Marker marker = map.addMarker(new MarkerOptions()
						.position(new LatLng(location.getLatitude(), location.getLongitude()))
						.title(location.getName())
						.snippet(location.getAddress())
						.icon(BitmapDescriptorFactory.fromResource(location.getImage()))
					);
					marker.showInfoWindow();
					spotMap.put(marker, location);
				}
				
				// setting a custom info window adapter for the google maps marker
				map.setInfoWindowAdapter(new CustomInfoWindowAdapter(this.getLayoutInflater()));
				map.setOnInfoWindowClickListener(this);
			}
		}
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Location location = (Location) spotMap.get(marker);
		Toast.makeText(this, location.getName(), Toast.LENGTH_SHORT).show();
	}
		

}
