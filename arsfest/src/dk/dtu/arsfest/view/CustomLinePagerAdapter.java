package dk.dtu.arsfest.view;

import java.util.ArrayList;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomLinePagerAdapter extends PagerAdapter {
	
	protected transient Activity mContext;
	private ArrayList<Event> events;
	
	public CustomLinePagerAdapter (Activity context, ArrayList<Event> events) {
		this.mContext = context;
		this.events = events;
	}
	
	@Override
	public void destroyItem(View container, int position, Object view) {
		((ViewPager) container).removeView((View) view);
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		
		Event event = events.get(position);
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.event_item, null);
		
		TextView eventTitle = (TextView) view.findViewById(R.id.event_title);
		TextView eventLocation = (TextView) view.findViewById(R.id.event_location);
		
		eventTitle.setText(event.getName());
		eventLocation.setText(event.getLocation());
		
		((ViewPager) container).addView(view, 0);
		
		return view;
		
	}

	@Override
	public int getCount() {
		return this.events.size();
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
