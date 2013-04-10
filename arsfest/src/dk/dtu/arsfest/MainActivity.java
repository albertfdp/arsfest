package dk.dtu.arsfest;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import com.astuetz.viewpager.extensions.ScrollingTabsView;
import com.astuetz.viewpager.extensions.TabsAdapter;
import com.korovyansk.android.slideout.SlideoutActivity;

import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.model.Bssid;
import dk.dtu.arsfest.parser.JsonParser;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import dk.dtu.arsfest.view.CustomPageAdapter;
import dk.dtu.arsfest.view.LocationTabs;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String PREFS_NAME = "ArsFestPrefsFile";
	private AlarmManagerBroadcastReceiver myBSSIDAlarm;

	private ArrayList<Location> locations;
	private ArrayList<Event> happeningNow;
	private ArrayList<Bssid> bssids;

	private ViewPager viewPager;
	private PagerAdapter pageAdapter;
	private ScrollingTabsView scrollingTabs;
	private TabsAdapter scrollingTabsAdapter;

	private String currentLocation;
	private Date currentDate;
	private String arsfest_start_s = new String("03-05-2013:17:30");
	private Date arsfest_start;

	private TextView headerTitle;

	private String currentTime_test_s = new String("03-05-2013:16:10");
	private Date currentTime_test;

	private TextView closeEventTitle;
	private TextView closeEventLocation;
	private TextView closeEventTime;
	private ImageView closeEventPicture;
	private TextView closeEventHappening;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Scan the BSSIDs every 60 seconds
		registerAlarmManager(60);

		// hide title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		initView();

		// Read from data.JSON
		readJson();

		// start listener for happeningNow
		startContextAwareness();

		for (Location location : this.locations) {
			Log.i("ARSFEST", location.getName());
		}

		// If it is before the start of arsfest shows countdown

		this.currentDate = getCurrentDate();
		this.arsfest_start = getStartDate(this.arsfest_start_s);

		// create menu

		findViewById(R.id.actionBarAccordeon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
										.getDisplayMetrics());
						SlideoutActivity.prepare(MainActivity.this,
								R.id.MainLayout, width);
						startActivity(new Intent(MainActivity.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		// inflate the list view with the events
		inflateView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//Prompts user to enable WiFi
		enableYourWiFiGotDammIt();
	}

	@Override
	protected void onDestroy() {
		super.onStop();
		//Cancels the Broadcast Receiver
		unregisterAlarmManager();
	}

	/**
	 * Method setting alarm manager for location awareness
	 * 
	 * @author AA
	 * @param alarmFrequency
	 *            frequency of the updates
	 */
	private void registerAlarmManager(int alarmFrequency) {
		myBSSIDAlarm = new AlarmManagerBroadcastReceiver();
		Context appContext = this.getApplicationContext();
		if (myBSSIDAlarm != null) {
			myBSSIDAlarm.SetAlarm(appContext, alarmFrequency);
		}
	}

	/**
	 * Method to unregister alarm manager for location awareness
	 * 
	 * @author AA
	 */
	private void unregisterAlarmManager() {
		Context appContext = this.getApplicationContext();
		if (myBSSIDAlarm != null) {
			myBSSIDAlarm.CancelAlarm(appContext);
		}
	}

	private void initView() {

		// update the font type of the header
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		headerTitle.setText(Constants.APP_NAME);

		closeEventTitle = (TextView) findViewById(R.id.card_title);
		closeEventLocation = (TextView) findViewById(R.id.card_location);
		closeEventTime = (TextView) findViewById(R.id.card_time);
		closeEventHappening = (TextView) findViewById(R.id.card_happening_now);
	}

	private void startContextAwareness() {
		// get current location
		
		/*JAVIER do it like this:
		 * SharedPreferences settings = appContext.getSharedPreferences(PREFS_NAME, 0);
			String result = settings.getString("SSIDs", null);
			result is a String i cannot put array list in shared preferences, check with toast how it looks like
			Toast.makeText(appContext, popUpSettings, Toast.LENGTH_SHORT).show();
		 */
		
		/*ArrayList<String> ps = new ArrayList<String>(); //should be the list of bssids recieved
		
		ArrayList<String> posLocations = new ArrayList<String>();
		
		for(String s : ps){
			for (Bssid bssid : this.bssids){
				if(bssid.compareBssid(s))
					posLocations.add(bssid.getLocation());
			}
		}
		
		currentLocation = chooseLocation(posLocations);*/
		
		// get best event
		//Event closeEvent = this.locations.get(0).getEvents().get(0);

		// inflate card with event data
		/*closeEventTitle.setText(closeEvent.getName());
		closeEventLocation.setText("Location");
		closeEventTime.setText("21:00");
		closeEventHappening.setText("Happening now".toUpperCase());
		closeEventHappening.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));*/

		// sort events

	}

	private void readJson() {

		JsonParser jsonParser;
		try {
			InputStream is = getAssets().open("data.JSON");
			jsonParser = new JsonParser(is);
			this.locations = jsonParser.readLocations();
			this.bssids = jsonParser.readBssid();

			// all events
			ArrayList<Event> allEvents = new ArrayList<Event>();
			for (Location location : this.locations) {
				for (Event event : location.getEvents()) {
					allEvents.add(event);
				}
			}
			this.locations.add(new Location("0", "ALL", allEvents));

			// get position of 'all'
			int allPos = this.locations.size() - 1;
			Collections.swap(this.locations, allPos, 0);

		} catch (IOException e) {
			Log.i("ARSFEST", e.getMessage());
		}

	}

	private void initViewPager(int pageCount) {
		viewPager = (ViewPager) findViewById(R.id.pager);
		pageAdapter = new CustomPageAdapter(this, this.locations);
		viewPager.setAdapter(pageAdapter);
		viewPager.setCurrentItem(1);
		viewPager.setPageMargin(1);

		scrollingTabs = (ScrollingTabsView) findViewById(R.id.scrolling_tabs);
		scrollingTabsAdapter = new LocationTabs(this, this.locations);
		scrollingTabs.setAdapter(scrollingTabsAdapter);
		scrollingTabs.setViewPager(viewPager);
	}

	private void inflateView() {
		initViewPager(this.locations.size());
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
	 * @author AA
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

	private Date getCurrentDate() {

		Date date = null;
		Calendar c = Calendar.getInstance();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		String formattedDate = df.format(c.getTime());
		try {
			date = (Date) df.parse(formattedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	private Date getStartDate(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
		Date start = null;
		try {
			start = (Date) formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return start;

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

	private ArrayList<Event> happeningNow_List(Date currentTime) {

		ArrayList<Event> now = new ArrayList<Event>();

		for (Location loc : this.locations) {
			Log.i("ARSFEST", "Aqui2");
			now.addAll(loc.happeningNow(currentTime));
		}

		return now;
	}
	
	private String chooseLocation(ArrayList<String> posLocations){
		
		int final_count = -1;
		int count = 0,i = 0, j = 0;
		String loc = "", elem = "", elem_j = "";
		
		for(i=0; i< posLocations.size() ; i++){
			count = 0;
			elem = posLocations.get(i);
			for(j=i; j < posLocations.size(); j++){
				elem_j = posLocations.get(j);
				if(elem.equals(elem_j))
					count++;
			}
			
			if (count > final_count){
				loc = elem;
			}
		}
		
		
		return loc;
		
	}

}
