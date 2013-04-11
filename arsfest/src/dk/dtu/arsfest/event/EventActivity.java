package dk.dtu.arsfest.event;

import java.util.Locale;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class EventActivity extends Activity {

	private TextView eventTitle;
	private TextView eventTime;
	private TextView eventLocation;
	private TextView eventDescription;
	private TextView headerTitle;
	
	private Event event;
	private Location location;
	
	private void initView() {
		eventTitle = (TextView) findViewById(R.id.event_title);
		eventTime = (TextView) findViewById(R.id.event_time);
		eventLocation = (TextView) findViewById(R.id.event_location);
		eventDescription = (TextView) findViewById(R.id.event_description);
		
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		
		Typeface dtuFont = Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS);
		headerTitle.setTypeface(dtuFont);
		eventTitle.setTypeface(dtuFont);
		
		headerTitle.setText(Constants.APP_NAME);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// hide title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_detail);
		
		initView();

		// Read event info from intent
		Intent intent = getIntent();
		this.event = (Event) intent.getParcelableExtra(Constants.EXTRA_EVENT);
		this.location = (Location) intent.getParcelableExtra(Constants.EXTRA_LOCATION);
		
		updateView();
	}
	
	private void updateView() {
		eventTitle.setText(event.getName().toUpperCase(Locale.ENGLISH));
		eventTime.setText(Utils.getEventStringTime(event.getStartTime()) + "\t-\t" + Utils.getEventStringTime(event.getEndTime()));
		eventLocation.setText(location.getName());
		//eventDescription.setText(event.getDescription());
	}
	
	
}