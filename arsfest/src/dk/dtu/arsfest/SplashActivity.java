package dk.dtu.arsfest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
	private Context mContext;
		
	//private ProgressBar progressBar;
	private ProgressDialog dialog;
	
	private TextView splashTitle;
	private TextView splashSubtitle;
	
	private Button programButton;
	private Button ticketButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pre_arsfest);
		
		mContext = this;
		
		splashTitle = (TextView) findViewById(R.id.splash_arsfest_title);
		splashSubtitle = (TextView) findViewById(R.id.splash_arsfest_subtitle);
		
		programButton = (Button) findViewById(R.id.splash_button_program);
		ticketButton = (Button) findViewById(R.id.splash_button_tickets);
		

		programButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				intent.putExtra(Constants.EXTRA_EVENT_SHOW_FINISHED, false);
				startActivity(intent);
				finish();
            }
        });
		
		ticketButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(SplashActivity.this, TicketSaleActivity.class);
				intent.putExtra(Constants.EXTRA_EVENT_SHOW_FINISHED, false);
				startActivity(intent);
				finish();
            }
        });
		
		/*progressBar = (ProgressBar) findViewById(R.id.splash_progress_bar);
		progressBar.setVisibility(View.GONE);*/
		
		// update fonts
		Typeface font = Typeface.createFromAsset(getAssets(), Constants.TYPEFONT_NEOSANS);
		splashTitle.setTypeface(font);
		splashSubtitle.setTypeface(font);
		programButton.setTypeface(font);
		ticketButton.setTypeface(font);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		if (!checkNeedsUpdate()) {
			updateJson();
		}/* else {
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			intent.putExtra(Constants.EXTRA_EVENT_SHOW_FINISHED, false);
			startActivity(intent);
			finish();
		}	*/
	}
	
	private void updateJson() {
		client = new AsyncHttpClient();
		client.get(Constants.JSON_URL, new AsyncHttpResponseHandler() {
			
			@Override
			public void onStart() {
				/*progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgress(0);*/
				dialog = new ProgressDialog(mContext);
		        dialog.setMessage("Loading...");
		        dialog.setIndeterminate(false);
		        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        dialog.setCancelable(true);
			}
			
			@Override
			public void onProgress(int position, int length) {
				//progressBar.setProgress((position * 100) / length);
				super.onProgress(position, length);
				dialog.show();
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
				//progressBar.setVisibility(View.GONE);
				dialog.dismiss();
				
				//if event has started start with program
				if(hasStarted()){
					Intent intent = new Intent(SplashActivity.this, MainActivity.class);
					intent.putExtra(Constants.EXTRA_EVENT_SHOW_FINISHED, false);
					startActivity(intent);
					finish();
				}
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
		return calendar.getTimeInMillis() > lastUpdated;
		//return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	private boolean hasStarted(){
		Date date = new Date();
		//TODO
		return false;
	}

}
