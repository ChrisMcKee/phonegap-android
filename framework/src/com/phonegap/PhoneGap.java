package com.phonegap;

import java.util.TimeZone;

import android.content.Context;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.webkit.WebView;
import android.media.Ringtone;
import android.media.RingtoneManager;

@SuppressWarnings("unused")
public class PhoneGap{
	
	private static final String LOG_TAG = "PhoneGap";
	/*
	 * UUID, version and availability	
	 */
	public boolean droid = true;
	public static String version = "0.9.99999";
	public static String platform = "Android";
	public static String uuid;
	private Context mCtx;
    private WebView mAppView;
    AudioHandler audio; 
    
	public PhoneGap(Context ctx, WebView appView) {
        this.mCtx = ctx;
        this.mAppView = appView;
        audio = new AudioHandler(mAppView, ctx);
        uuid = getUuid();
    }
	
	public void beep(long pattern)
	{
		Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone notification = RingtoneManager.getRingtone(mCtx, ringtone);
		for (long i = 0; i < pattern; ++i)
		{
			notification.play();
		}
	}
	
	public void vibrate(long pattern){
        // Start the vibration, 0 defaults to half a second.
		if (pattern == 0)
			pattern = 500;
        Vibrator vibrator = (Vibrator) mCtx.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern);
	}
	
	public String getPlatform()
	{
		return PhoneGap.platform;
	}
	
	public String getUuid()
	{		
		//TelephonyManager operator = (TelephonyManager) mCtx.getSystemService(Context.TELEPHONY_SERVICE);		
		//String uuid = operator.getDeviceId();
		String uuid = Settings.Secure.getString(mCtx.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		return uuid;
	}

  public String getLine1Number(){
	  TelephonyManager operator = (TelephonyManager)mCtx.getSystemService(Context.TELEPHONY_SERVICE);
	  return operator.getLine1Number();
  }
	public String getDeviceId(){
	  TelephonyManager operator = (TelephonyManager)mCtx.getSystemService(Context.TELEPHONY_SERVICE);
	  return operator.getDeviceId();
  }
	public String getSimSerialNumber(){
	  TelephonyManager operator = (TelephonyManager)mCtx.getSystemService(Context.TELEPHONY_SERVICE);
	  return operator.getSimSerialNumber();
  }
  public String getSubscriberId(){
	  TelephonyManager operator = (TelephonyManager)mCtx.getSystemService(Context.TELEPHONY_SERVICE);
	  return operator.getSubscriberId();
  }
	
	public String getModel()
	{
		String model = android.os.Build.MODEL;
		return model;
	}
	public String getProductName()
	{
		String productname = android.os.Build.PRODUCT;
		return productname;
	}
	public String getOSVersion()
	{
		String osversion = android.os.Build.VERSION.RELEASE;
		return osversion;
	}
	public String getSDKVersion()
	{
		String sdkversion = android.os.Build.VERSION.SDK;
		return sdkversion;
	}
	
	public String getVersion()
	{
		return version;
	}	
	
	
    public void httpGet(String url, String file)
    /**
     * grabs a file from specified url and saves it to a name and location
     * the base directory /sdcard is abstracted so that paths may be the same from one mobile OS to another
     * TODO: JavaScript call backs and error handling
     */
    {
    	HttpHandler http = new HttpHandler();
    	http.get(url, file);
    }
    
    /**
     * AUDIO
     * TODO: Basic functions done but needs more work on error handling and call backs, remove record hack
     */
    
    public void startRecordingAudio(String file)
    {
    	/* for this to work the recording needs to be specified in the constructor,
    	 * a hack to get around this, I'm moving the recording after it's complete 
    	 */
    	audio.startRecordingAudio(file);
    }
    
    public void stopRecordingAudio()
    {
    	audio.stopRecordingAudio();
    }
    
    public void startPlayingAudio(String file)
    {
    	audio.startPlayingAudio(file);
    }
    
    public void stopPlayingAudio()
    {
    	audio.stopPlayingAudio();
    }
    
    public long getCurrentPositionAudio()
    {
    	System.out.println(audio.getCurrentPositionAudio());
    	return(audio.getCurrentPositionAudio());
    }
    
    public long getDurationAudio(String file)
    {
    	System.out.println(audio.getDurationAudio(file));
    	return(audio.getDurationAudio(file));
    }  
    
    public void setAudioOutputDevice(int output){
    	audio.setAudioOutputDevice(output);
    }
    
    public int getAudioOutputDevice(){
    	return audio.getAudioOutputDevice();
    }       
    
    public String getTimeZoneID() {
       TimeZone tz = TimeZone.getDefault();
        return(tz.getID());
    } 
    
}

