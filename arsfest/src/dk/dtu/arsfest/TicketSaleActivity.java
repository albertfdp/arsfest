package dk.dtu.arsfest;

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
import dk.dtu.arsfest.cards.SaleCardHeader;
import dk.dtu.arsfest.navigation.SideNavigation;
import android.os.Bundle;
import android.widget.ScrollView;


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
		
		iniPriceCard();
		iniHeader();
		
	}
	
	private void iniPriceCard() {
		/*Card card = new Card(this);
		SaleCardHeader saleCardHeader = new SaleCardHeader(this);
		card.addCardHeader(saleCardHeader);
		
		CardView cardView = (CardView) findViewById(R.id.card_price);
        cardView.setCard(card);*/
		PriceTicketCard card = new PriceTicketCard(this);
        CardView cardView = (CardView) findViewById(R.id.card_price);
        cardView.setCard(card);
		
	}
	
	private void iniHeader(){
		//Create a Card
		  Card card = new Card(this);

		  //Create a CardHeader
		  CardHeader header = new CardHeader(this);

		  //Add header to card
		  card.addCardHeader(header);

		
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
