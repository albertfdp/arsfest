package dk.dtu.arsfest;

import java.util.Date;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;
import com.google.analytics.tracking.android.EasyTracker;

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
	    
	    mScrollView = (ScrollView) findViewById(R.id.sale_scrollview);
	    createCards();
	    
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
		
		iniCardEventInfo();
		iniPriceCard();

		
	}
	
	private void iniCardEventInfo(){
		//******************************************************
		Date date1 = Utils.getFormattedDate("25-04-2014:09:00");
		Date date2 = null;
		Location parent = new Location(null, "administration hall in building 101 DTU Lyngby Campus", 0, 0, null, null, null);
		Event event = new Event("","TICKET SALE","",date1,date2,"administration hall in building 101 DTU Lyngby Campus","","","",null,false,parent);
		//******************************************************
		Card info = new EventInfoCard(this, event);
		CardView infoCardView = (CardView) findViewById(R.id.card_sale_info);
		infoCardView.setCard(info);
	}
	
	private void iniPriceCard() {
		Card card = new PriceTicketCard(this);
        CardView cardView = (CardView) findViewById(R.id.card_price);
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
