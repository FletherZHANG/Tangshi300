package com.flether.android.tangshi300;

import java.io.IOException;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.util.Log;


@SuppressWarnings("ALL")
public class PoemPage extends Activity {

	private DatabaseHelper mDbHelper;
	private Cursor mCursor;
	
    private Long mRowId;
    private ScrollView mScrollView;
	private TextView mShiMing;
    private TextView mShiRen;
    private TextView mYuanWen;
    private TextView mPingXi;
    private TextView mZhuShi;
    String  mYiWen;
    String  mImageName;
    private String  keyShiRen;
    
    private ImageButton mBtn1;
    private ImageButton mBtn2;

    private MediaPlayer mPlayer;
    Handler mHandler;
    
    private Context mContext; 
    private Notification mNotification;  
	private NotificationManager mNotificationManager; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poem_detail);	
		
		mContext = PoemPage.this;  
		
		mScrollView = (ScrollView)findViewById(R.id.SCROLL_VIEW);
		mScrollView.setVerticalScrollBarEnabled(false);
		
		
		mDbHelper = new DatabaseHelper(this);		
		
		try { 
			mDbHelper.openDataBase(); 
		}catch(SQLException sqle){ 
			throw sqle; 
		}
				
		mShiMing = (TextView)findViewById(R.id.DET_SHIMING); 
		mShiRen = (TextView)findViewById(R.id.DET_SHIREN);
		mYuanWen = (TextView)findViewById(R.id.DET_YUANWEN); 
		mPingXi = (TextView)findViewById(R.id.DET_PINGXI);
		mZhuShi = (TextView)findViewById(R.id.DET_ZHUSHI); 
		mBtn1 = (ImageButton)findViewById(R.id.BTN_FAVOR2); 
		mBtn2 = (ImageButton)findViewById(R.id.BTN_PLAY); 

		
		Bundle bundle = new Bundle();
	    bundle = this.getIntent().getExtras();
	    mRowId = bundle.getLong(DatabaseHelper.KEY_ROWID);  
	
	    mCursor = mDbHelper.fetchData(mRowId);
	    String shiMing = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SHIMING));
	    String shiRen = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SHIREN));
	    String yuanWen = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_YUANWEN));
	    String pingXi = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_PINGXI));
	    String zhuShi = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_ZHUSHI));
	    mYiWen = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_YIWEN));
	    mImageName = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_IMAGENAME));
	    int favor = mCursor.getInt(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_ISFAVOR));	
	    if (favor == 1) 
	    	mBtn1.setImageResource(R.drawable.ic_favor_del);
	    else
	    	mBtn1.setImageResource(R.drawable.ic_favor_add);
	    mCursor.close();
			
	    mShiMing.setText(shiMing);	
	    mShiRen.setText(shiRen);
	    mYuanWen.setText(yuanWen);
	    mPingXi.setText(pingXi);
	    mZhuShi.setText(zhuShi);
	    keyShiRen = shiRen;	 	
	    

	    mPlayer = new MediaPlayer();
	    mPlayer = MediaPlayer.create(PoemPage.this, R.raw.gaoshan);
	    Log.w("Tangshi300","MediaPlayer.create");

	    setImageButtonListener();
	    
	    	    
	    mHandler = new Handler(){
	    	@Override
	    	public void handleMessage(Message msg) {
	    		super.handleMessage(msg);
	    		if (msg.what == 1){
					mBtn2.setImageResource(R.drawable.ic_stop);
				}
				else{
					mBtn2.setImageResource(R.drawable.ic_play);
				}
	    	
	    	}
	    };	   
	    
		Thread t = new Thread(r);
		t.start();	   

	}


	Runnable r = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true){
					Message msg = new Message(); 
					if (mPlayer.isPlaying())
						msg.what = 1;
					else
						msg.what = 0;
				
					mHandler.sendMessage(msg); 
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}		
	};

    private void setImageButtonListener(){

        mBtn1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mCursor = mDbHelper.fetchData(mRowId);
                int favor = mCursor.getInt(mCursor.getColumnIndexOrThrow(DatabaseHelper.KEY_ISFAVOR));
                mCursor.close();
                if (favor == 1){
                    mDbHelper.updateData (mRowId, false);
                    mBtn1.setImageResource(R.drawable.ic_favor_add);
                }
                else {
                    mDbHelper.updateData (mRowId, true);
                    mBtn1.setImageResource(R.drawable.ic_favor_del);

                }
            }
        });


        ImageButton btn2 = (ImageButton)findViewById(R.id.BTN_PLAY);
        btn2.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO Auto-generated method stub
                if (mPlayer.isPlaying())
                    mPlayer.stop();
                else
                    try {
                        mPlayer.stop();
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        });


        ImageButton btn3 = (ImageButton)findViewById(R.id.BTN_IMAGE);
        btn3.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
                bundle.putString(DatabaseHelper.KEY_YIWEN, mYiWen);
                bundle.putString(DatabaseHelper.KEY_IMAGENAME, mImageName);

                Intent intent = new Intent(PoemPage.this, ImagePage.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        ImageButton btn4 = (ImageButton)findViewById(R.id.BTN_POET);
        btn4.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
                bundle.putString(DatabaseHelper.KEY_SHIREN, keyShiRen);

                Intent intent = new Intent(PoemPage.this, PoetPage.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}

