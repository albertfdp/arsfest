package dk.dtu.arsfest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;

import dk.dtu.arsfest.maps.MapActivity;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

public class AboutActivity extends Activity implements
		OnSlideMenuItemClickListener {

	private SlideMenu slidemenu;
	private TextView headerTitle;
	private TextView rateUs;
	private TextView about;
	private TextView version;
	private RelativeLayout rateUsLink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		startMenu(Constants.SCROLL_MENU_TIME);
		setAbout();
	}

	/**
	 * Method setting the about activity
	 * 
	 * @author AA
	 */
	private void setAbout() {
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));

		rateUs = (TextView) findViewById(R.id.textViewRateUs);
		rateUs.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));

		about = (TextView) findViewById(R.id.textViewAbout);
		about.setTypeface(Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS));

		version = (TextView) findViewById(R.id.textViewVersion);
		version.setTypeface(Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS));

		rateUsLink = (RelativeLayout) findViewById(R.id.AboutLayoutRateUs);
		rateUsLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(getString(R.string.linkforgoogleplay)));
				startActivity(browserIntent);
			}
		});
	}

	/**
	 * Method showing the accordeon slide menu at the left hand side
	 * 
	 * @param durationOfAnimation
	 *            : Duration of Animation
	 * @author AA
	 */
	private void startMenu(int durationOfAnimation) {
		slidemenu = new SlideMenu(this, R.menu.slide, this, durationOfAnimation);
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.slide, this, durationOfAnimation);
		slidemenu.setFont(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		ImageButton imageButtonAccordeon = (ImageButton) findViewById(R.id.actionBarAccordeon);
		imageButtonAccordeon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				slidemenu.show();
			}
		});
	}

	/**
	 * Menu
	 */
	@Override
	public void onSlideMenuItemClick(int itemId) {
		switch (itemId) {
		case R.id.item_programme:
			Intent intentProgramme = new Intent(this, MainActivity.class);
			this.startActivity(intentProgramme);
			break;
		case R.id.item_map:
			Intent intentMap = new Intent(this, MapActivity.class);
			intentMap.putExtra(Constants.EXTRA_START, "");
			this.startActivity(intentMap);
			break;
		case R.id.item_settings:
			Toast.makeText(this, "Don't milk nipples when they are soft.",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_about:
			break;
		}
	}

	/**
	 * Menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.icon:
			slidemenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
