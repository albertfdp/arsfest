package dk.dtu.arsfest.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.event.EventActivity;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CustomPageAdapter extends PagerAdapter {
	
	protected transient Activity mContext;
	
	private ArrayList<Location> locations;

	private EventAdapter eventAdapter;
	private ArrayList<Event> events;
	
	public CustomPageAdapter(Activity context, ArrayList<Location> locations) {
		this.mContext = context;
		this.locations = locations;
	}

	@Override
	public int getCount() {
		return this.locations.size();
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		
		ListView listView = new ListView (mContext);
		
		events = this.locations.get(position).getEvents();
		Collections.sort(events);
				
		eventAdapter = new EventAdapter(mContext, R.layout.event_item, events, this.locations);
		listView.setTag(position);
		listView.setAdapter(eventAdapter);
		listView.setSelection(getNextEvent());
		listView.setPadding(15, 10, 15, 10);
		listView.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
		listView.setDivider(new ColorDrawable(mContext.getResources().getColor(R.color.grey)));
		listView.setDividerHeight(15); // always after setDivider()
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int itemPosition,
					long id) {
				
				int tabPos = (Integer) parent.getTag();
				Event clickedEvent = locations.get(tabPos).getEvents().get(itemPosition);
				
				// get location of the clicked event
				Location clickedLocation = locations.get(tabPos);
				
				Intent intent = new Intent(mContext, EventActivity.class);
				intent.putExtra(Constants.EXTRA_EVENT, clickedEvent);
				intent.putExtra(Constants.EXTRA_LOCATION, clickedLocation);
				mContext.startActivity(intent);
			}
			
		});
		((ViewPager) container).addView(listView, 0);
		return listView;
	}
	
	private int getNextEvent() {
		int index = 0;
		Date currentTime = Utils.getCurrentDate();
		while (index < events.size()) {
			if (!events.get(index).hasFinished(currentTime)) {
				break;
			}
			index++;
		}
		return index;
	}
	
	@Override
	public void destroyItem(View container, int position, Object view) {
		((ViewPager) container).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}
	
	@Override
	public void finishUpdate(View container) {}
	
	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {}
	
	@Override
	public Parcelable saveState() {
		return null;
	}
	
	@Override
	public void startUpdate(View container) {}


}
