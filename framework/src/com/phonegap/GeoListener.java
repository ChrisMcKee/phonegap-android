package com.phonegap;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.webkit.WebView;

/**
 * Killed off
 *
 * @deprecated  Doesn't Work in Android 1.5/6 as of PhoneGap 0.9.1
 *    Use methods from MyLocation class instead
 *    
 *    {@link #GeoListener} and {@link #MyLocation}
 */
@SuppressWarnings("unused")
@Deprecated public class GeoListener {
	String id;
	String successCallback;
	String failCallback;
    GpsListener mGps; 
    NetworkListener mNetwork;
    LocationManager mLocMan;
    Context mCtx;
    private WebView mAppView;
	
	int interval;
	
	GeoListener(String i, Context ctx, int time, WebView appView)
	{
		id = i;
		interval = 4000;//time;
		mCtx = ctx;
		mGps = null;
		mNetwork = null;
		mLocMan = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);
	
		/* moo */
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		String provider = mLocMan.getBestProvider(criteria, true);
				
		/* oom */
		
		if (mLocMan.getProvider(LocationManager.GPS_PROVIDER) != null)
			mGps = new GpsListener(mCtx, interval, this);
		if (mLocMan.getProvider(LocationManager.NETWORK_PROVIDER) != null)
			mNetwork = new NetworkListener(mCtx, interval, this);
        mAppView = appView;
	}
	
	void success(Location loc)
	{
		/*
		 * We only need to figure out what we do when we succeed!
		 */
		
		String params; 
		/*
		 * Build the giant string to send back to Javascript!
		 */
		params = loc.getLatitude() + "," + loc.getLongitude() + ", " + loc.getAltitude() + "," + loc.getAccuracy() + "," + loc.getBearing();
		params += "," + loc.getSpeed() + "," + loc.getTime();
		if(id != "global")
		{
			mAppView.loadUrl("javascript:navigator._geo.success(" + id + "," +  params + ")");
		}		
	}
	
	void fail()
	{
		// Do we need to know why?  How would we handle this?
		if (id != "global") {
			mAppView.loadUrl("javascript:navigator._geo.fail(" + id + ")");
		}
		else
		{
			mAppView.loadUrl("javascript:navigator._geo.fail()");
		}
	}
	
	// This stops the listener
	void stop()
	{
		if(mGps != null)
			mGps.stop();
		if(mNetwork != null)
			mNetwork.stop();
	}

	public Location getCurrentLocation() {
		Location loc = null;
		if (mGps != null)
			loc = mGps.getLocation();
		if (loc == null && mNetwork != null)
			loc = mNetwork.getLocation();
		if(loc == null)
			loc = new Location(LocationManager.NETWORK_PROVIDER);
		return loc;
	}
}
