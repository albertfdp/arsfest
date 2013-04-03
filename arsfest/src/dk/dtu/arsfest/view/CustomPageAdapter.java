package dk.dtu.arsfest.view;

import java.util.ArrayList;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.event.EventActivity;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;

import android.app.Activity;
import android.content.Intent;
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
		eventAdapter = new EventAdapter(mContext, R.layout.event_item, events);
		listView.setTag(position);
		listView.setAdapter(eventAdapter);
		listView.setPadding(10, 10, 10, 10);
		listView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.textured_paper));
		listView.setDivider(mContext.getResources().getDrawable(R.drawable.textured_paper));
		listView.setDividerHeight(30); // always after setDivider()
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int itemPosition,
					long id) {
				
				int tabPos = (Integer) parent.getTag();
				Event clickedEvent = locations.get(tabPos).getEvents().get(itemPosition);
				Intent intent = new Intent(mContext, EventActivity.class);
				intent.putExtra("dk.dtu.arsfest.Event", clickedEvent);
				mContext.startActivity(intent);
			}
			
		});
		((ViewPager) container).addView(listView, 0);
		return listView;
	}
	
	@Override
	public void destroyItem(View container, int position, Object view) {
		//((ViewPager) container).removeView((View) view);
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
