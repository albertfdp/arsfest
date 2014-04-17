package dk.dtu.arsfest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Price implements Parcelable {
	
	private String name;
	private Double price;
	private String currency;
	
	public Price(String name, Double price, String currency) {
		super();
		this.name = name;
		this.price = price;
		this.currency = currency;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public Price (Parcel read) {
		this.name = read.readString();
		this.price = read.readDouble();
		this.currency = read.readString();
	}
	
	public static final Parcelable.Creator<Price> CREATOR = 
			new Parcelable.Creator<Price>() {

				@Override
				public Price createFromParcel(Parcel source) {
					return new Price(source);
				}

				@Override
				public Price[] newArray(int size) {
					return new Price[size];
				}
		
			};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeDouble(price);
		dest.writeString(currency);
	}
	
	
	
}
