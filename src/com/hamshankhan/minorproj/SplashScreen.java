package com.hamshankhan.minorproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread splashThread = new Thread() {
        	@Override
        	public void run() {
        	//sets time delay for changing to main menu
        	try {
        		Log.i("RIapp", "Waiting Started");
        		int waited = 0;
        		while (waited < 5000) {
        		sleep(100);
        		waited += 100;
        		}
        		Log.i("RIapp", "Waiting Completed");
        	} catch (InterruptedException e) {
        	// do nothing
        	}
        	finally {
        	finish();
        	Log.i("RIapp", "Creating Intent");
        	Intent i = new Intent();
        	Log.i("RIapp", "Intent Created");
        	i.setClassName("com.hamshankhan.minorproj","com.hamshankhan.minorproj.MainMenu");
        	startActivity(i);
        	}
        }
        };
        Log.i("RIapp", "Starting thread");
        splashThread.start();
    }
}