package com.hamshankhan.minorproj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
public class location extends Activity implements LocationListener {
	private TextView latituteField;
	private TextView longitudeField;
	private LocationManager locationManager;
	private String provider;
	private LocAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			
			Log.i("loc", "Location activity started");
			
			latituteField = (TextView) findViewById(R.id.TextView02);
			longitudeField = (TextView) findViewById(R.id.TextView04);
	// Get the location manager
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	// Define the criteria how to select the location provider -> use default
			
			Criteria criteria = new Criteria();
			
			provider = locationManager.getBestProvider(criteria, false);
			
			Location location = locationManager.getLastKnownLocation(provider);
	
	// Initialize the location fields
			
			if (location != null) {
				//System.out.println("Provider " + provider + " has been selected.");
				double lat = (location.getLatitude());
				double lng = (location.getLongitude());
				Log.i("loc", "Location Fetched");
				latituteField.setText(String.valueOf(lat));
				longitudeField.setText(String.valueOf(lng));				
			} else {
				latituteField.setText("Provider not available");
				longitudeField.setText("Provider not available");
			}
	}
	
	private void saveLoc(Loc l)
	{
		adapter = MyApplication.getDbAdapter();
		adapter.open(LocAdapter.writeMode);
		adapter.save(l);
		adapter.close();
	}
	
	
	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		Log.i("loc", "Location Activity Resume");
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		Log.i("loc", "Location Activity Started");
		locationManager.removeUpdates(this);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		
		//gets the lat and long varriables and stores as doubles
		double lat = (location.getLatitude());
		double lng = (location.getLongitude());
		
		//defines output (lat and long)
		latituteField.setText(String.valueOf(lat));
		longitudeField.setText(String.valueOf(lng));
		
		Loc l = new Loc(lat,lng);
		
		saveLoc(l);
		
		Toast.makeText(getApplicationContext(), R.string.savemsg, Toast.LENGTH_SHORT).show();
		
		Thread waitThread = new Thread() {
        	@Override
        	public void run() {
        	//sets time delay for changing to main menu
        	try {
        		Log.i("loc", "Waiting Started");
        		int waited = 0;
        		while (waited < 5000) {
        		sleep(100);
        		waited += 100;
        		}
        		Log.i("loc", "Waiting Completed");
        	} catch (InterruptedException e) {
        	// do nothing
        	}
        	finally {
        	finish();
        	Log.i("loc", "Creating Intent");
        	Intent i = new Intent();
        	Log.i("RIapp", "Intent Created");
        	i.setClassName("com.hamshankhan.minorproj","com.hamshankhan.minorproj.MainMenu");
        	startActivity(i);
        	}
        }
        };
        Log.i("RIapp", "Starting thread");
        waitThread.start();
		
		//this is supposed to return to mainmenu.java
		/*Intent myIntent = new Intent(outView.getContext(), MainMenu.class);
		startActivityForResult(myIntent, 0);*/
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disenabled provider " + provider, Toast.LENGTH_SHORT).show();
	}
}