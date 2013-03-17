package dk.dtu.arsfest.maps;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import dk.dtu.arsfest.R;

public class CustomInfoWindowAdapter implements com.google.android.gms.maps.GoogleMap.InfoWindowAdapter {
	
	private LayoutInflater inflater = null;
	
	public CustomInfoWindowAdapter(LayoutInflater inflater) {
		this.inflater = inflater;
	}
	
	/*
	 * Defines the content of the InfoWindow
	 * @see com.google.android.gms.maps.GoogleMap.InfoWindowAdapter#getInfoContents(com.google.android.gms.maps.model.Marker)
	 */
	@Override
	public View getInfoContents(Marker marker) {
		
		// getting view from the layout file
		View view = inflater.inflate(R.layout.map_info_window, null);
		
		// getting reference to the TextView to set the title & description
		TextView title = (TextView) view.findViewById(R.id.marker_title);
		TextView description = (TextView) view.findViewById(R.id.marker_description);
		
		// setting the title
		title.setText(marker.getTitle());
		description.setText(marker.getSnippet());
		
		return view;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

}
