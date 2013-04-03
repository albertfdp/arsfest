package dk.dtu.arsfest.event;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class EventActivity extends Activity {

	private TextView textViewEventName;
	private TextView textViewTime;
	private TextView textViewPlace;
	private TextView textViewEventDescription;
	private TextView headerTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// hide title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_event);
		Typeface myNeoSans = Typeface.createFromAsset(getAssets(), "fonts/NeoSans.ttf");
		
		// update the font type of the header
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		headerTitle.setTypeface(Utils.getTypeface(this, Constants.TYPEFONT_PROXIMANOVA));
		headerTitle.setText(Constants.APP_NAME);
		
		// Read event info from intent
		Intent intent = getIntent();
		Event event = (Event) intent.getParcelableExtra("dk.dtu.arsfest.Event");
		
		Log.i("ARSFEST", event.getName() + " " + event.getStartTime());

		textViewEventName = (TextView) findViewById(R.id.textViewEventName);
		textViewEventName.setTypeface(myNeoSans);
		textViewTime = (TextView) findViewById(R.id.textViewTime);
		textViewTime.setTypeface(myNeoSans);
		textViewPlace = (TextView) findViewById(R.id.textViewPlace);
		textViewPlace.setTypeface(myNeoSans);
		textViewEventDescription = (TextView) findViewById(R.id.textViewEventDescription);
		textViewEventDescription.setTypeface(myNeoSans);

		setEventName(event.getName());
		setEventTime("");
		setEventPlace("Somewhere");
		setEventDescription("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");	
	}

	private void setEventDescription(String string) {
		textViewEventDescription.setText(string);
	}

	private void setEventPlace(String string) {
		textViewPlace.setText(string);
	}

	private void setEventTime(String string) {
		textViewTime.setText(string);
	}

	private void setEventName(String string) {
		textViewEventName.setText(string);
	}
}