package dk.dtu.arsfest;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;
import com.google.analytics.tracking.android.EasyTracker;

import dk.dtu.arsfest.cards.EventDescriptionCard;
import dk.dtu.arsfest.cards.EventImageCard;
import dk.dtu.arsfest.cards.EventInfoCard;
import dk.dtu.arsfest.cards.PriceTicketCard;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.navigation.SideNavigation;
import android.os.Bundle;
import android.widget.ScrollView;

import dk.dtu.arsfest.utils.*;


public class TicketSaleActivity extends BaseActivity {

	private SideNavigationView sideNavigationView;	
	private ScrollView  mScrollView;

	private Event saleEvent;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_sale);

		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		SideNavigation mSideNavigation = new SideNavigation(sideNavigationView,
				getApplicationContext());
		mSideNavigation.getSideNavigation(Mode.LEFT);

	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setHomeButtonEnabled(true);
	    getSupportActionBar().setTitle("Ticket Sale Information");
	    
	    saleEvent = findSaleEvent(Utils.getProgramme(TicketSaleActivity.this));
	    
	    mScrollView = (ScrollView) findViewById(R.id.sale_scrollview);
	    createCards();
	    
	}
	
	private Event findSaleEvent(ArrayList<Location> programme) {		
		Event eventAux = null;
		for (Location location : programme) {
			for (Event event : location.getEvents()) {
				if(event.getType().equals(Constants.EVENT_TYPE_SALE)){
					event.setParent(location);
					eventAux = event;
					eventAux.setEndTime(null);
				}
			}
		}
		return eventAux;
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
	
	private void createCards() {
		
		initCardImage();
		iniCardEventInfo();
		iniPriceCard();
		iniDescriptionCard();

		
	}
	
	private void initCardImage() {
		Card eventImageCard = new EventImageCard(this, saleEvent);		
		eventImageCard.setTitle(saleEvent.getName());		
		CardView cardView = (CardView) findViewById(R.id.card_event_image);
		cardView.setCard(eventImageCard);
	}
	
	private void iniCardEventInfo(){
		Card info = new EventInfoCard(this, saleEvent);
		CardView infoCardView = (CardView) findViewById(R.id.card_sale_info);
		infoCardView.setCard(info);
	}
	
	private void iniPriceCard() {
		Card card = new PriceTicketCard(this,saleEvent);
        CardView cardView = (CardView) findViewById(R.id.card_price);
        cardView.setCard(card);
		
	}
	
	private void iniDescriptionCard() {
		Card card = new EventDescriptionCard(this,saleEvent);
        CardView cardView = (CardView) findViewById(R.id.card_sale_description);
        cardView.setCard(card);
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
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
