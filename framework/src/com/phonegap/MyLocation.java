package com.phonegap;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
/* Usage
 * 
 * MyLocation myLocation = new MyLocation();
 * private void locationClick() {
 *     myLocation.getLocation(this, locationResult));
 * }
 *
 * public LocationResult locationResult = new LocationResult(){
 *   @Override
 *   public void gotLocation(final Location location){
 *       //Got the location!
 *       });
 *   }
 * };
 * 
 */
public class MyLocation {
	
    Timer timer1;
    LocationManager locMan;
    LocationResult locationResult;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private boolean stopAfterResult = false;
    
    long timeInterval = 60000; //MilliSeconds (60000 = 60secs)
    float distanceInterval = 100; //Meters

    public boolean getLocation(Context context, LocationResult result)
    {
        //I use LocationResult callback class to pass location value from MyLocation to user code.
        locationResult=result;
        if(locMan==null)
            locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //exceptions will be thrown if provider is not permitted.
        try{gps_enabled=locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
        try{network_enabled=locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

        //don't start listeners if no provider is enabled
        if(!gps_enabled && !network_enabled)
            return false;

        //Parameters here should be (GPS/Network, MinTime i.e. 60000ms, Meters
        //to stop power chomping (set to 0 on both if your using  StopAfterResult = true;
        //http://developer.android.com/reference/android/location/LocationManager.html#requestLocationUpdates(java.lang.String, long, float, android.app.PendingIntent)
        if(gps_enabled)
            locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeInterval, distanceInterval, locationListenerGps);
        if(network_enabled)
            locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeInterval, distanceInterval, locationListenerNetwork);
        
        timer1=new Timer();
        timer1.schedule(new GetLastLocation(), 20000);
        
        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            locationResult.gotLocation(location);
            if(isStopAfterResult())
            {
            	locMan.removeUpdates(this);
            	locMan.removeUpdates(locationListenerNetwork);
            }
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            locationResult.gotLocation(location);
            if(isStopAfterResult())
            {
            	locMan.removeUpdates(this);
            	locMan.removeUpdates(locationListenerGps);
            }
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
        	
             //TODO: Add handler so these can be activated & Deactivated
        	if(isStopAfterResult())
        	{
        	 locMan.removeUpdates(locationListenerGps);
             locMan.removeUpdates(locationListenerNetwork);
        	}

             Location net_loc=null, gps_loc=null;
             if(gps_enabled)
                 gps_loc=locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
             if(network_enabled)
                 net_loc=locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);

             //if there are both values use the latest one
             if(gps_loc!=null && net_loc!=null){
                 if(gps_loc.getTime()>net_loc.getTime())
                     locationResult.gotLocation(gps_loc);
                 else
                     locationResult.gotLocation(net_loc);
                 return;
             }

             if(gps_loc!=null){
                 locationResult.gotLocation(gps_loc);
                 return;
             }
             if(net_loc!=null){
                 locationResult.gotLocation(net_loc);
                 return;
             }
             locationResult.gotLocation(null);
        }
    }

    public static abstract class LocationResult{
        public abstract void gotLocation(Location location);
    }

    //Accessors
	public boolean isStopAfterResult() {
		return stopAfterResult;
	}

	public void setStopAfterResult(boolean stopAfterResult) {
		this.stopAfterResult = stopAfterResult;
	}

	public long getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public float getDistanceInterval() {
		return distanceInterval;
	}

	public void setDistanceInterval(float distanceInterval) {
		this.distanceInterval = distanceInterval;
	}
}