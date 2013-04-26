package dk.dtu.arsfest.notification;

public class UnderstandableLocations {

	private String resultLocation;

	public UnderstandableLocations(String inputLocation) {
		this.resultLocation = giveMeUnderstandableLocation(inputLocation);
	}

	private String giveMeUnderstandableLocation(String location) {
		if (location.equals("l0")) {
			return "Sportshal";
		} else if (location.equals("l1")) {
			return "Library";
		} else if (location.equals("l2")) {
			return "Oticon";
		} else if (location.equals("l3")) {
			return "Kantine";
		}
		return null;
	}

	public String getResultLocation() {
		return resultLocation;
	}
}
