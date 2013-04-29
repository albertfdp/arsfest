package dk.dtu.arsfest;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;

import dk.dtu.arsfest.maps.MapActivity;
import dk.dtu.arsfest.notification.NotificationActivity;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

public class SlideMenuSuper extends Activity implements
		OnSlideMenuItemClickListener {

	private Handler myHandler = new Handler();
	private SlideMenu mySlideMenu;
	private Class<?> myDesiredClass;
	private int durationOfAnimation;

	/**
	 * Method showing the slide menu at the left hand side
	 * 
	 * @param duration
	 *            : Duration of Animation
	 * @author AA
	 */
	protected void startMenu(int duration) {
		this.durationOfAnimation = duration;
		mySlideMenu = new SlideMenu(this, R.menu.slide, this,
				durationOfAnimation);
		mySlideMenu = (SlideMenu) findViewById(R.id.slideMenu);
		mySlideMenu.init(this, R.menu.slide, this, durationOfAnimation);
		mySlideMenu.setFont(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		ImageButton imageButtonAccordeon = (ImageButton) findViewById(R.id.actionBarAccordeon);
		imageButtonAccordeon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mySlideMenu.show();
			}
		});
	}

	/**
	 * Defining the onClick intents 
	 */
	@Override
	public void onSlideMenuItemClick(int itemId) {
		switch (itemId) {
		case R.id.item_programme:
			myDesiredClass = MainActivity.class;
			myHandler.postDelayed(myrunnable, (long) (1.1 * durationOfAnimation));
			break;
		case R.id.item_map:
			myDesiredClass = MapActivity.class;
			myHandler.postDelayed(myrunnable, (long) (1.1 * durationOfAnimation));
			break;
		case R.id.item_alarms:
			myDesiredClass = NotificationActivity.class;
			myHandler.postDelayed(myrunnable, (long) (1.1 * durationOfAnimation));
			break;
		case R.id.item_about:
			myDesiredClass = AboutActivity.class;
			myHandler.postDelayed(myrunnable, (long) (1.1 * durationOfAnimation));
			break;
		}
	}

	private Runnable myrunnable = new Runnable() {
		public void run() {
			runMyIntent();
		};
	};

	private void runMyIntent() {
		if (!this.getClass().getSimpleName().equals(myDesiredClass.getSimpleName())) {
			Intent myIntent = new Intent(this, myDesiredClass);
			this.startActivity(myIntent);
		}
	}

}
