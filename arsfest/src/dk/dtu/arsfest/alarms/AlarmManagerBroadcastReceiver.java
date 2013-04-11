package dk.dtu.arsfest.alarms;

import java.util.ArrayList;
import java.util.List;

import dk.dtu.arsfest.utils.Constants;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	final public static String ONE_TIME = "showThisOneTimeOnly";
	public ArrayList<String> sSIDs = new ArrayList<String>();

	@Override
	public void onReceive(Context appContext, Intent myIntent) {
		Bundle extras = myIntent.getExtras();
		if (extras != null && extras.getBoolean(ONE_TIME, Boolean.TRUE)) {
			scanTheNetForMeBaby(appContext);
			saveTheResultInSharedPreferences(appContext);
		} else {
			// TODO If we want periodical scan.
			scanTheNetForMeBaby(appContext);
			saveTheResultInSharedPreferences(appContext);
		}
	}

	/**
	 * Method saves WiFi scan results to shared preferences file
	 * 
	 * @author AA
	 * @param appContext
	 *            Sets the application context
	 */
	private void saveTheResultInSharedPreferences(Context appContext) {
		SharedPreferences settings = appContext.getSharedPreferences(Constants.PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("SSIDs", sSIDs.toString());
		editor.commit();
	}

	/**
	 * Method broadcasts intent which will result with one time action
	 * 
	 * @author AA
	 * @param appContext
	 *            Sets the application context
	 */
	public void setOnetimeTimer(Context appContext) {
		AlarmManager myAlarmManager = (AlarmManager) appContext
				.getSystemService(Context.ALARM_SERVICE);
		Intent alarmIntent = new Intent(appContext,
				AlarmManagerBroadcastReceiver.class);
		alarmIntent.putExtra(ONE_TIME, Boolean.TRUE);
		PendingIntent alarmSender = PendingIntent.getBroadcast(appContext, 0,
				alarmIntent, 0);
		myAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				alarmSender);
	}

	/**
	 * Method broadcasts intent which will result with repeated action
	 * 
	 * @author AA
	 * @param appContext
	 *            Sets the application context
	 * @param alarmFrequency
	 *            Sets the alarm frequency in seconds
	 */
	public void SetAlarm(Context appContext, int alarmFrequency) {
		AlarmManager myAlarmManager = (AlarmManager) appContext
				.getSystemService(Context.ALARM_SERVICE);
		Intent alarmIntent = new Intent(appContext,
				AlarmManagerBroadcastReceiver.class);
		alarmIntent.putExtra(ONE_TIME, Boolean.FALSE);
		PendingIntent alarmSender = PendingIntent.getBroadcast(appContext, 0,
				alarmIntent, 0);
		myAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 1000 * alarmFrequency, alarmSender);
	}

	/**
	 * Method broadcasts intent which will result with canceling the alarm
	 * 
	 * @author AA
	 * @param appContext
	 *            Sets the application context
	 */
	public void CancelAlarm(Context appContext) {
		Intent alarmIntent = new Intent(appContext,
				AlarmManagerBroadcastReceiver.class);
		PendingIntent alarmSender = PendingIntent.getBroadcast(appContext, 0,
				alarmIntent, 0);
		AlarmManager alarmManager = (AlarmManager) appContext
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(alarmSender);
	}

	/**
	 * Method get the WiFi scan results
	 * 
	 * @author AA
	 * @param wifiManager
	 *            Sets the WiFi manager
	 */
	public void BSSIDScan(WifiManager wifiManager) {
		List<ScanResult> resultsOfTheScan = wifiManager.getScanResults();
		sSIDs.clear();
		for (ScanResult result : resultsOfTheScan) {
			sSIDs.add(result.BSSID);
		}

	}

	/**
	 * Method checks if WiFi card is enabled and performs one time or frequent
	 * scanning. The results are stored in sSIDs list as Strings
	 * 
	 * @author AA
	 * @param appContext
	 *            Sets the application context
	 * @param myIntent
	 *            Sets the intent that was previously sent
	 */
	private void scanTheNetForMeBaby(Context appContext) {
		WifiManager wifi = (WifiManager) appContext
				.getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled()) {
			wifi.startScan();
			BSSIDScan(wifi);
		}
	}
}