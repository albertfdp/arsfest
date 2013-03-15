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

import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private final Handler mHandler = new Handler();
	private ToggleButton scanningOnOff;
	private TextView status;
	private String location;
	private String bSSID;
	private String networkName = "wireless";
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		scanningOnOff = (ToggleButton) findViewById(R.id.toggleButtonOnOff);
		status = (TextView) findViewById(R.id.textViewStatus);
		status.setText("Not scanning.");

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

	public void onRadioButtonClicked(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		switch (view.getId()) {
		case R.id.radioButtonLibrary:
			if (checked) {
				location = "Library";
				Toast.makeText(getApplicationContext(), location,
						Toast.LENGTH_SHORT).show();
				break;
			}
		case R.id.radioButtonOticon:
			if (checked) {
				location = "Otticon";
				Toast.makeText(getApplicationContext(), location,
						Toast.LENGTH_SHORT).show();
				break;
			}
		case R.id.radioButtonSportsHall:
			if (checked) {
				location = "Sports Hall";
				Toast.makeText(getApplicationContext(), location,
						Toast.LENGTH_SHORT).show();
				break;
			}
		case R.id.radioButtonCanteen:
			if (checked) {
				location = "Canteen";
				Toast.makeText(getApplicationContext(), location,
						Toast.LENGTH_SHORT).show();
				break;
			}
		case R.id.radioButtonCellar:
			if (checked) {
				location = "Cellar";
				Toast.makeText(getApplicationContext(), location,
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

	private Runnable RepeatingThread = new Runnable() {

		public void run() {
			WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			List<ScanResult> results = wifiManager.getScanResults();
			for (ScanResult result : results) {
				if (result.SSID.toString().equals(networkName)) {
					Toast.makeText(getApplicationContext(), result.SSID,
							Toast.LENGTH_SHORT).show();
					bSSID = result.BSSID;
					break;
				}
			}
			status.setText("Scanning.\nNumber of counts: " + ++count
					+ ".\nSSID: " + networkName + ".\nBSSID: " + bSSID);
			mHandler.postDelayed(this, 10000);
		}
	};
}
