package dk.dtu.arsfest.notification;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.SlideMenuSuper;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.parser.JSONParser;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotificationActivity extends SlideMenuSuper {

	private ListView myNotificationsListView;
	private TextView headerTitle, textViewYourAlarms, textViewVibration;
	private CheckBox checkBoxVibration;
	private RelativeLayout vibrationRelativeLayout;
	private ArrayList<String> myEventsStrings;
	private ArrayList<Event> myEvents;
	private ArrayList<Location> locations;
	private static Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NotificationActivity.context = getApplicationContext();
		setContentView(R.layout.activity_notification);
		initGeneralView();
		initNotificationActivity();
		initEvents();
		setAdapter();
	}

	private void setAdapter() {
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(
				getApplicationContext(), myEventsStrings, myEvents);
		myNotificationsListView.setAdapter(adapter);
	}

	private void initEvents() {
		JSONParser jsonParser;
		try {
			InputStream is = getAssets().open("data.JSON");
			jsonParser = new JSONParser(is);
			this.locations = jsonParser.readLocations();
			myEventsStrings = new ArrayList<String>();
			myEvents = new ArrayList<Event>();
			Calendar calendarNow = Calendar.getInstance();
			calendarNow.setTimeInMillis(System.currentTimeMillis());
			calendarNow.add(Calendar.MINUTE, 15);
			for (Location location : this.locations) {
				for (Event event : location.getEvents()) {
					if (event != null)
						if (!event.hasStarted(calendarNow.getTime())) {
							myEvents.add(event);
							myEventsStrings.add(event.getName());
						}
				}
			}
			this.locations.add(new Location("0", "ALL", myEvents));
			for (Location location : locations) {
				location.sortEventsByTime();
			}
		} catch (IOException e) {
			Log.e("ARSFEST", e.getMessage());
		}
	}

	private void initNotificationActivity() {
		SharedPreferences sharedPrefs = getSharedPreferences(
				Constants.PREFS_NAME, 0);
		myNotificationsListView = (ListView) findViewById(R.id.listViewOfNotifications);
		textViewYourAlarms = (TextView) findViewById(R.id.textViewYourAlarmsTitle);
		textViewVibration = (TextView) findViewById(R.id.textViewVibrationTitle);
		checkBoxVibration = (CheckBox) findViewById(R.id.checkBoxVibration);
		Typeface dtuFont = Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS);
		textViewYourAlarms.setTypeface(dtuFont);
		textViewVibration.setTypeface(dtuFont);
		checkBoxVibration.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_NEOSANS));
		checkBoxVibration.setChecked(sharedPrefs.getBoolean(
				Constants.PREFERENCES_VIBRATION, true));
		vibrationRelativeLayout = (RelativeLayout) findViewById(R.id.VibrationLayout);
		vibrationRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences sharedPrefs = getSharedPreferences(
						Constants.PREFS_NAME, 0);
				SharedPreferences.Editor editor = sharedPrefs.edit();
				checkBoxVibration.setChecked(!checkBoxVibration.isChecked());
				if (checkBoxVibration.isChecked()) {
					editor.putBoolean(Constants.PREFERENCES_VIBRATION, true);
				} else {
					editor.putBoolean(Constants.PREFERENCES_VIBRATION, false);
				}
				editor.commit();
			}
		});
	}

	private void initGeneralView() {
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		headerTitle.setText(Constants.APP_NAME);
		super.startMenu(Constants.SCROLL_MENU_TIME);
	}

	public static Context getAppContext() {
		return NotificationActivity.context;
	}
}