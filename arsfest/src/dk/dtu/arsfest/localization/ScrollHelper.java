package dk.dtu.arsfest.localization;

import dk.dtu.arsfest.utils.Constants;

public class ScrollHelper {

	private int[] measuredDimentions, mScroll;
	private double mScale = 1;

	public int[] getScroll() {
		return mScroll;
	}

	public ScrollHelper(int[] intendedScroll, int[] measuredDimentions, double s) {
		this.mScroll = new int[2];
		this.measuredDimentions = measuredDimentions;
		this.mScale = s;
		setInitialScroll(intendedScroll);
	}

	private void setInitialScroll(int[] intendedScroll) {
		for (int i = 0; i < 2; i++) {
			mScroll[i] = (int) mCorrect(
					(intendedScroll[i] * mScale - measuredDimentions[i] / 2), i);
		}
	}

	private double mCorrect(double d, int i) {
		if (d + measuredDimentions[i] > Constants.MapDimentions[i] * mScale) {
			d = (Constants.MapDimentions[i] * mScale) - measuredDimentions[i];
		}
		if (d < 0) {
			d = 0;
		}
		return d;
	}
}