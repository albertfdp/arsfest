package dk.dtu.arsfest.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class MapAct extends Activity {

	ImageView myImageView;
	Bitmap bitmap;
	int bmpWidth, bmpHeight;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		final WebView web = (WebView) findViewById(R.id.imageViewMapOfThe101);

		web.loadDataWithBaseURL("file:///android_asset/images/",
				getHtmlFromAsset(), "text/html", "utf-8", null);
		web.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				web.scrollTo(300, 300);
				web.getSettings().setBuiltInZoomControls(true);
				web.getSettings().setUseWideViewPort(true);
				web.setInitialScale(100);
			}
		});

		web.setPictureListener(new PictureListener() {

			@Override
			public void onNewPicture(WebView view, Picture picture) {
				web.scrollTo(300, 300);
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
}
