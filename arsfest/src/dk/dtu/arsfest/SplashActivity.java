package dk.dtu.arsfest;

import java.io.IOException;
import java.util.Calendar;

import org.apache.http.Header;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.FileCache;

public class SplashActivity extends SherlockActivity {
	
	private AsyncHttpClient client;
		
	private ProgressBar progressBar;
	
	private TextView splashTitle;
	private TextView splashSubtitle;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pre_arsfest);
		
		splashTitle = (TextView) findViewById(R.id.splash_arsfest_title);
		splashSubtitle = (TextView) findViewById(R.id.splash_arsfest_subtitle);
		
		progressBar = (ProgressBar) findViewById(R.id.splash_progress_bar);
		progressBar.setVisibility(View.GONE);
		
		// update fonts
		Typeface font = Typeface.createFromAsset(getAssets(), Constants.TYPEFONT_NEOSANS);
		splashTitle.setTypeface(font);
		splashSubtitle.setTypeface(font);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		if (checkNeedsUpdate()) {
			updateJson();
		} else {
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
		}	
	}
	
	private void updateJson() {
		client = new AsyncHttpClient();
		client.get(Constants.JSON_URL, new AsyncHttpResponseHandler() {
			
			@Override
			public void onStart() {
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgress(0);
			}
			
			@Override
			public void onProgress(int position, int length) {
				progressBar.setProgress((position * 100) / length);
				super.onProgress(position, length);
			}
			
			@Override
			public void onSuccess(String response) {
				
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putLong(Constants.PREFS_LAST_UPDATED_KEY, System.currentTimeMillis());
				editor.commit();
				
				try {
					FileCache.createCacheFile(getApplicationContext(), Constants.JSON_CACHE_FILENAME, response);
				} catch (IOException e) {
					Log.e(Constants.TAG, e.getMessage());
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Crouton.showText(SplashActivity.this, getString(R.string.splash_json_error), Style.ALERT);
			}
			
			@Override
			public void onFinish() {
				progressBar.setVisibility(View.GONE);
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
	}
	
	/**
	 * Checks wheather the JSON needs to be updated.
	 * 
	 * @return
	 */
	private boolean checkNeedsUpdate() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
		long lastUpdated = prefs.getLong(Constants.PREFS_LAST_UPDATED_KEY, 0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		//return calendar.getTimeInMillis() > lastUpdated;
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
