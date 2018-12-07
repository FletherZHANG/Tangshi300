package com.flether.android.tangshi300;

import android.os.Bundle;
import android.preference.PreferenceActivity;



public class PrePage extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);		
			
	}

}
