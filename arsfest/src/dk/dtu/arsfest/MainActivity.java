package dk.dtu.arsfest;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;
import com.google.analytics.tracking.android.EasyTracker;

import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.FileCache;

public class MainActivity extends BaseFragment {
	
	private SideNavigationView sideNavigationView;
	
	private ArrayList<Location> locations;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
//		sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
//	    sideNavigationView.setMenuClickCallback(new ISideNavigationCallback() {
//			@Override
//			public void onSideNavigationItemClick(int itemId) {}
//		});
//	    
//	    sideNavigationView.setMode(Mode.LEFT);
//	    
//	    getSupportActionBar().setHomeButtonEnabled(true);
//	    getSupportActionBar().setTitle("Events");
	    
	    readProgramme();
	    
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }
	
	private void readProgramme() {
		locations = new ArrayList<Location>();
		try {
			locations = FileCache.readCacheFile(MainActivity.this.getActivity(), Constants.JSON_CACHE_FILENAME);
		} catch (IOException e) {
			Log.e(Constants.TAG, "Could not read file from cache. Reading from assets, could be not updated");
			Log.e(Constants.TAG, e.getMessage());
			// TODO : locations = FileCache.readFromAssets();
		}
		
		for (Location location : locations) {
			location.sortEventsByTime();
		}
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
		
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onStop();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add(R.string.action_search)
			.setIcon(R.drawable.ic_action_search)
			.setActionView(R.layout.collapsible_edittext)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		
		menu.add(R.string.action_map)
			.setIcon(R.drawable.ic_action_map)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
			
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case android.R.id.home:
		        sideNavigationView.toggleMenu();
		        break;
		    default:
		        return super.onOptionsItemSelected(item);
		    }
		    return true;
	}

	@Override
	public int getTitleResourceId() {
		return R.string.activity_main;
	}
	
}