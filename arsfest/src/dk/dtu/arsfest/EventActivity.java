package dk.dtu.arsfest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;

public class EventActivity extends BaseActivity {
	
	private Event event;
	
	private ImageView eventImage;

	private SideNavigationView sideNavigationView;
	private DisplayImageOptions options;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
	    sideNavigationView.setMenuClickCallback(new ISideNavigationCallback() {
			@Override
			public void onSideNavigationItemClick(int itemId) {}
		});
	    
	    sideNavigationView.setMode(Mode.LEFT);
	    
	    options = new DisplayImageOptions.Builder()
	    	.showImageOnLoading(R.drawable.ic_empty)
	    	.showImageOnFail(R.drawable.ic_error)
	    	.cacheInMemory(true)
	    	.cacheOnDisc(true)
	    	.build();
	    
	    Intent intent = getIntent();
	    this.event = (Event) intent.getParcelableExtra(Constants.EXTRA_EVENT);
	    
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    //getSupportActionBar().setHomeButtonEnabled(true);
	    getSupportActionBar().setTitle(event.getName());
	    initView();
	    
	}
	
	private void initView() {
		
		eventImage = (ImageView) findViewById(R.id.event_image);
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.defaultDisplayImageOptions(options)
			.build();
	
	
		//In real case you should config better the imageLoader
	    ImageLoader imageLoader = ImageLoader.getInstance();
	    imageLoader.init(config);
	    
	    imageLoader.displayImage(Constants.IMG_CONTENT_PROVIDER_URL + this.event.getImage() + ".jpg", (ImageView) eventImage);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case android.R.id.home:
		        finish();
		        break;
		    default:
		        return super.onOptionsItemSelected(item);
		    }
		    return true;
	}
}
