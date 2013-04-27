package dk.dtu.arsfest.maps;

public class MapScroller {

	private int bmpWidth = 0, bmpHeight = 0, bmpScrollX = 0, bmpScrollY = 0;
	private double currentScale = 1;
	private String currentHTML = "mapDefault.html";
	
	public String getCurrentHTML() {
		return currentHTML;
	}

	/**
	 * 
	 * @return This function returns the intended X scroll depending on location
	 *         stated in the constructor
	 * @author AA
	 */
	public int getBmpScrollX() {
		return bmpScrollX;
	}

	/**
	 * 
	 * @return This function returns the intended Y scroll depending on location
	 *         stated in the constructor
	 * @author AA
	 */
	public int getBmpScrollY() {
		return bmpScrollY;
	}	

	/**
	 * @param scrollLocation
	 *            The intended scroll location
	 * @param measuredWidth
	 *            Pass the width of the view
	 * @param measuredHeight
	 *            Pass the height of the view
	 * @param theScale
	 *            Pass current scale of the view
	 * @author AA
	 */
	public MapScroller(String scrollLocation, int measuredWidth,
			int measuredHeight, double theScale) {
		bmpWidth = measuredWidth / 2;
		bmpHeight = measuredHeight / 2;
		currentScale = theScale;
		setInitialScroll(scrollLocation);
	}

	private void setInitialScroll(String scrollLocation) {
		if (scrollLocation.equals("Library") 
				|| scrollLocation.equals("l1")) {
			bmpScrollX = (int) (762 * currentScale - bmpWidth);
			bmpScrollY = (int) (537 * currentScale - bmpHeight);
			correctX(bmpScrollX);
			correctY(bmpScrollY);
			currentHTML = "mapLibrary.html";
		} else if (scrollLocation.equals("Oticon Hall")
				|| scrollLocation.equals("l2")) {
			bmpScrollX = (int) (2098 * currentScale - bmpWidth);
			bmpScrollY = (int) (224 * currentScale - bmpHeight);
			correctX(bmpScrollX);
			correctY(bmpScrollY);
			currentHTML = "mapOticon.html";
		} else if (scrollLocation.equals("Kantine")
				|| scrollLocation.equals("l3")) {
			bmpScrollX = (int) (1552 * currentScale - bmpWidth);
			bmpScrollY = (int) (660 * currentScale - bmpHeight);
			correctX(bmpScrollX);
			correctY(bmpScrollY);
			currentHTML = "mapKantine.html";
		} else if (scrollLocation.equals("Sports Hall")
				|| scrollLocation.equals("l0")) {
			bmpScrollX = (int) (2129 * currentScale - bmpWidth);
			bmpScrollY = (int) (1066 * currentScale - bmpHeight);
			correctX(bmpScrollX);
			correctY(bmpScrollY);
			currentHTML = "mapSportshal.html";
		}
	}

	private void correctY(int Y) {
		if (Y < 0) {
			bmpScrollY = 0;
		} else if (Y + bmpHeight * 2 > 1671 * currentScale) {
			bmpScrollY = (int) ((1671 * currentScale) - bmpHeight * 2);
			if (bmpScrollY < 0) {
				bmpScrollY = 0;
			}
		}
	}

	private void correctX(int X) {
		if (X < 0) {
			bmpScrollX = 0;
		} else if (X + bmpWidth * 2 > 2482 * currentScale) {
			bmpScrollX = (int) ((2482 * currentScale) - bmpWidth * 2);
			if (bmpScrollY < 0) {
				bmpScrollY = 0;
			}
		}
	}
}