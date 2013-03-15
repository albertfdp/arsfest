package dk.dtu.indoor;

/*
 * BSSID: Basic Service Set Identifier
 * 
 * BSSID refers to the MAC address of the Station (STA) in an Access Point
 * (AP) in an infrastructure mode BSS defined by the IEEE 802.11-1999
 * Wireless Lan specification. This field uniquely identifies each BSS. In
 * an IBSS, the BSSID is a locally administered IEEE MAC address generated
 * from a 46-bit random number. The individual/group bit of the address is
 * set to 0. The universal/local bit of the address is set to 1.
 */

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private final Handler mHandler = new Handler();
	private ToggleButton scanningOnOff;
	private TextView status;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		scanningOnOff = (ToggleButton) findViewById(R.id.toggleButtonOnOff);
		status = (TextView) findViewById(R.id.textViewStatus);

		scanningOnOff.setChecked(false);
		scanningOnOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (scanningOnOff.isChecked()) {
					RepeatingThread.run();
				} else {
					mHandler.removeCallbacks(RepeatingThread);
					status.setText("Not scanning.");
				}
			}
		});
	}

	private Runnable RepeatingThread = new Runnable() {

		public void run() {
			i++;
			WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			status.setText("Scanning? " + scanningOnOff.isEnabled()
					+ ". SSID: " + wifiInfo.getSSID() + ". #: " + i
					+ ". Info: " + wifiInfo.toString());
			mHandler.postDelayed(this, 1000);
		}
	};
}
