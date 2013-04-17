package dk.dtu.arsfest.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;
import dk.dtu.arsfest.AboutActivity;
import dk.dtu.arsfest.MainActivity;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity implements
		OnSlideMenuItemClickListener {

	private int bmpScrollX, bmpScrollY = 0;
	private int bmpWidth, bmpHeight = 0;
	private SlideMenu slidemenu;
	private WebView webView;
	private TextView headerTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map);
		startMenu(Constants.SCROLL_MENU_TIME);
		
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		
		setWebView();
	}

	private void setInitialScroll(String stringExtra) {	
		bmpWidth = (int) findViewById(R.id.imageViewMapOfThe101)
				.getMeasuredWidth() / 2;
		bmpHeight = (int) findViewById(R.id.imageViewMapOfThe101)
				
				.getMeasuredHeight() / 2;
		if (stringExtra.equals("Library")) {
			bmpScrollX = 770 - bmpWidth;
			bmpScrollY = 560 - bmpHeight;
			correctX(bmpScrollX);
			correctY(bmpScrollY);
		} else if (stringExtra.equals("Oticon")) {
			bmpScrollX = 2100 - bmpWidth;
			bmpScrollY = 240 - bmpHeight;
			correctX(bmpScrollX);
			correctY(bmpScrollY);
		} else if (stringExtra.equals("Kantine")) {
			bmpScrollX = 1420 - bmpWidth;
			bmpScrollY = 590 - bmpHeight;
			correctX(bmpScrollX);
			correctY(bmpScrollY);
		} else if (stringExtra.equals("Sportshal")) {
			bmpScrollX = 2130 - bmpWidth;
			bmpScrollY = 1090 - bmpHeight;
			correctX(bmpScrollX);
			correctY(bmpScrollY);
		}
	}

	private void correctY(int Y) {
		if (Y < 0) {
			bmpScrollY = 0;
		} else if (Y + bmpHeight * 2 > 1671) {
			bmpScrollY = 1671 - bmpHeight * 2;
		}
	}

	private void correctX(int X) {
		if (X < 0) {
			bmpScrollX = 0;
		} else if (X + bmpWidth * 2 > 2482) {
			bmpScrollX = 2482 - bmpWidth * 2;
		}
	}

	private void setWebView() {
		webView = (WebView) findViewById(R.id.imageViewMapOfThe101);
		webView.loadDataWithBaseURL("file:///android_asset/images/",
				getHtmlFromAsset(), "text/html", "utf-8", null);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				webView.getSettings().setBuiltInZoomControls(false);
				webView.getSettings().setUseWideViewPort(true);
				webView.setInitialScale(100);
			}
		});

		webView.setPictureListener(new PictureListener() {
			@Override
			public void onNewPicture(WebView view, Picture picture) {
				Intent intent = getIntent();
				setInitialScroll(intent.getStringExtra(Constants.EXTRA_START));
				webView.scrollTo(bmpScrollX, bmpScrollY);
			}
		});
	}

	private String getHtmlFromAsset() {
		InputStream is;
		StringBuilder builder = new StringBuilder();
		String htmlString = null;
		try {
			is = getAssets().open("map.html");
			if (is != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

				htmlString = builder.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlString;
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
			break;
		case R.id.item_settings:
			Toast.makeText(this, "Don't milk nipples when they are soft.",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_about:
			Intent intentAbout = new Intent(this, AboutActivity.class);
			this.startActivity(intentAbout);
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
