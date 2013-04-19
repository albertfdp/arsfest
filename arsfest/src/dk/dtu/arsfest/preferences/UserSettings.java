package dk.dtu.arsfest.preferences;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;
import android.widget.TextView;

public class UserSettings extends PreferenceActivity {
	
	private TextView headerTitle;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// hide title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		setContentView(R.layout.preferences_layout);
		
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		headerTitle.setText(Constants.APP_NAME);
	}

}
