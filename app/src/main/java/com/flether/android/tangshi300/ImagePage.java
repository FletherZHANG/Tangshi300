package com.flether.android.tangshi300;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;



public class ImagePage extends Activity {
	
    private TextView mYiWen;
    private ImageView mPoemImage;
    private ScrollView mScrollView;

    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_page);
		MainPage.activityList.add(this);

		/* 取得屏幕分辨率大小 */
		DisplayMetrics dm=new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    int displayWidth=dm.widthPixels;

	   	
		mYiWen = (TextView)findViewById(R.id.IMA_YIWEN);
		mPoemImage = (ImageView)findViewById(R.id.IMA_POEMIMAGE); 
		mScrollView = (ScrollView)findViewById(R.id.IMA_SCROLLVIEW);
	
		Bundle bundle = new Bundle();
	    bundle = this.getIntent().getExtras();
	    String yiWen = bundle.getString(DatabaseHelper.KEY_YIWEN);  
	    String imageName = bundle.getString(DatabaseHelper.KEY_IMAGENAME);  
	    
	
	    mYiWen.setText(yiWen);	
		
	    int resId = getResources().getIdentifier(imageName.toLowerCase(), "drawable", "com.flether.android.tangshi300");
	    Bitmap bmp=BitmapFactory.decodeResource(getResources(),resId);
	 
	    int bmpWidth=bmp.getWidth();
	    int bmpHeight=bmp.getHeight();	
	    float ratio=(float)bmpHeight/(float)bmpWidth; 
	    
	    mPoemImage.setMinimumWidth(displayWidth);
	    mPoemImage.setMinimumHeight((int) (displayWidth*ratio));
		    
	    mPoemImage.setImageBitmap(bmp);
	    mScrollView.setPadding(10, 10, 10, (int) (displayWidth*ratio + 20));

	}

	
	
}

