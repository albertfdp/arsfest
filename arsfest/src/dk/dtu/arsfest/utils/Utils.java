package dk.dtu.arsfest.utils;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {
	
	public static Typeface getTypeface(Context context, String typeface) {
		return Typeface.createFromAsset(context.getAssets(), "fonts/" + typeface);
  	}
}
