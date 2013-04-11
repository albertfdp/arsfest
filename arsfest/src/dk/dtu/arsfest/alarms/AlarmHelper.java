package dk.dtu.arsfest.alarms;

import android.content.Context;
import dk.dtu.arsfest.AlarmManagerBroadcastReceiver;

public class AlarmHelper {
	
	private AlarmManagerBroadcastReceiver myBSSIDAlarm;
	private Context context;
	private int alarmFrequency;
	
	public AlarmHelper(Context context, int frequency){
		
		this.context = context;
		this.alarmFrequency = frequency;
	}
	
	/**
	 * Method setting alarm manager for location awareness
	 * 
	 * @author AA
	 * @param alarmFrequency
	 *            frequency of the updates
	 */
	public void registerAlarmManager() {
		
		myBSSIDAlarm = new AlarmManagerBroadcastReceiver();
		if (myBSSIDAlarm != null) {
			myBSSIDAlarm.SetAlarm(context, alarmFrequency);
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
