package dk.dtu.arsfest.utils;

import it.gmariotti.cardslib.library.internal.CardThumbnail;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class UniversalImageLoaderThumbnail extends CardThumbnail {
	
	private ImageLoader imageLoader;
	
	private String url;
	
	public UniversalImageLoaderThumbnail(Context context) {
		super(context);
		imageLoader = ImageLoader.getInstance();
		setExternalUsage(true);
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void setupInnerViewElements(ViewGroup parent, View viewImage) {
		
		imageLoader.displayImage(Constants.IMG_CONTENT_PROVIDER_URL + this.url + ".jpg", (ImageView) viewImage);
		
		DisplayMetrics metrics=parent.getResources().getDisplayMetrics();
        viewImage.getLayoutParams().width= (int)(125*metrics.density);
        viewImage.getLayoutParams().height = (int)(125*metrics.density);
	}

}
