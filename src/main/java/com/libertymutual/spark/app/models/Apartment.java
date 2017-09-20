package com.libertymutual.spark.app.models;

import org.javalite.activejdbc.Model;

public class Apartment extends Model {
	
	public Apartment() {}

	public Apartment(int rent, int numberOfBedrooms, double numberOfBathrooms, int squareFootage, String street, String city,
			String state, String zipCode) {
		setRent(rent);
		setNumberOfBathrooms(numberOfBathrooms);
		setNumberOfBedrooms(numberOfBedrooms);
		setSquareFootage(squareFootage);
		setStreet(street);
		setCity(city);
		setState(state);
		setZipCode(zipCode);
	}

	public int getNumberOfBedrooms() {
		return getInteger("number_of_bedrooms");
	}

	public void setNumberOfBedrooms(int numberOfBedrooms) {
		set("number_of_bedrooms", numberOfBedrooms);
	}

	public double getNumberOfBathrooms() {
		return getInteger("number_of_bathrooms");
	}

	public void setNumberOfBathrooms(double numberOfBathrooms) {
		set("number_of_bathrooms", numberOfBathrooms);
	}

	public long getSquareFootage() {
		return getInteger("square_footage");
	}

	public void setSquareFootage(int squareFootage) {
		set("square_footage",  squareFootage);
	}

	public String getStreet() {
		return getString("street");
	}

	public void setStreet(String street) {
		set("street", street);
	}

	public String getCity() {
		return getString("city");
	}

	public void setCity(String city) {
		set("city", city);
	}

	public String getState() {
		return getString("state");
	}

	public void setState(String state) {
		set("state", state);
	}

	public String getZipCode() {
		return getString("zip_code");
	}

	public void setZipCode(String zipCode) {
		set("zip_code", zipCode);
	}

	public int getRent() {
		return getInteger("rent");
	}

	public void setRent(int rent) {
		set("rent", rent);
	}

}
