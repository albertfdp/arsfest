package dk.dtu.arsfest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.astuetz.viewpager.extensions.IndicatorLineView;
import com.astuetz.viewpager.extensions.ScrollingTabsView;
import com.astuetz.viewpager.extensions.TabsAdapter;
import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;

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
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		OnSlideMenuItemClickListener {

	public static final String PREFS_NAME = "ArsFestPrefsFile";
	public SlideMenu slidemenu;

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
	private TextView happeningNowTitle;
	private String currentLocation;
	
	private ArrayList<Event> happeningNow;

	// After refactoring
	private AlarmHelper alarmHelper;
	private ContextAwareHelper contextAwareHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// hide title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		initView();

		// Read from data.JSON
		readJson();

		// Create a new AlarmHelper
		alarmHelper = new AlarmHelper(this.getApplicationContext());

		// Create a new ContextAwareHelper
		contextAwareHelper = new ContextAwareHelper(
				this.getApplicationContext(), bssids, locations);

		// If it is before the start of arsfest shows countdown
		/*
		 * Date currentDate = Utils.getCurrentDate(); Date arsfest_start =
		 * Utils.getStartDate(Constants.FEST_START_TIME);
		 */
		// create menu

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
		
		//get Time && Location Awareness
		initViewPager(contextAwareHelper.getLocationArrayPosition(currentLocation),contextAwareHelper.getEventsHappeningNow());
		
		//headerTitle.setText(currentLocation);

		// get Time Awareness
		happeningNow = contextAwareHelper.getEventsHappeningNow();

		// inflate card with event data
		happeningNowTitle.setText(this.getResources().getString(
				R.string.event_happening_now));

		// sort events

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

		happeningNowTitle = (TextView) findViewById(R.id.card_happening_now);
		happeningNowTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		// Sets the menu
		startMenu(333);
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

	private void initViewPager(int pos, ArrayList<Event> happeningNow) {
		viewPager = (ViewPager) findViewById(R.id.pager);
		pageAdapter = new CustomPageAdapter(this, this.locations);
		viewPager.setAdapter(pageAdapter);
		viewPager.setCurrentItem(pos);
		viewPager.setPageMargin(1);

		scrollingTabs = (ScrollingTabsView) findViewById(R.id.scrolling_tabs);
		scrollingTabsAdapter = new LocationTabs(this, this.locations);
		scrollingTabs.setAdapter(scrollingTabsAdapter);
		scrollingTabs.setViewPager(viewPager);

		// happening now
		//this.happeningNow = this.locations.get(1).getEvents();
		happeningNow = contextAwareHelper.getEventsHappeningNow();			

		lineViewPager = (ViewPager) findViewById(R.id.linepager);
		linePageAdapter = new CustomLinePagerAdapter(this, this.locations, this.happeningNow);

		lineViewPager.setAdapter(linePageAdapter);
		lineViewPager.setCurrentItem(0);
		lineViewPager.setPageMargin(1);

		mLine = (IndicatorLineView) findViewById(R.id.line);
		mLine.setFadeOutDelay(0);
		mLine.setBackgroundColor(this.getResources()
				.getColor(R.color.flat_lila));
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

	private void showCountdown(Date current, Date start) {

		CountDownTimer cdt = new CountDownTimer(start.getTime()
				- current.getTime(), 1000) {

			@Override
			public void onTick(long millisUntilFinished) {

				TextView days = (TextView) findViewById(R.id.textDays);
				TextView hours = (TextView) findViewById(R.id.textHours);
				TextView mins = (TextView) findViewById(R.id.textMinutes);
				int daysValue = (int) (millisUntilFinished / 86400000);
				int hoursValue = (int) (((millisUntilFinished / 1000) - (daysValue * 86400)) / 3600);
				int minsValue = (int) (((millisUntilFinished / 1000) - ((daysValue * 86400) + (hoursValue * 3600))) / 60);
				days.setText(String.valueOf(daysValue));
				hours.setText(String.valueOf(hoursValue));
				mins.setText(String.valueOf(minsValue));

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

			}
		};

		cdt.start();

	}

	/**
	 * Method showing the accordeon slide menu at the left hand side
	 * 
	 * @param durationOfAnimation
	 *            : Duration of Animation
	 * @author AA
	 */
	private void startMenu(int durationOfAnimation) {
		slidemenu = new SlideMenu(this, R.menu.slide, this, durationOfAnimation);
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);		
		slidemenu.init(this, R.menu.slide, this, durationOfAnimation);			
		slidemenu.setFont(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		ImageButton imageButtonAccordeon = (ImageButton) findViewById(R.id.actionBarAccordeon);
		imageButtonAccordeon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				slidemenu.show();
			}
		});
	}

	/**
	 * Menu
	 */
	@Override
	public void onSlideMenuItemClick(int itemId) {
		switch (itemId) {
		case R.id.item_programme:
			if (this.getClass() != MainActivity.class) {
				Intent intent = new Intent(this, MainActivity.class);
				this.startActivity(intent);
			}
			break;
		case R.id.item_map:
			Toast.makeText(this, "Jamal, please paint the wall.", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.item_settings:
			Toast.makeText(this, "Don't drink coffe if it's dry.", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.item_about:
			Toast.makeText(this, "Don't milk nipples when they are soft.", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	/**
	 * Menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.icon:
			slidemenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
