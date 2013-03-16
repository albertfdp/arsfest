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

/*
 * This app is an Indoor Access Point Explorer in order to map Access Points and locations at DTU building 101.
 * This is needed as a documentation for DTU ArsFest 2013 App Project
 */

import java.io.FileOutputStream;
import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
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
	private String bSSID1;
	private String bSSID2;
	private String networkName1 = "dtu";
	private String networkName2 = "eduroam";
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

	/*
	 * changing the location depending on radio button state
	 */

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

	/*
	 * repeating thread which reads the current BSSID
	 */

	private Runnable RepeatingThread = new Runnable() {
		public void run() {
			WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			List<ScanResult> results = wifiManager.getScanResults();
			for (ScanResult result : results) {
				if (result.SSID.toString().equals(networkName1)) {
					bSSID1 = result.BSSID;
					break;
				}
			}

			for (ScanResult result : results) {
				if (result.SSID.toString().equals(networkName2)) {
					bSSID2 = result.BSSID;
					break;
				}
			}

			String dots = ".";
			for (int a = 0; a <= count % 15; a++) {
				dots += ".";
			}

			status.setText("Scanning" + dots + "\nNumber of counts: " + ++count
					+ ".\n" + networkName1 + ", BSSID: " + bSSID1 + ".\n"
					+ networkName2 + ", BSSID: " + bSSID2 + ".");
			mHandler.postDelayed(this, 1000);

			// TODO Some problems with file creation... need to take a look on
			// this
			/* Saving to the .txt file */
			// File file = new File(getApplicationContext().getCacheDir(),
			// filename);

			try {
				String filename = "resultsOfInternalScanning.txt";
				String string = networkName1 + ";" + location + ";" + bSSID1
						+ ";<\n" + networkName2 + ";" + location + ";" + bSSID2
						+ ".\n";
				saveToFile(filename, string);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public void saveToFile(String filename, String data) throws Exception {
		try {
			FileOutputStream outStream = openFileOutput(filename,
					Context.MODE_PRIVATE);
			outStream.write(data.toString().getBytes());
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}