package dk.dtu.arsfest.localization;

import dk.dtu.arsfest.utils.Constants;

public class GenerateHTML {

	private String mHTML = "";
	private String mScripts = "function position(mLeft, mTop) {"
			+ "var el = document.getElementById('gps');"
			+ "el.style.display = 'block';"
			+ "el.style.left = ((mLeft-32) + 'px');"
			+ "el.style.top = ((mTop-32) + 'px');" + "}";
	private String mScripts2 = "function rotation(mDegree) {"
			+ "var el = document.getElementById('gps');"
			+ "el.style.webkitTransform = 'rotate('+mDegree+'deg)';" + "}";
	private String mScripts3 = "function hide() {"
			+ "var el = document.getElementById('gps');"
			+ "el.style.display = 'none';"
			+ "var el = document.getElementById('circle');"
			+ "el.style.display = 'none';" + "}";
	private String mScripts4 = "function pageScroll(mLeft, mTop) {"
			+ "window.scrollTo(mLeft, mTop);" + "}";
	private String mScripts5 = "function showCircle(mLeft, mTop, mRadius) {"
			+ "var el = document.getElementById('circle');"
			+ "el.style.display = 'block';"
			+ "el.style.marginLeft = ((mLeft-mRadius-2) + 'px');"
			+ "el.style.marginTop = ((mTop-mRadius-2) + 'px');"
			+ "el.style.width = ((mRadius*2-4) + 'px');"
			+ "el.style.height = ((mRadius*2-4) + 'px');"
			+ "el.style.WebkitBorderRadius = ((mRadius*2-4) + 'px');" + "}";

	public GenerateHTML(int[] pinPosition, boolean showPIN) {
		mHTML += "<html>"
				+ "<head>"
				+ "<meta name=\"viewport\" content=\"width=device-width\" />"
				+ "<script>" + mScripts5 + mScripts4 + mScripts + mScripts2
				+ mScripts3 + "</script>" + "<style type='text/css'>";
		if (showPIN) {
			mHTML += "#pin {" + "position:absolute;z-index:5;" + "top:"
					+ (pinPosition[1] - 100) + "px;left:"
					+ (pinPosition[0] - 42) + "px;" + "}";
		} else {
			mHTML += "#pin {display:none}";
		}
		mHTML += "#gps {display:none;position:absolute;margin:0;z-index:10"
				+ "}";
		mHTML += "#circle {display:none;background: #F08080;-webkit-opacity: 0.5;border: 2px solid #8B2323;z-index:2;"
				+ "}";
		mHTML += "body {background-image: url(map.png);margin:0;padding:0;background-repeat: no-repeat;height:"
				+ Constants.MapDimentions[1]
				+ "px;matgin:0;padding:0;width:"
				+ Constants.MapDimentions[0] + "px}";
		mHTML += "</style>"
				+ "</head>"
				+ "<body>"
				+ "<div style=\"padding:0;margin:0;overflow:hidden; height:1590px; width:1900px;\"><div id=\"circle\"></div>"
				+ "<img src=\"pin.png\" id=\"pin\">"
				+ "<img src=\"gps_direction.png\" id=\"gps\"></div></body>";
	}

	public String getmHTML() {
		return mHTML;
	}
}
