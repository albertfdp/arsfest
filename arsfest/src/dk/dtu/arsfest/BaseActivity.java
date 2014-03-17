package dk.dtu.arsfest;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public abstract class BaseActivity extends SherlockActivity {
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create global configuration and initialize
        // ImageLoader with this configuration
        DisplayImageOptions options = new DisplayImageOptions.Builder()
	    	.showImageOnLoading(R.drawable.ic_empty)
	    	.showImageOnFail(R.drawable.ic_error)
	    	.cacheInMemory(true)
	    	.cacheOnDisc(true)
	    	.build();
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.defaultDisplayImageOptions(options)
			.build();
        
        imageLoader.init(config);
        
    }
	

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
	            // This ID represents the Home or Up button. In the case of this
	            // activity, the Up button is shown. Use NavUtils to allow users
	            // to navigate up one level in the application structure. For
	            // more details, see the Navigation pattern on Android Design:
	            //
	            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
	            //
	            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
