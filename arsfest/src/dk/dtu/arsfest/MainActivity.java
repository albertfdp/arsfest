package dk.dtu.arsfest;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import java.util.ArrayList;
import java.util.Collections;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;
import com.google.analytics.tracking.android.EasyTracker;

import dk.dtu.arsfest.cards.HappenningNowCard;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.navigation.SideNavigation;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.UniversalImageLoaderThumbnail;
import dk.dtu.arsfest.utils.Utils;

public class MainActivity extends BaseActivity {
	
	private SideNavigationView sideNavigationView;
	
	private ArrayList<Location> locations;
	private ArrayList<Event> events = new ArrayList<Event>();
	
	//private HappenningNowCard happeningNowCard;
	//private CardView happeningNowCardView;
	private ArrayList<Card> cards;
	
	private boolean showFinishedEvents = false;
	private CardArrayAdapter cardArrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		SideNavigation mSideNavigation = new SideNavigation(sideNavigationView,
				getApplicationContext());
		mSideNavigation.getSideNavigation(Mode.LEFT);

	    Intent intent = getIntent();
	    this.showFinishedEvents = intent.getBooleanExtra(Constants.EXTRA_EVENT_SHOW_FINISHED, false);
	    
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setHomeButtonEnabled(true);
	    getSupportActionBar().setTitle("Events");
	    
	    
	    readProgramme();
	    
//	    Event happeningNow = getHappenningNow();
//	    if (happeningNow != null)
//	    	onHappenningNow(happeningNow);
	    
	    if (events.isEmpty()) {
	    	createArsfestFinishedCard();
	    } else {
	    	createCards();
	    }
	}
	
	/*private Event getHappenningNow() {
		if (!events.isEmpty())
			return events.get(1);
		return null;
	}*/
	
	private void readProgramme() {
		
		locations = Utils.getProgramme(MainActivity.this);
		
		for (Location location : locations) {
			for (Event event : location.getEvents()) {
				
				event.setParent(location);
				events.add(event);
				
			}
		}
		
		// sort events by start time
		Collections.sort(events, Event.START_TIME);
		
		// remove finished, if needed
		if (!showFinishedEvents) {
			ArrayList<Event> finishedEvents = new ArrayList<Event>();
			for (Event event : events) {
				if (event.hasFinished())
					finishedEvents.add(event);
			}
			events.removeAll(finishedEvents);
		} else { // remove not finished
			ArrayList<Event> notFinished = new ArrayList<Event>();
			for (Event event : events) {
				if (!event.hasFinished())
					notFinished.add(event);
			}
			events.removeAll(notFinished);
		}
		
	}
	
	private void createArsfestFinishedCard() {
		cards = new ArrayList<Card>();
		
		Card card = new Card(this);
		card.setTitle("aLL EVENTS ARE FINISHED DUDE!");
		
		cards.add(card);
		
		CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this, cards);
		CardListView cardsView = (CardListView) findViewById(R.id.arsfest_events_list);
		
		if (cardsView != null) cardsView.setAdapter(cardArrayAdapter);
	}
	
	private void createCards() {
		cards = new ArrayList<Card>();
		
		for (Event event : events) {
			
			Card card = new HappenningNowCard(this, event);
			card.setTitle(event.getName());
			
			UniversalImageLoaderThumbnail cardThumbnail = new UniversalImageLoaderThumbnail(this);
			cardThumbnail.setUrl(event.getImage());
			card.addCardThumbnail(cardThumbnail);
			
			card.setShadow(true);
			
			cards.add(card);
		}
		
		cardArrayAdapter = new CardArrayAdapter(this,cards);
		CardListView cardsView = (CardListView) findViewById(R.id.arsfest_events_list);

		if (cardsView != null) cardsView.setAdapter(cardArrayAdapter);
		
	}
	
//	private void onHappenningNow(Event event) {
//		happeningNowCard = new HappenningNowCard(this, event);
//		
//		CardHeader cardHeader = new HappeningNowHeader(this);
//		cardHeader.setTitle("Happening now");
//		happeningNowCard.addCardHeader(cardHeader);
//		
//		UniversalImageLoaderThumbnail cardThumbnail = new UniversalImageLoaderThumbnail(this);
//		cardThumbnail.setUrl(event.getImage());
//		happeningNowCard.addCardThumbnail(cardThumbnail);
//		
//		happeningNowCard.setShadow(true);
//		
//		happeningNowCardView = (CardView) findViewById(R.id.card_happening_now);
//		happeningNowCardView.setCard(happeningNowCard);
//		
//		
//	}
	
//	private void createCards() {
//		cards = new ArrayList<Card>();
//		
//		for (Event event : events) {
//			Card card = new Card(this, R.layout.card_event_inner);
//			card.setTitle(event.getStartTime().toString());
//			
//			CardHeader cardHeader = new CardHeader(this);
//			cardHeader.setTitle(event.getName());
//			
//			//PicassoThumbnail cardThumbnail = new PicassoThumbnail(this);
//			UniversalImageLoaderThumbnail cardThumbnail = new UniversalImageLoaderThumbnail(this, options);
//			cardThumbnail.setUrl(event.getImage());
//			
//			card.addCardHeader(cardHeader);
//			card.addCardThumbnail(cardThumbnail);
//			
//			card.setSwipeable(true);
//			
//			cards.add(card);
//		}
//		
//		CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this, cards);
//		CardListView cardsView = (CardListView) findViewById(R.id.event_list);
//		if (cardsView != null) cardsView.setAdapter(cardArrayAdapter);
//		
//	}

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
		
		final SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
		searchView.setQueryHint("Search");
		
		menu.add(R.string.action_search)
			.setIcon(R.drawable.ic_action_search)
			//.setActionView(R.layout.collapsible_edittext)
			.setActionView(searchView)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		
		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener(){
			
			@Override
			public boolean onQueryTextSubmit(String query) {			
				cardArrayAdapter.getFilter().filter(query);
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				cardArrayAdapter.getFilter().filter(newText);
				return true;
			}
		};
		
		searchView.setOnQueryTextListener(queryTextListener);
		
		return super.onCreateOptionsMenu(menu);
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