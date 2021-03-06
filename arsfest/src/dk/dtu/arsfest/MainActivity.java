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
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;
import com.google.analytics.tracking.android.EasyTracker;

import dk.dtu.arsfest.cards.FinishedEventCard;
import dk.dtu.arsfest.cards.HappenningNowCard;
import dk.dtu.arsfest.cards.PostEventCard;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.navigation.SideNavigation;
import dk.dtu.arsfest.notification.ArsfestNotification;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.UniversalImageLoaderThumbnail;
import dk.dtu.arsfest.utils.Utils;

public class MainActivity extends BaseActivity {
	
	private SideNavigationView sideNavigationView;
	
	private ArrayList<Location> locations;
	private ArrayList<Event> events = new ArrayList<Event>();
	
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
	    getSupportActionBar().setTitle(getText(R.string.events));
	    
	    
	    readProgramme();
	    
	   if (events.isEmpty()) {
	    	createArsfestFinishedCard();
	    } else {
	    	createCards();
	    }
	    
	    ArsfestNotification mNotification = new ArsfestNotification();
	    mNotification.setAlarm(getApplicationContext());
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
		
	}
	
	private void createArsfestFinishedCard() {
		PostEventCard card = new PostEventCard(this);
		
		
		if(!(cards.get(0) instanceof PostEventCard))
			cards.add(0,card);
		
		CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this, cards);
		CardListView cardsView = (CardListView) findViewById(R.id.arsfest_events_list);
		
		if (cardsView != null) cardsView.setAdapter(cardArrayAdapter);
	}
	
	private void createCards() {
		cards = new ArrayList<Card>();
		Event ticketEvent = null;
		
		for (Event event : events) {
			
			if(!event.getType().equals(Constants.EVENT_TYPE_SALE)){
				Card card = new HappenningNowCard(this, event);
				card.setTitle(event.getName());
				
				UniversalImageLoaderThumbnail cardThumbnail = new UniversalImageLoaderThumbnail(this);
				cardThumbnail.setUrl(event.getImage());
				card.addCardThumbnail(cardThumbnail);
				
				card.setShadow(true);
				
				cards.add(card);
			}
			
			else 
				ticketEvent = event;
			
		}
		
		events.remove(ticketEvent);
		
		cardArrayAdapter = new CardArrayAdapter(this,cards);
		CardListView cardsView = (CardListView) findViewById(R.id.arsfest_events_list);

		if (cardsView != null) cardsView.setAdapter(cardArrayAdapter);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);		
		updateView(cards);
		if (events.isEmpty())
			createArsfestFinishedCard();
	}
		
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateView(cards);
	}

	@Override
	protected void onDestroy() {
		super.onStop();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		/*final SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
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
		
		searchView.setOnQueryTextListener(queryTextListener);*/
		
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
	
	private void updateView(ArrayList<Card> currentCards){
		ArrayList<Event> outdatedEvents;
		
		outdatedEvents = getOutdatedEvents(currentCards);
		removeOutdatedCards(currentCards);
		changeCardView(currentCards, outdatedEvents);
		events.removeAll(outdatedEvents);
		
		CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this, currentCards);
		CardListView cardsView = (CardListView) findViewById(R.id.arsfest_events_list);
		
		if (cardsView != null) cardsView.setAdapter(cardArrayAdapter);
		
		return;
	}
	
	private ArrayList<Event> getOutdatedEvents(ArrayList<Card> currentCards){
		ArrayList<Event> e = new ArrayList<Event>();
		
		for(Card c : currentCards){
			if(((c instanceof HappenningNowCard)) &&((HappenningNowCard) c).getEvent().hasFinished()){
				e.add(((HappenningNowCard) c).getEvent());
			}
		}
		
		return e;
	}
	
	private void removeOutdatedCards(ArrayList<Card> currentCards){
		ArrayList<Card> finishedCards = new ArrayList<Card>();
		
		for(Card c : currentCards){
			if(((c instanceof HappenningNowCard)) &&((HappenningNowCard) c).getEvent().hasFinished()){
				finishedCards.add(c);
			}
		}
		
		currentCards.removeAll(finishedCards);
		
		return;
	}
	
	private void changeCardView(ArrayList<Card> currentCards,ArrayList<Event> outDatedEvents){
		
		for(Event e : outDatedEvents){
			Card card = new FinishedEventCard(this, e);
			card.setTitle(e.getName());
			
			UniversalImageLoaderThumbnail cardThumbnail = new UniversalImageLoaderThumbnail(this);
			cardThumbnail.setUrl(e.getImage());
			card.addCardThumbnail(cardThumbnail);
			
			card.setShadow(true);
			
			currentCards.add(card);
		}
	}

	
}