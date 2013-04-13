package dk.dtu.arsfest.alarms;

import android.content.Context;

public class AlarmHelper {

	private AlarmManagerBroadcastReceiver myBSSIDAlarm;
	private Context context;

	public AlarmHelper(Context context) {
		this.context = context;
	}

	/**
	 * Method setting alarm manager for location awareness
	 * 
	 * @author AA
	 */
	public void registerAlarmManager() {
		myBSSIDAlarm = new AlarmManagerBroadcastReceiver();
		if (myBSSIDAlarm != null) {
			myBSSIDAlarm.setOnetimeTimer(context);
		}
	}

	/**
	 * Method to unregister alarm manager for location awareness
	 * 
	 * @author AA
	 */
	public void unregisterAlarmManager() {
		if (myBSSIDAlarm != null) {
			myBSSIDAlarm.CancelAlarm(context);
		}
	}
}
