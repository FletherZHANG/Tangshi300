package com.flether.android.tangshi300;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public class MainPage extends ListActivity {

	private DatabaseHelper mDbHelper;
	private Cursor mCursor;
	private Boolean mShowFavor;
	private String mSelPoet;
	private ArrayList <String> mSelShiTi;
	SimpleCursorAdapter mAdapterdata;
	private Boolean mNeedUpdate = true;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poem_list);	
			
		mDbHelper = new DatabaseHelper(this);		
		
		try { 
			mDbHelper.openDataBase(); 
		}catch(SQLException sqle){ 
			throw sqle; 
		}
		
		updateSelected();
		mCursor = mDbHelper.fetchSelData(mSelPoet, mSelShiTi);				
		bindData ();
		
		mShowFavor = false;
		startManagingCursor(mCursor);		
	    setImageButtonListener();
	}
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    	Bundle bundle = new Bundle();	   		
    	bundle.putLong(DatabaseHelper.KEY_ROWID, id);
    	
        Intent intent = new Intent(MainPage.this, PoemPage.class);    
        intent.putExtras(bundle);
        startActivity(intent); 	
    }
    	
    	
	@Override
	protected void onResume() {
		if (mNeedUpdate)
		{
			// TODO Auto-generated method stub
			updateSelected();
			mCursor.close();
		
			if (mShowFavor)
				mCursor = mDbHelper.fetchFavorData();	
			else
				mCursor = mDbHelper.fetchSelData(mSelPoet, mSelShiTi);	
		
			bindData ();
		}
		mNeedUpdate = false;
		super.onResume();
	}

    	
	@Override
	protected void onDestroy() {
			// TODO Auto-generated method stub
			mDbHelper.close();
			super.onDestroy();
		}	
	
	
	private void updateSelected() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		mSelPoet = prefs.getString("selPoetName","全部诗人");
		mSelShiTi = new ArrayList <String>();
		mSelShiTi.clear();
		if(prefs.getBoolean("guShi5", true)) mSelShiTi.add("五言古诗");
		if(prefs.getBoolean("jueJu5", true)) mSelShiTi.add("五言绝句");
		if(prefs.getBoolean("yueFu5", true)) mSelShiTi.add("五言乐府");
		if(prefs.getBoolean("lvShi5", true)) mSelShiTi.add("五言律诗");
		if(prefs.getBoolean("guShi7", true)) mSelShiTi.add("七言古诗");
		if(prefs.getBoolean("jueJu7", true)) mSelShiTi.add("七言绝句");
		if(prefs.getBoolean("yueFu7", true)) mSelShiTi.add("七言乐府");
		if(prefs.getBoolean("lvShi7", true)) mSelShiTi.add("七言律诗");

	}
	
	
	private void bindData () {
        // Create an array to specify the fields we want to display in the list (only TITLE)
	    String[] from = new String[]{DatabaseHelper.KEY_SHIMING,DatabaseHelper.KEY_SHIREN,DatabaseHelper.KEY_SHITI};

	    // and an array of the fields we want to bind those fields to (in this case just text1)
	    int[] to = new int[]{R.id.TAB_SHIMING,R.id.TAB_SHIREN,R.id.TAB_SHITI,};

	    // Now create a simple cursor adapter and set it to display
	    mAdapterdata = new SimpleCursorAdapter(this, R.layout.poem_row, mCursor, from, to);
	    setListAdapter(mAdapterdata);
	}	
	
	
	
	private void setImageButtonListener(){	
		
		final ImageButton btn1 = (ImageButton)findViewById(R.id.BTN_FAVOR1); 		
		btn1.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mShowFavor = (mShowFavor) ? false:true; 				
				if (mShowFavor)
					btn1.setImageResource(R.drawable.ic_favor);			
				else 
					btn1.setImageResource(R.drawable.ic_all);	
				mNeedUpdate = true;
				onResume();
				
			}
		});
		
		ImageButton btn2 = (ImageButton)findViewById(R.id.BTN_SEL); 		
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    Intent intent = new Intent(MainPage.this, PrePage.class);    
		        startActivity(intent);
		        mNeedUpdate = true;			
			}
		});
		
		ImageButton btn3 = (ImageButton)findViewById(R.id.BTN_INFO); 		
		btn3.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainPage.this).setTitle(
						R.string.app_info).setMessage(R.string.app_info_msg)
						.setPositiveButton(R.string.str_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialoginterface,
											int i) {
									}
								}).show();
				
			}
		});
		
		ImageButton btn4 = (ImageButton)findViewById(R.id.BTN_QUIT); 		
		btn4.setOnClickListener(new ImageButton.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				System.exit(0);
				
			}
		});
	}

}
