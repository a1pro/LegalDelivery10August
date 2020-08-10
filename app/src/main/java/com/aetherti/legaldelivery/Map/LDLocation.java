package com.aetherti.legaldelivery.Map;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

@SuppressLint("ShowToast")
public class LDLocation {
    Timer timer1;
    LocationManager locationManager;
    public LocationResult locationResult;
    boolean gps_enabled=false;
    boolean network_enabled=false;
    final Handler handler = new Handler();

    public boolean getLocation(Context context, LocationResult result)
    {
        locationResult=result;
        if(locationManager==null)
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try{
        	gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        	if(gps_enabled == false){

        	}
        }catch(Exception ex){
        	System.out.println("Exception:"+ex);
        }
        try{
        	network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        	}catch(Exception ex){}

        //don't start listeners if no provider is enabled
        if(!gps_enabled && !network_enabled)
            return false;

        if(gps_enabled)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
    
        if(network_enabled)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListenerNetwork);
        timer1=new Timer();
        timer1.schedule(new GetLastLocation(), 12000);
        return true;
    }

	LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            timer1.purge();
            timer1=null;
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
           
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
        	
            timer1.cancel();
            timer1.purge();
            timer1=null;
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);   
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    
   public class GetLastLocation extends TimerTask {
	   Location net_loc=null, gps_loc=null;
    	@Override
			public void run() {
    		handler.post(new Runnable() {
    			public void run() {
             locationManager.removeUpdates(locationListenerGps);
             locationManager.removeUpdates(locationListenerNetwork);
             if(gps_enabled)
                 gps_loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             if(network_enabled)
                 net_loc=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

             //if there are both values use the latest one
             if(gps_loc!=null && net_loc!=null){
                 if(gps_loc.getTime()>net_loc.getTime())
                     locationResult.gotLocation(gps_loc);
                 else
                     locationResult.gotLocation(net_loc);
                 return;
             }
             Log.d("gps_loc", "value of gps_loc ::"+gps_loc);
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
    	});
    }	
    }

    public static abstract class LocationResult{
        public abstract void gotLocation(Location location);
    }
}