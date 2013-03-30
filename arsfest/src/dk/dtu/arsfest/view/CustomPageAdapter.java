package dk.dtu.arsfest.view;

import java.util.ArrayList;

import dk.dtu.arsfest.model.Location;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomPageAdapter extends PagerAdapter {
	
	protected transient Activity mContext;
	
	private ArrayList<Location> locations;
	private int mBackgroundColor = 0xFFFFFFFF;
	private int mTextColor = 0xFF000000;
	
	public CustomPageAdapter(Activity context, ArrayList<Location> locations, int backgroundColor, int textColor) {
		this.mContext = context;
		this.locations = locations;
		this.mBackgroundColor = backgroundColor;
		this.mTextColor = textColor;
	}

	@Override
	public int getCount() {
		return this.locations.size();
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		RelativeLayout v = new RelativeLayout(mContext);
		
		TextView t = new TextView(mContext);
		t.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		t.setText(this.locations.get(position).getName());
		t.setTextSize(120);
		t.setGravity(Gravity.CENTER);
		t.setTextColor(mTextColor);
		t.setBackgroundColor(mBackgroundColor);
		
		v.addView(t);
		
		((ViewPager) container).addView(v, 0);
		
		return v;
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
