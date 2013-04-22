package dk.dtu.arsfest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
	private TextView textViewHeaderTitle, textViewGooglePlay, textViewAboutUs,
			textViewRateUs, textViewAbout, textViewVersion,
			textViewVersionTitle;
	private RelativeLayout relativeLayoutRateUsLink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		startMenu(Constants.SCROLL_MENU_TIME);
		setAbout();
	}

	/**
	 * Method setting the about activity (fonts and listeners)
	 * 
	 * @author AA
	 */
	private void setAbout() {
		textViewHeaderTitle = (TextView) findViewById(R.id.actionBarTitle);
		textViewHeaderTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		textViewHeaderTitle.setText(Constants.APP_NAME);

		textViewRateUs = (TextView) findViewById(R.id.textViewRateUs);
		textViewRateUs.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));

		textViewAbout = (TextView) findViewById(R.id.textViewAbout);
		textViewAbout.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_NEOSANS));

		textViewVersion = (TextView) findViewById(R.id.textViewVersion);
		textViewVersion.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_NEOSANS));

		textViewGooglePlay = (TextView) findViewById(R.id.AboutGooglePlay);
		textViewGooglePlay.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_NEOSANS));

		textViewAboutUs = (TextView) findViewById(R.id.textViewAboutUs);
		textViewAboutUs.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_NEOSANS));

		textViewVersionTitle = (TextView) findViewById(R.id.textViewVersion2);
		textViewVersionTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_NEOSANS));

		relativeLayoutRateUsLink = (RelativeLayout) findViewById(R.id.AboutLayoutRateUs);
		relativeLayoutRateUsLink.setOnClickListener(new View.OnClickListener() {
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
