package com.phonegap;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;


/**
 * Killed off
 *
 * @deprecated  Doesn't Work in Android 1.5/6 as of PhoneGap 0.9.1
 *    Use methods from MyLocation class instead
 *    
 *    {@link #GeoBroker} and {@link #MyLocation}
 */
@Deprecated public class GpsListener implements LocationListener {
	
	private Context mCtx;
	private Location cLoc;
	private LocationManager mLocMan;
	private static final String LOG_TAG = "PhoneGap";
	private GeoListener owner;
	private boolean hasData = false;
	
	public GpsListener(Context ctx, int interval, GeoListener m)
	{
		owner = m;
		mCtx = ctx;
		mLocMan = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);
		mLocMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, interval, 0, this);
		cLoc = mLocMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public Location getLocation()
	{
		cLoc = mLocMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		hasData = true;
		return cLoc;
	}
	
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "The provider " + provider + " is disabled");
		owner.fail();
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "The provider "+ provider + " is enabled");
	}


	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "The status of the provider " + provider + " has changed");
		if(status == 0)
		{
			Log.d(LOG_TAG, provider + " is OUT OF SERVICE");
			owner.fail();
		}
		else if(status == 1)
		{
			Log.d(LOG_TAG, provider + " is TEMPORARILY_UNAVAILABLE");
		}
		else
		{
			Log.d(LOG_TAG, provider + " is Available");
		}
	}


	public void onLocationChanged(Location location) {
		Log.d(LOG_TAG, "The location has been updated!");
		owner.success(location);
	}

	public boolean hasLocation() {
		return hasData;
	}

	public void stop()
	{
		mLocMan.removeUpdates(this);
	}
	
}
