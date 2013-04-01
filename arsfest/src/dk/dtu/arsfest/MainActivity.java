package dk.dtu.arsfest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.astuetz.viewpager.extensions.ScrollingTabsView;
import com.astuetz.viewpager.extensions.TabsAdapter;
import com.korovyansk.android.slideout.SlideoutActivity;
import com.korovyansk.android.slideout.SlideoutHelper;

import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.parser.JsonParser;
import dk.dtu.arsfest.view.CustomPageAdapter;
import dk.dtu.arsfest.view.EventAdapter;
import dk.dtu.arsfest.view.LocationTabs;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String PREFS_NAME = "ArsFestPrefsFile";
	private ArrayList<Location> locations;
	private ArrayList<Event> events;
	
	private ViewPager viewPager;
	private PagerAdapter pageAdapter;
	private ScrollingTabsView scrollingTabs;
	private TabsAdapter scrollingTabsAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		// Read from data.JSON
		readJson();
		
		if (this.locations != null) {
			for (Location location : this.locations) {
				Log.i("ARSFEST", location.getName());
			}
		}
		//create menu
		
		findViewById(R.id.actionBarAccordeon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
						SlideoutActivity.prepare(MainActivity.this, R.id.MainLayout, width);
						startActivity(new Intent(MainActivity.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		
		// inflate the list view with the events
		inflateView();
	}
	
	private void readJson() {
			
		JsonParser jsonParser;
		try {
			InputStream is = getAssets().open("data.JSON");
			jsonParser = new JsonParser(is);
			this.locations = jsonParser.readLocations();
			this.events = jsonParser.readEvents();
		} catch (IOException e) {
			Log.i("ARSFEST", e.getMessage());
		}
		
	}
	
	private void initViewPager(int pageCount, int backgroundColor, int textColor) {
		viewPager = (ViewPager) findViewById(R.id.pager);
		pageAdapter = new CustomPageAdapter(this, this.locations, backgroundColor, textColor);
		viewPager.setAdapter(pageAdapter);
		viewPager.setCurrentItem(1);
		viewPager.setPageMargin(1);
		
		scrollingTabs = (ScrollingTabsView) findViewById(R.id.scrolling_tabs);
		scrollingTabsAdapter = new LocationTabs(this, this.locations);
		scrollingTabs.setAdapter(scrollingTabsAdapter);
		scrollingTabs.setViewPager(viewPager);
	}
	
	private void inflateView() {
		initViewPager(this.locations.size(), 0xFF000000, 0xFFFFFFFF);
	}

		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * Here in onResume() WiFi connection. User is prompted with dialog box
		 * to enable WiFi
		 */
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		boolean popUpSettings = settings.getBoolean("popUpConnectivityDiscard",
				false);
		if (!haveITurnedOnMyWiFi(getApplicationContext()) && !popUpSettings) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle(R.string.wifi_title);
			alertDialogBuilder.setMessage(R.string.wifi_txt);
			alertDialogBuilder.setPositiveButton(R.string.wifi_option1,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							startActivityForResult(new Intent(
									android.provider.Settings.ACTION_SETTINGS),
									id);
							dialog.cancel();
						}
					}).setNegativeButton(R.string.wifi_option2,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							SharedPreferences settings = getSharedPreferences(
									PREFS_NAME, 0);
							SharedPreferences.Editor editor = settings.edit();
							editor.putBoolean("popUpConnectivityDiscard", true);
							editor.commit();

							Toast.makeText(getApplicationContext(),
									"WiFi card is off", Toast.LENGTH_SHORT)
									.show();
							dialog.cancel();
						}
					});
			alertDialogBuilder.create().show();

		}
	}

	/**
	 * Method checking current state of the WiFi card
	 * 
	 * @param applicationContext
	 *            : Application Context
	 * @return false: WiFi off
	 * @return true: WiFi on
	 */
	private boolean haveITurnedOnMyWiFi(Context applicationContext) {
		ConnectivityManager myConnectivityManager = (ConnectivityManager) applicationContext
				.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo myWiFi = myConnectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (myWiFi.isAvailable()) {
			return true;
		}
		return false;
	}
}
