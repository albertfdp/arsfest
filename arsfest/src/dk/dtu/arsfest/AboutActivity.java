package dk.dtu.arsfest;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

public class AboutActivity extends SlideMenuSuper {

	private TextView textViewHeaderTitle, textViewGooglePlay, textViewAboutUs,
			textViewRateUs, textViewAbout, textViewVersion,
			textViewVersionTitle;
	private RelativeLayout relativeLayoutRateUsLink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		super.startMenu(Constants.SCROLL_MENU_TIME);
		setAbout();
	}

	/**
	 * Method setting the about activity (fonts and listeners)
	 * 
	 * @author AA
	 */
	private void setAbout() {
		
		Typeface tf_neosans = Utils.getTypeface(this,
				Constants.TYPEFONT_NEOSANS);
		
		textViewHeaderTitle = (TextView) findViewById(R.id.actionBarTitle);
		textViewHeaderTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		textViewHeaderTitle.setText(Constants.APP_NAME);

		textViewRateUs = (TextView) findViewById(R.id.textViewRateUs);
		textViewRateUs.setTypeface(tf_neosans);

		textViewAbout = (TextView) findViewById(R.id.textViewAbout);
		textViewAbout.setTypeface(tf_neosans);

		textViewVersion = (TextView) findViewById(R.id.textViewVersion);
		textViewVersion.setTypeface(tf_neosans);

		textViewGooglePlay = (TextView) findViewById(R.id.AboutGooglePlay);
		textViewGooglePlay.setTypeface(tf_neosans);

		textViewAboutUs = (TextView) findViewById(R.id.textViewAboutUs);
		textViewAboutUs.setTypeface(tf_neosans);

		textViewVersionTitle = (TextView) findViewById(R.id.textViewVersion2);
		textViewVersionTitle.setTypeface(tf_neosans);

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
}
