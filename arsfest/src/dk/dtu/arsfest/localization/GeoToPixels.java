package dk.dtu.arsfest.localization;

public class GeoToPixels {

	private double aTop, bTop, aLeft, bLeft, lengthRL, lengthTD, x, y,
			topScroll, leftScroll;
	private int[] numberOfPixels;

	public GeoToPixels(double aTop, double bTop, double aLeft, double bLeft,
			double lengthRL, double lengthTD, int[] numberOfPixels, double x,
			double y) {
		this.aTop = aTop;
		this.bTop = bTop;
		this.aLeft = aLeft;
		this.bLeft = bLeft;
		this.lengthRL = lengthRL;
		this.lengthTD = lengthTD;
		this.numberOfPixels = numberOfPixels;
		this.x = x;
		this.y = y;
		this.topScroll = getYDistance();
		this.leftScroll = getXDistance();
	}

	public int getXDistance() {
		double myB = (y - (aTop * x));
		if (myB > bTop) {
			return -1;
		}
		double xTop = (myB - bLeft) / (aLeft - aTop);
		double yTop = aLeft * xTop + bLeft;
		return (int) (((Math.sqrt(Math.pow((x - xTop), 2)
				+ Math.pow((y - yTop), 2))) / lengthRL) * numberOfPixels[0]);
	}

	public int getYDistance() {
		double myB = (y - (aLeft * x));
		if (myB > bLeft) {
			return -1;
		}
		double xLeft = (myB - bTop) / (aTop - aLeft);
		double yLeft = aTop * xLeft + bTop;
		return (int) (((Math.sqrt(Math.pow((x - xLeft), 2)
				+ Math.pow((y - yLeft), 2))) / lengthTD) * numberOfPixels[1]);
	}

	public double[] getScroll() {
		return new double[] { leftScroll, topScroll };
	}
}
