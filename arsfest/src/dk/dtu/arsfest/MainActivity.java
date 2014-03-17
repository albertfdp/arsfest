package dk.dtu.arsfest;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;
import com.google.analytics.tracking.android.EasyTracker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import dk.dtu.arsfest.cards.HappeningNowHeader;
import dk.dtu.arsfest.cards.HappenningNowCard;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.FileCache;
import dk.dtu.arsfest.utils.UniversalImageLoaderThumbnail;
import dk.dtu.arsfest.utils.Utils;

public class MainActivity extends BaseActivity {
	
	private SideNavigationView sideNavigationView;
	
	private ArrayList<Location> locations;
	private ArrayList<Event> events = new ArrayList<Event>();
	
	private HappenningNowCard happeningNowCard;
	private CardView happeningNowCardView;
	private ArrayList<Card> cards;
	
	private DisplayImageOptions options;

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
	    
	    options = new DisplayImageOptions.Builder()
	    	.showImageOnLoading(R.drawable.ic_empty)
	    	.showImageOnFail(R.drawable.ic_error)
	    	.cacheInMemory(true)
	    	.cacheOnDisc(true)
	    	.build();
	    
	    readProgramme();
	    onHappenningNow(events.get(0));
	    //createCards();
	}
	
	private void readProgramme() {
		locations = new ArrayList<Location>();
		try {
			locations = FileCache.readCacheFile(MainActivity.this, Constants.JSON_CACHE_FILENAME);
		} catch (IOException e) {
			Log.e(Constants.TAG, "Could not read file from cache. Reading from assets, could be not updated");
			Log.e(Constants.TAG, e.getMessage());
			locations = FileCache.readFromAssets(MainActivity.this);
		}
		
		for (Location location : locations) {
			events.addAll(location.getEvents());
			location.sortEventsByTime();
		}
		
	}
	
	private void onHappenningNow(Event event) {
		happeningNowCard = new HappenningNowCard(this);
		
		CardHeader cardHeader = new HappeningNowHeader(this);
		cardHeader.setTitle("Happening now");
		happeningNowCard.addCardHeader(cardHeader);
		
		UniversalImageLoaderThumbnail cardThumbnail = new UniversalImageLoaderThumbnail(this, options);
		cardThumbnail.setFilename(event.getImage());
		happeningNowCard.addCardThumbnail(cardThumbnail);
		
		happeningNowCard.setTitle(event.getName());
		happeningNowCard.setMember(Utils.getEventTime(event.getStartTime()));
				
		happeningNowCardView = (CardView) findViewById(R.id.card_happening_now);
		happeningNowCardView.setCard(happeningNowCard);
		
		
	}
	
	private void createCards() {
		cards = new ArrayList<Card>();
		
		for (Event event : events) {
			Card card = new Card(this, R.layout.card_event_inner);
			card.setTitle(event.getStartTime().toString());
			
			CardHeader cardHeader = new CardHeader(this);
			cardHeader.setTitle(event.getName());
			
			//PicassoThumbnail cardThumbnail = new PicassoThumbnail(this);
			UniversalImageLoaderThumbnail cardThumbnail = new UniversalImageLoaderThumbnail(this, options);
			cardThumbnail.setFilename(event.getImage());
			cardThumbnail.setExternalUsage(true);
			
			card.addCardHeader(cardHeader);
			card.addCardThumbnail(cardThumbnail);
			
			card.setSwipeable(true);
			
			cards.add(card);
		}
		
		CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this, cards);
		CardListView cardsView = (CardListView) findViewById(R.id.event_list);
		if (cardsView != null) cardsView.setAdapter(cardArrayAdapter);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);		
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