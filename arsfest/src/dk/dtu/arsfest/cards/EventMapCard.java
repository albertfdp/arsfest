package dk.dtu.arsfest.cards;

import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.PictureListener;
import android.widget.RelativeLayout;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.localization.GenerateHTML;
import dk.dtu.arsfest.localization.LocalizationHelper;
import dk.dtu.arsfest.localization.MapActivity;
import dk.dtu.arsfest.localization.ScrollHelper;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;

@SuppressWarnings("deprecation")
public class EventMapCard extends Card {

	private String mLocation;
	private Context mContext;
	private WebView mWebView;
	private LocalizationHelper mLocalization;
	private ScrollHelper mMapScroll;

	public EventMapCard(Context context, Event event) {
		super(context, R.layout.card_event_map_layout);
		this.mContext = context;
		this.mLocation = event.getParent().getName();
		mLocalization = new LocalizationHelper(getContext());
	}

	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {

		if (view != null) {
			this.mWebView = (WebView) view.findViewById(R.id.card_inside_map);

			GenerateHTML mHTML = new GenerateHTML(
					mLocalization.getScroll(mLocation), true);
			mWebView.loadDataWithBaseURL("file:///android_asset/images/",
					mHTML.getmHTML(), "text/html", "utf-8", null);

			mWebView.getSettings().setJavaScriptEnabled(false);

			mWebView.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView v, String url) {
					super.onPageFinished(v, url);
					mWebView.setScrollContainer(false);
					mWebView.setScrollbarFadingEnabled(true);
					mWebView.getSettings().setUseWideViewPort(true);
					mWebView.getSettings().setLoadWithOverviewMode(true);
					mWebView.getSettings().setBuiltInZoomControls(false);
					mWebView.getSettings().setDisplayZoomControls(false);
					mWebView.getSettings().setSupportZoom(false);
					mWebView.setInitialScale(100);
					RelativeLayout.LayoutParams mParms = new RelativeLayout.LayoutParams(
							mWebView.getMeasuredWidth(), mWebView
									.getMeasuredWidth());
					mWebView.setLayoutParams(mParms);
				}
			});

			mWebView.setPictureListener(new PictureListener() {

				@Override
				@Deprecated
				public void onNewPicture(WebView view, Picture picture) {
					mWebView.setWebViewClient(null);
					mWebView.getSettings().setUseWideViewPort(false);
					mMapScroll = new ScrollHelper(mLocalization
							.getScroll(mLocation),
							new int[] { view.getMeasuredWidth(),
									view.getMeasuredHeight() }, view.getScale());
					mWebView.scrollTo(mMapScroll.getScroll()[0],
							mMapScroll.getScroll()[1]);
					mWebView.setPictureListener(null);
				}
			});

			mWebView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						Intent mIntent = new Intent(getContext(),
								MapActivity.class);
						mIntent.putExtra(Constants.MapStartLocation, mLocation);
						mIntent.putExtra(Constants.MapShowPin, true);
						mContext.startActivity(mIntent);
					}
					return (event.getAction() == MotionEvent.ACTION_MOVE);
				}
			});

		}
	}
}