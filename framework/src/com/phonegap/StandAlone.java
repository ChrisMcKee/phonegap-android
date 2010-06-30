package com.phonegap;

import android.os.Bundle;

public class StandAlone extends DroidGap {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        super.loadUrl("file:///android_asset/www/index.html");                        
    }		
	
}
