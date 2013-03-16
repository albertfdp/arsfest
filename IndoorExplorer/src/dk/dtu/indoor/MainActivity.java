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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
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
			ArrayList<Ssids> ssids = new ArrayList<Ssids>();
			for (ScanResult result : results) {
				ssids.add(new Ssids(result.BSSID, result.SSID));
			}
			status.setText("Scanning ...");
			mHandler.postDelayed(this, 1000);
			
			try {
				String filename = "result.txt";
				String string = "";
				for (Ssids ssid : ssids) {
					string = location + ";" + ssid.getBSSID() + ";" + ssid.getSSID() + ";\n";
					saveToFile(filename, string);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public void saveToFile(String filename, String data) throws Exception {
		try {
			if (isExternalStorageWritable()) {
				File file = new File(Environment.getExternalStorageDirectory(), filename);
				FileOutputStream outStream = new FileOutputStream(file, true);
				outStream.write(data.toString().getBytes());
				outStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}
}