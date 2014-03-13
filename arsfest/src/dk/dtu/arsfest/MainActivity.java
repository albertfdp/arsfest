package dk.dtu.arsfest;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;
import com.google.analytics.tracking.android.EasyTracker;

import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.FileCache;
import dk.dtu.arsfest.utils.PicassoThumbnail;

public class MainActivity extends BaseActivity {
	
	private SideNavigationView sideNavigationView;
	
	private ArrayList<Location> locations;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
	    sideNavigationView.setMenuClickCallback(new ISideNavigationCallback() {
			@Override
			public void onSideNavigationItemClick(int itemId) {}
		});
	    
	    sideNavigationView.setMode(Mode.LEFT);
	    
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setHomeButtonEnabled(true);
	    getSupportActionBar().setTitle("Events");
	    
	    readProgramme();
	    
	}
	
	private void readProgramme() {
		locations = new ArrayList<Location>();
		try {
			locations = FileCache.readCacheFile(MainActivity.this, Constants.JSON_CACHE_FILENAME);
		} catch (IOException e) {
			Log.e(Constants.TAG, "Could not read file from cache. Reading from assets, could be not updated");
			Log.e(Constants.TAG, e.getMessage());
			// TODO : locations = FileCache.readFromAssets();
		}
		
		for (Location location : locations) {
			location.sortEventsByTime();
		}
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
		
		Card card = new Card(this, R.layout.card_event_inner);
		card.setTitle("What time");
		card.setSwipeable(true);
		card.setOnClickListener(new Card.OnCardClickListener() {
			
			@Override
			public void onClick(Card card, View view) {
				Toast.makeText(MainActivity.this,"Clickable card", Toast.LENGTH_LONG).show();
			}
		});
		
		CardHeader cardHeader = new CardHeader(this);
		cardHeader.setTitle("This is fucking awesome");
		card.addCardHeader(cardHeader);
		
		PicassoThumbnail thumbnail = new PicassoThumbnail(this);
		card.addCardThumbnail(thumbnail);
		
		CardView cardView = (CardView) findViewById(R.id.carddemo1);
		cardView.setCard(card);
		
	}
		
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onStop();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add(R.string.action_search)
			.setIcon(R.drawable.ic_action_search)
			.setActionView(R.layout.collapsible_edittext)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case android.R.id.home:
		        sideNavigationView.toggleMenu();
		        break;
		    default:
		        return super.onOptionsItemSelected(item);
		    }
		    return true;
	}
	
}