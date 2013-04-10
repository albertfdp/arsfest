package dk.dtu.arsfest.event;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventActivity extends Activity {

	private TextView textViewEventName;
	private TextView textViewTime;
	private TextView textViewPlace;
	private WebView textViewEventDescription;
	private TextView headerTitle;
	private LinearLayout backgroundImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// hide title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_event);
		Typeface myNeoSans = Typeface.createFromAsset(getAssets(),
				"fonts/NeoSans.ttf");

		// update the font type of the header
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		headerTitle.setText(Constants.APP_NAME);

		// Read event info from intent
		Intent intent = getIntent();
		Event event = (Event) intent.getParcelableExtra(Constants.EXTRA_EVENT);
		Location eventLocation = (Location) intent.getParcelableExtra(Constants.EXTRA_LOCATION);

		Log.i("ARSFEST", event.getName() + " " + event.getStartTime());

		Drawable background = getResources().getDrawable(R.drawable.background);
		
		backgroundImage = (LinearLayout) findViewById(R.id.eventBackground);
		backgroundImage.setBackgroundDrawable(background);
		textViewEventName = (TextView) findViewById(R.id.textViewEventName);
		textViewEventName.setTypeface(myNeoSans);
		textViewTime = (TextView) findViewById(R.id.textViewTime);
		textViewTime.setTypeface(myNeoSans);
		textViewPlace = (TextView) findViewById(R.id.textViewPlace);
		textViewPlace.setTypeface(myNeoSans);
		textViewEventDescription = (WebView) findViewById(R.id.textViewEventDescription);
		textViewEventDescription.setBackgroundColor(0x00000000);
		textViewEventDescription.setVerticalScrollBarEnabled(true);
		textViewEventDescription.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		setEventName(event.getName());
		setEventTime(event.getStartTime().toString());
		setEventPlace(eventLocation.getName());
		setEventDescription(event.getDescription());
	}

	private void setEventDescription(String string) {
		string = "<html><head><style type=\"text/css\">@font-face {font-family: 'myNeo'; src: url('file:///android_asset/fonts/NeoSans.ttf');}</style></head><body style=\"text-align:justify;font-family: 'myNeo', serif;background: #232323; color: ffffff;\">"
				+ string + "</body></html>";
		textViewEventDescription.loadDataWithBaseURL(null, string, "text/html",
				"utf-8", "about:blank");
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