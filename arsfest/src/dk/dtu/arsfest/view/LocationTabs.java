package dk.dtu.arsfest.view;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.astuetz.viewpager.extensions.TabsAdapter;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Location;

public class LocationTabs implements TabsAdapter {
	
	private Activity context;
	private ArrayList<Location> locations;
	
	public LocationTabs(Activity context, ArrayList<Location> locations) {
		this.context = context;
		this.locations = locations;
	}
	
	@Override
	public View getView(int position) {
		Button tab;
		
		LayoutInflater inflater = context.getLayoutInflater();
		tab = (Button) inflater.inflate(R.layout.tab_scrolling, null);
		
		if (position < this.locations.size()) tab.setText(this.locations.get(position).getName());
		return tab;
	}

}