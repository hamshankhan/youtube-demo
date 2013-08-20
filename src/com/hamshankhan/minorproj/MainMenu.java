package com.hamshankhan.minorproj;

import android.app.Activity; 
import android.content.Intent;
import android.hardware.SensorListener; 
import android.hardware.SensorManager; 
import android.os.Bundle; 
import android.util.Log; 
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
@SuppressWarnings("deprecation") 
public class MainMenu extends Activity implements SensorListener { 
private static final String TAG = "SensorDemo"; 
private SensorManager sensorManager; 
private TextView x_val;
private TextView y_val;
private TextView z_val;
//private TextView outView;
private int sensor = SensorManager.SENSOR_ACCELEROMETER; 

/** Called when the activity is first created. */ 
@Override 
public void onCreate(Bundle savedInstanceState) { 
	super.onCreate(savedInstanceState); 
		setContentView(R.layout.mainscreen); 
		Log.i(TAG, "Display Screen created");
		x_val = (TextView) findViewById(R.id.x_acc_value);
		y_val = (TextView) findViewById(R.id.y_acc_value);
		z_val = (TextView) findViewById(R.id.z_acc_value);
		//outView = (TextView) findViewById(R.id.promptmsg);
		// Read sensor manager 
		Log.i(TAG, "Getting the Instance of Sensor Service");
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
	} 

/** Register for the updates when Activity is in foreground */ 
@Override 
protected void onResume() { 
	super.onResume(); 
		Log.d(TAG, "onResume"); 
		sensorManager.registerListener(this, sensor); 
	} 

/** Stop the updates when Activity is paused */ 
@Override 
//what happens when the sensors are paused 
protected void onPause() { 
	super.onPause(); 
		Log.d(TAG, "onPause"); 
		sensorManager.unregisterListener(this, sensor); 
	}


public void onAccuracyChanged(int sensor, int accuracy) { 
	Log.d(TAG, String.format("onAccuracyChanged sensor: %d accuraccy: %d", sensor, accuracy)); 
} 

public void onSensorChanged(int sensorReporting, float[] values) { 
	if (sensorReporting != sensor) 
		return; 
	//define variables 
	float azimuth = Math.round(values[0]); 
	float pitch = Math.round(values[1]); 
	float roll = Math.round(values[2]); 
	//output sensor data 
	String out = String.format("X: %.2f\nY:%.2f\nZ %.2f", azimuth, pitch, roll);
	Log.d(TAG, out); 
	x_val.setText(String.valueOf((int)azimuth));
	y_val.setText(String.valueOf((int)pitch));
	z_val.setText(String.valueOf((int)roll));
	//define threshold 
	if (roll > -5) { 
		//display text on threshold being passed 
	
	   	/*String out1 = String.format("POTHOLE!!!!"); 
    	Log.d(TAG, out1); 
    	outView.setText(out1);*/
    	
    	Toast.makeText(getApplicationContext(), R.string.msg, Toast.LENGTH_SHORT).show();
    	
    	//intent switches to location.java to read current location 
    	
    	/*Thread locationThread = new Thread() {
        	@Override
        	public void run() {
        		Intent i = new Intent();
        	Log.i("RIapp", "Intent Created");
        	i.setClassName("com.hamshankhan.minorproj","com.hamshankhan.minorproj.location");
        	startActivity(i);
        	}
        };
        Log.i("RIapp", "Starting thread");
        locationThread.start();*/
        
	Intent myIntent = new Intent(this, location.class); 
	startActivity(myIntent); 
		}

	}

@Override
public boolean onPrepareOptionsMenu(Menu menu) {
	 super.onCreateOptionsMenu(menu);
	 menu.clear();
	 menu.add(0, 1, Menu.NONE, "Start").setIcon(android.R.drawable.ic_media_play);
	 menu.add(0, 2, Menu.NONE, "Stop").setIcon(android.R.drawable.ic_media_pause);
	 menu.add(0, 3, Menu.NONE, "View").setIcon(android.R.drawable.ic_menu_view);
	 menu.add(0, 4, Menu.NONE, "Quit").setIcon(android.R.drawable.ic_lock_power_off);
	 return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case 1:
            onResume();
            return true;
        case 2:
            onPause();
            return true;
        case 3:
        	startActivity(new Intent(this,LocViewer.class));
        	return true;
        case 4:
            finish();
            return true;
    }
    return false;
}

}
