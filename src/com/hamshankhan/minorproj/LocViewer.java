package com.hamshankhan.minorproj;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LocViewer extends Activity implements OnClickListener{
	ListView list;
	Button btn;
	LocAdapter dbAdapter;
	 @Override
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.locviewer);
	        list=(ListView)findViewById(R.id.locList);
	        btn=(Button)findViewById(R.id.btnBackFromViewer);
	        btn.setOnClickListener(this);
	      
	    }
	 @Override
	public void onStart()
	 {
		 super.onStart();
		 dbAdapter=MyApplication.getDbAdapter();
		 dbAdapter.open(LocAdapter.readMode);
		 Log.i("dbtest","Obtaining cursor...");
		 Cursor locCursor=dbAdapter.getAllLocs();
		 
		 /*Log.i("dbtest","Obtaining ContentResolver...");
		 ContentResolver resolver=getContentResolver();
		 Cursor empCursor=resolver.query(Uri.parse("content://com.techmentro.provider/Emp"), new String[]{"_id","name","job","salary"}, null, null, null);*/
		 
		 Log.i("dbtest","cursor obtained, creting list adapter...");
		 SimpleCursorAdapter listAdapter=new SimpleCursorAdapter(this,R.layout.listrow,locCursor,new String[]{"_id",
				 "lat","lng"},new int[]{R.id.viewId,R.id.viewLat,R.id.viewLng});
		 Log.i("dbtest","list adapter created...");
		 list.setAdapter(listAdapter);
		 Log.i("dbtest","list adapter provided to the list view...");
	 }
	 @Override
		protected void onStop() {
			super.onStop();
			dbAdapter.close();
		}
	@Override
	public void onClick(View v) {
		Log.i("dbtest","EmpViewer activity is completed.");
		finish();
		
	}
}
