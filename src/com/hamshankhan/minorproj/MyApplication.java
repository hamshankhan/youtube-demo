package com.hamshankhan.minorproj;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
	
	static LocAdapter dbAdapter;


	@Override
	public void onCreate() {
			super.onCreate();
			dbAdapter=new LocAdapter(this);
			Log.i("locdb","application object created.");
	}


	public static LocAdapter getDbAdapter() {
		Log.i("locdb","adapter object requested from the application.");
		return dbAdapter;
	}

}
