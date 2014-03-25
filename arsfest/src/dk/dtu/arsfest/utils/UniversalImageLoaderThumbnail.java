package dk.dtu.arsfest.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import it.gmariotti.cardslib.library.internal.CardThumbnail;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

        viewImage.getLayoutParams().width = 250;
        viewImage.getLayoutParams().height = 250;
	}

}
