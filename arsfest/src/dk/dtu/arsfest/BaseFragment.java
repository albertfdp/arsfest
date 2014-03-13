package dk.dtu.arsfest;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public abstract class BaseFragment extends SherlockFragmentActivity {
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle();
    }

    protected void setTitle(){
        getActivity().setTitle(getTitleResourceId());
    }

    public abstract int getTitleResourceId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
	            // This ID represents the Home or Up button. In the case of this
	            // activity, the Up button is shown. Use NavUtils to allow users
	            // to navigate up one level in the application structure. For
	            // more details, see the Navigation pattern on Android Design:
	            //
	            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
	            //
	            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
