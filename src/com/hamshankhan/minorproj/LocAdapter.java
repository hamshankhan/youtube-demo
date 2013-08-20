package com.hamshankhan.minorproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class LocAdapter {

	private static final String dbName="locations.db";
	private static final String tableName="loc";
	private static final int dbVersion=1;
	private static final String createQuery="create table loc (_id integer primary key autoincrement, lat REAL, lng REAL);";
	private SQLiteDatabase db;
	public static final int readMode=1;
	public static final int writeMode=2;
	private MyDbHelper helper;
	
	public LocAdapter(Context ctx) {
		
		try{
			helper = new MyDbHelper(ctx, dbName, null, dbVersion);
				Log.i("locdb","helper object created.");
		}catch(Exception e){ 
			
			Log.i("locdb","Error in creating helper object"); 
			
		}
		
	}
	
	public void open(int mode)
	{
		try{
		if(mode==readMode)
		{
			Log.i("dbtest","Opening database in read mode...");
			db=helper.getReadableDatabase();
			Log.i("locdb","database  opened in read mode...");
		}
		else
		{
			Log.i("dbtest","Opening database in write mode...");
			db=helper.getWritableDatabase();
			Log.i("locdb","database  opened in write mode...");
			
		}
		}catch(Exception e)
		{
			Log.i("locdb","Error in opening database.");
			
		}
	}
	public void close(){
		db.close();
	}
	public void save(Loc coordinate){
		ContentValues row=new ContentValues();
		row.put("lat", coordinate.getLat());
		row.put("lng", coordinate.getLng());
		db.insert(tableName, null, row);
		Log.i("locdb","Record inserted.");
	}
	
	public Cursor getAllLocs()
	{
		Log.i("locdb","Fetching records from the database...");
		return db.query(tableName, new String[]{"_id","lat","lng"}, null, null, null, null, null);
	}
	
	public static class MyDbHelper extends SQLiteOpenHelper {

		public MyDbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			Log.i("locdb","DbHelper  created.");
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(createQuery);
			Log.i("locdb","table created.");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

}
}
