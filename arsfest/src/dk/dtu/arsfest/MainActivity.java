package dk.dtu.arsfest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import com.astuetz.viewpager.extensions.IndicatorLineView;
import com.astuetz.viewpager.extensions.ScrollingTabsView;
import com.astuetz.viewpager.extensions.TabsAdapter;
import dk.dtu.arsfest.alarms.AlarmHelper;
import dk.dtu.arsfest.context.ContextAwareHelper;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.model.Bssid;
import dk.dtu.arsfest.parser.JSONParser;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import dk.dtu.arsfest.view.CustomLinePagerAdapter;
import dk.dtu.arsfest.view.CustomPageAdapter;
import dk.dtu.arsfest.view.LocationTabs;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SlideMenuSuper {

	public static final String PREFS_NAME = "ArsFestPrefsFile";

	private ArrayList<Location> locations;
	private ArrayList<Bssid> bssids;

	private ViewPager viewPager;
	private PagerAdapter pageAdapter;
	private ViewPager lineViewPager;
	private PagerAdapter linePageAdapter;
	private IndicatorLineView mLine;
	private ScrollingTabsView scrollingTabs;
	private TabsAdapter scrollingTabsAdapter;
	private TextView headerTitle;
	private String currentLocation;

	// After refactoring
	private AlarmHelper alarmHelper;
	private ContextAwareHelper contextAwareHelper;
	private String comingFromLocation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		initView();

		// Read from data.JSON
		readJson();
		
		// Create a new AlarmHelper
		alarmHelper = new AlarmHelper(this.getApplicationContext());

		// Create a new ContextAwareHelper
		contextAwareHelper = new ContextAwareHelper(
				this.getApplicationContext(), bssids, locations);

		// inflate the list view with the events
		initViewPager(1,contextAwareHelper.getEventsHappeningNow());
	}

	@Override
	protected void onStart() {
		super.onStart();

		// Scan the BSSIDs every 60 seconds
		alarmHelper.registerAlarmManager();

		// start Context Awareness
		contextAwareHelper.startContextAwareness();

		// get Location Awareness
		currentLocation = contextAwareHelper.getCurrentLocation();
		
		if (this.comingFromLocation != null) {
			this.currentLocation = this.comingFromLocation;
			this.comingFromLocation = null;
		}
		//get Time && Location Awareness
		initViewPager(contextAwareHelper.getLocationArrayPosition(currentLocation),contextAwareHelper.getEventsHappeningNow());
				
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Prompts user to enable WiFi
		enableYourWiFiGotDammIt();
	}

	@Override
	protected void onDestroy() {
		super.onStop();
		// Cancels the Broadcast Receiver
		alarmHelper.unregisterAlarmManager();
	}

	private void initView() {

		// update the font type of the header
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		headerTitle.setText(Constants.APP_NAME);
		
		// Sets the menu
		super.startMenu(Constants.SCROLL_MENU_TIME);
	}

	private void readJson() {

		JSONParser jsonParser;

		try {
			InputStream is = getAssets().open("data.JSON");
			jsonParser = new JSONParser(is);

			this.locations = jsonParser.readLocations();
			this.bssids = jsonParser.readBssid();

			// all events
			ArrayList<Event> allEvents = new ArrayList<Event>();
			for (Location location : this.locations) {
				for (Event event : location.getEvents()) {
					if (event != null)
						allEvents.add(event);
				}
			}
			this.locations.add(new Location("0", "ALL", allEvents));

			// get position of 'all'
			int allPos = this.locations.size() - 1;
			Collections.swap(this.locations, allPos, 0);

			for (Location location : locations) {
				location.sortEventsByTime();
			}

		} catch (IOException e) {
			Log.i("ARSFEST", e.getMessage());
		}

	}

	private void initViewPager(final int pos, ArrayList<Event> happeningNow) {
		viewPager = (ViewPager) findViewById(R.id.pager);
		pageAdapter = new CustomPageAdapter(this, this.locations);
		viewPager.setAdapter(pageAdapter);
		viewPager.setCurrentItem(pos);
		viewPager.setPageMargin(1);

		scrollingTabs = (ScrollingTabsView) findViewById(R.id.scrolling_tabs);
		scrollingTabsAdapter = new LocationTabs(this, this.locations);
		scrollingTabs.setAdapter(scrollingTabsAdapter);
		scrollingTabs.setViewPager(viewPager);
		ViewTreeObserver vto = scrollingTabs.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				
				scrollingTabs.moveTabTo(pos);
				
				ViewTreeObserver obs = scrollingTabs.getViewTreeObserver();
				obs.removeGlobalOnLayoutListener(this);
			}
			
		});
		
		// happening now
		happeningNow = contextAwareHelper.getEventsHappeningNow();			
		
		lineViewPager = (ViewPager) findViewById(R.id.linepager);
		linePageAdapter = new CustomLinePagerAdapter(this, this.locations, happeningNow);

		lineViewPager.setAdapter(linePageAdapter);
		lineViewPager.setCurrentItem(0);
		lineViewPager.setPageMargin(1);

		mLine = (IndicatorLineView) findViewById(R.id.line);
		mLine.setFadeOutDelay(0);
		if (Utils.hasFestFinished() || !Utils.hasFestStarted()) {
			mLine.setLineColor(this.getResources().getColor(R.color.flat_light_grey));
			mLine.setVisibility(View.INVISIBLE);
		}
		mLine.setViewPager(lineViewPager);
	}

	/**
	 * Method prompting user to enable WiFi
	 * 
	 * @author AA
	 */
	private void enableYourWiFiGotDammIt() {
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