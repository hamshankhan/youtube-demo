package com.hamshankhan.minorproj;

public class Loc {

	int id;
	double lat, lng;
	
	public Loc(double lat, double lng)
	{
		super();
		this.lat = lat;
		this.lng = lng;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
}
