package dk.dtu.arsfest.notification;

import java.util.ArrayList;
import java.util.Calendar;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {

	private final Context currentContext;
	private final ArrayList<Event> myEventNames;
	private RelativeLayout myRelativeLayout;

	public MySimpleArrayAdapter(Context setContext,
			ArrayList<String> initialStrings, ArrayList<Event> eventNames) {
		super(setContext, R.layout.notification_list_item, initialStrings);
		this.myEventNames = eventNames;
		this.currentContext = setContext;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) currentContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View myRowView = myInflater.inflate(R.layout.notification_list_item,
				parent, false);
		Typeface neoSans = Utils.getTypeface(currentContext,
				Constants.TYPEFONT_NEOSANS);
		myRelativeLayout = (RelativeLayout) myRowView
				.findViewById(R.id.layoutOfNotificationItem);

		TextView myMainText = (TextView) myRowView
				.findViewById(R.id.textViewEventInfo);
		myMainText.setText(setMainText(position));
		myMainText.setTypeface(neoSans);
		TextView mySubText = (TextView) myRowView
				.findViewById(R.id.textViewEventName);
		mySubText.setText(myEventNames.get(position).getName());
		mySubText.setTypeface(neoSans);

		final CheckBox mych = (CheckBox) myRowView
				.findViewById(R.id.checkBoxListView);

		SharedPreferences sharedPrefs = currentContext.getSharedPreferences(
				Constants.PREFS_NAME, 0);
		if (sharedPrefs.getBoolean(
				"Alarm" + myEventNames.get(position).getId(), false)) {
			mych.setChecked(true);
		} else {
			mych.setChecked(false);
		}

		myRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SharedPreferences sharedPrefs = currentContext
						.getSharedPreferences(Constants.PREFS_NAME, 0);
				SharedPreferences.Editor editor = sharedPrefs.edit();
				mych.setChecked(!mych.isChecked());
				if (mych.isChecked()) {
					setMyPendingIntent(position);
					editor.putBoolean("Alarm"
							+ myEventNames.get(position).getId(), true);
				} else {
					cancelMyPendingIntent(position);
					editor.putBoolean("Alarm"
							+ myEventNames.get(position).getId(), false);
				}
				editor.commit();
			}			
		});
		return myRowView;
	}

	private String setMainText(int position) {
		String myStringBuilder = "";
		if (myEventNames.get(position).getStartTime().getHours() < 10) {
			myStringBuilder += "0";
		}
		myStringBuilder += myEventNames.get(position).getStartTime().getHours()
				+ ":";
		if (myEventNames.get(position).getStartTime().getMinutes() < 10) {
			myStringBuilder += "0";
		}
		myStringBuilder += myEventNames.get(position).getStartTime()
				.getMinutes();
		UnderstandableLocations myLocation = new UnderstandableLocations(myEventNames.get(position)
				.getLocation());
		myStringBuilder += ", "
				+ myLocation.getResultLocation();
		return myStringBuilder;
	}
	
	private void setMyPendingIntent(int position) {
		int myFlagForIntent = Integer.valueOf(myEventNames.get(position).getId().substring(1));
		Intent myIntent = new Intent(currentContext, MyNotificationService.class);

		myIntent.putExtra(Constants.EXTRA_EVENT, myEventNames.get(position));
		PendingIntent myPendingIntent = PendingIntent.getService(currentContext, myFlagForIntent, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager alarmManager = (AlarmManager) currentContext.getSystemService(Context.ALARM_SERVICE);
		//TODO get correct shift
		Calendar calendarNow = Calendar.getInstance();
		calendarNow.setTimeInMillis(System.currentTimeMillis());
		Calendar calendarEvent = Calendar.getInstance();
		calendarEvent.setTime(myEventNames.get(position).getStartTime());
		calendarEvent.add(Calendar.MINUTE, -15);
		//calendarNow.add(Calendar.SECOND, (int) - calendarEvent.getTimeInMillis()/1000);
		//calendarNow.add(Calendar.SECOND, 3);		
		alarmManager.set(AlarmManager.RTC_WAKEUP,
				calendarEvent.getTimeInMillis(), myPendingIntent);
		
	}
	
	private void cancelMyPendingIntent(int position) {
		int myFlagForIntent = Integer.valueOf(myEventNames.get(position).getId().substring(1));
		AlarmManager alarmManager = (AlarmManager) currentContext.getSystemService(Context.ALARM_SERVICE);
		Intent myIntent = new Intent(currentContext, MyNotificationService.class);;
		PendingIntent myPendingIntent = PendingIntent.getService(currentContext, myFlagForIntent, myIntent, 0);
		alarmManager.cancel(myPendingIntent);
	}
}