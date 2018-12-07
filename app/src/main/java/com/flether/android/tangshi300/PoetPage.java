package com.flether.android.tangshi300;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class PoetPage extends Activity {
	private DatabaseHelper mDbHelper;
	private Cursor mCursor;
	
    private TextView mShiRen;
    private TextView mJieShao;
    private ImageView mHuaXiang;
    
    private ScrollView mScrollView;
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poet_page);	
		
		mScrollView = (ScrollView)findViewById(R.id.SCROLL_VIEW1);
		mScrollView.setVerticalScrollBarEnabled(false);
				
		mDbHelper = new DatabaseHelper(this);		
		
		try { 
			mDbHelper.openDataBase(); 
		}catch(SQLException sqle){ 
			throw sqle; 
		}
		

	
		mShiRen = (TextView)findViewById(R.id.POET_SHIREN);
		mJieShao = (TextView)findViewById(R.id.POET_JIESHAO); 
		mHuaXiang = (ImageView)findViewById(R.id.POET_HUAXIANG); 

	
		Bundle bundle = new Bundle();
	    bundle = this.getIntent().getExtras();
	    String shiRen = bundle.getString(DatabaseHelper.KEY_SHIREN);  
	
	    mCursor = mDbHelper.findPoet(shiRen);
	    String jieShao = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_JIESHAO));
	    String huaXiang = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_HUAXIANG));

		
	    mShiRen.setText(shiRen);	
	    mJieShao.setText(jieShao);
		
	    int resId = getResources().getIdentifier(huaXiang.toLowerCase(), "drawable", "com.flether.android.tangshi300");
	    mHuaXiang.setImageResource(resId);	
	
	    

	}

	
	
}

