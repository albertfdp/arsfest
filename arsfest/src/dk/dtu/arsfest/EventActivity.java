package dk.dtu.arsfest;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;

import dk.dtu.arsfest.cards.EventImageCard;
import dk.dtu.arsfest.cards.EventInfoCard;
import dk.dtu.arsfest.cards.EventMapCard;
import dk.dtu.arsfest.cards.EventMenuCard;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;

public class EventActivity extends BaseActivity {
	
	private Event event;

	private SideNavigationView sideNavigationView;
	
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
	    
	    Intent intent = getIntent();
	    this.event = (Event) intent.getParcelableExtra(Constants.EXTRA_EVENT);
	    
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setTitle(event.getName());
	    initView();
	    
	}
	
	private void initView() {
		
		Card eventImageCard = new EventImageCard(this, event);		
		eventImageCard.setTitle(event.getName());		
		CardView cardView = (CardView) findViewById(R.id.card_event_image);
		cardView.setCard(eventImageCard);
		
		Card info = new EventInfoCard(this, event);
		CardView infoCardView = (CardView) findViewById(R.id.card_event_info);
		infoCardView.setCard(info);
		
		//if (event.isMenu()) {
			Card menuCard = new EventMenuCard(this, event);
			CardView menuCardView = (CardView) findViewById(R.id.card_event_menu);
			menuCardView.setCard(menuCard);
		/*else {
			Card descriptionCard = new EventDescriptionCard(this, event);
			CardView descriptionCardView = (CardView) findViewById(R.id.card_event_menu);
			descriptionCardView.setCard(descriptionCard);
		}*/
		
		Card mapCard = new EventMapCard(this, event);
		CardView mapCardView = (CardView) findViewById(R.id.card_event_map);
		mapCardView.setCard(mapCard);
		
				
		
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
