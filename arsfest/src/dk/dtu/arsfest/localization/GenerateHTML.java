package dk.dtu.arsfest.localization;

import dk.dtu.arsfest.utils.Constants;

public class GenerateHTML {

	private String mHTML = "";
	private String mScripts = "function position(mLeft, mTop) {"
			+ "var el = document.getElementById('gps');"
			+ "el.style.display = 'block';"
			+ "el.style.marginLeft = ((mLeft-32) + 'px');"
			+ "el.style.marginTop = ((mTop-32) + 'px');"
			+ "}";
	private String mScripts2 = "function rotation(mDegree) {"
			+ "var el = document.getElementById('gps');"
			+ "el.style.webkitTransform = 'rotate('+mDegree+'deg)';" 
			+ "}";
	private String mScripts3 = "function hide() {"
			+ "var el = document.getElementById('gps');"
			+ "el.style.display = 'none';" 
			+ "}";

	public GenerateHTML(int[] pinPosition, boolean showPIN) {
		mHTML += "<html>"
				+ "<head>"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\" />"
				+ "<script>" + mScripts + mScripts2 + mScripts3 + "</script>"
				+ "<style type='text/css'>";
		if (showPIN) {
			mHTML += "#pin {" + "position:absolute;" + "margin:"
					+ (pinPosition[1] - 100) + "px 0 0 "
					+ (pinPosition[0] - 42) + "px;" + "}";
		} else {
			mHTML += "#pin {display:none}";
		}
		mHTML += "#gps {display:none;position:absolute;margin:0;" +
				//"-webkit-transform: rotate(30deg);" +
				"}";
		mHTML += "body {background-image: url(map.png);height:"
				+ Constants.MapDimentions[1] + "px;matgin:0;padding:0;width:"
				+ Constants.MapDimentions[0] + "px}";
		mHTML += "</style>";
		mHTML += "<body><img src=\"pin.png\" id=\"pin\"><img src=\"gps_direction.png\" id=\"gps\"></body>";
	}

	public String getmHTML() {
		return mHTML;
	}
}
