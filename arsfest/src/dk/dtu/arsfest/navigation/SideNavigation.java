package dk.dtu.arsfest.navigation;

import android.content.Context;
import android.content.Intent;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;

import dk.dtu.arsfest.MainActivity;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.TicketSaleActivity;
import dk.dtu.arsfest.localization.MapActivity;
import dk.dtu.arsfest.utils.Constants;

public class SideNavigation {

	private SideNavigationView sideNavigationView;
	private Context mContext;
	private Intent intent = new Intent();

	public SideNavigation(SideNavigationView sideNavigationView,
			Context context) {
		this.sideNavigationView = sideNavigationView;
		this.sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
		this.mContext = context;
	}

	public void getSideNavigation(Mode mode) {
		sideNavigationView.setMenuClickCallback(new ISideNavigationCallback() {
			@Override
			public void onSideNavigationItemClick(int itemId) {
				switch (itemId) {
				case R.id.side_navigation_events:
					intent = new Intent(mContext, MainActivity.class);
					intent.putExtra(Constants.EXTRA_EVENT_SHOW_FINISHED, false);
					break;
				case R.id.side_navigation_map:
					intent = new Intent(mContext, MapActivity.class);
					break;
				/*case R.id.side_navigation_old_events:
					intent = new Intent(mContext, MainActivity.class);
					intent.putExtra(Constants.EXTRA_EVENT_SHOW_FINISHED, true);
					break;*/
				case R.id.side_navigation_tickets:
					intent = new Intent(mContext, TicketSaleActivity.class);
					intent.putExtra(Constants.EXTRA_TICKET_SALE_ACTIVITY, "");
					break;
				default:
					break;
				}
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				mContext.startActivity(intent);

			}
		});
		sideNavigationView.setMode(mode);
	}
}
