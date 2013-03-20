/*******************************************************************************
 * Copyright 2013 Albert Fern�ndez de la Pe�a
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package dk.dtu.arsfest;

import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button button;
	private TextView days;
	private TextView hours;
	private TextView mins;
	private TextView daysLabel;
	private TextView hoursLabel;
	private TextView minsLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (haveITurnedOnMyWiFi(getApplicationContext())) {

			setContentView(R.layout.activity_main);

			days = (TextView) findViewById(R.id.textDays);
			hours = (TextView) findViewById(R.id.textHours);
			mins = (TextView) findViewById(R.id.textMinutes);
			daysLabel = (TextView) findViewById(R.id.labelDays);
			hoursLabel = (TextView) findViewById(R.id.labelHours);
			minsLabel = (TextView) findViewById(R.id.labelMinutes);
			days.setTypeface(Utils.getTypeface(this,
					Constants.TYPEFONT_WELLFLEET));
			hours.setTypeface(Utils.getTypeface(this,
					Constants.TYPEFONT_WELLFLEET));
			mins.setTypeface(Utils.getTypeface(this,
					Constants.TYPEFONT_WELLFLEET));
			daysLabel.setTypeface(Utils.getTypeface(this,
					Constants.TYPEFONT_WELLFLEET));
			hoursLabel.setTypeface(Utils.getTypeface(this,
					Constants.TYPEFONT_WELLFLEET));
			minsLabel.setTypeface(Utils.getTypeface(this,
					Constants.TYPEFONT_WELLFLEET));

			// set countdown to arsfest start
			showCountdown();

			button = (Button) findViewById(R.id.button);
			button.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this,
							dk.dtu.arsfest.maps.MapActivity.class);
					startActivity(i);
				}

			});

			// CallRestServiceTask task = new CallRestServiceTask(this);
			// task.execute();
		} else {
			setContentView(R.layout.wifi_popup);
			TextView myError = (TextView) findViewById(R.id.textViewErrorCode);
			myError.setText("There is no WiFi enabled right now. " +
					"The application you want to use was aimed to use information " +
					"provided by your WiFi card. Please enable your WiFi " +
					"connection and try again later.");
		}
	}

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

	private void showCountdown() {
		Time startTime = new Time();
		startTime
				.set(0, Constants.FEST_START_TIME_MINS,
						Constants.FEST_START_TIME_HOURS,
						Constants.FEST_START_TIME_DAY,
						Constants.FEST_START_TIME_MONTH,
						Constants.FEST_START_TIME_YEAR);
		startTime.normalize(true);
		long startTimeMillis = startTime.toMillis(true);

		Time now = new Time();
		now.setToNow();
		now.normalize(true);
		long millisNow = now.toMillis(true);

		long millisOnFuture = startTimeMillis - millisNow;
		new CustomCounter(millisOnFuture, 1000).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class CustomCounter extends CountDownTimer {

		public CustomCounter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTick(long millisUntilFinished) {
			int daysValue = (int) ((millisUntilFinished / 1000) / 86400);
			int hoursValue = (int) (((millisUntilFinished / 1000) - (daysValue * 86400)) / 3600);
			int minsValue = (int) (((millisUntilFinished / 1000) - ((daysValue * 86400) + (hoursValue * 3600))) / 60);
			days.setText(String.valueOf(daysValue));
			hours.setText(String.valueOf(hoursValue));
			mins.setText(String.valueOf(minsValue));
		}

	}

}
