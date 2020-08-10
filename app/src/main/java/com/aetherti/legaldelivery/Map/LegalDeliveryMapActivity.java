package com.aetherti.legaldelivery.Map;
/*package gr.antalakas.android.LegalDelivery;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LegalDeliveryMapActivity extends MapActivity 
{
	private MapController mapController;
	//private MapView mapView;
	//GeoPoint p;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ld_map);
		
		MapView mapView = (MapView) findViewById(R.id.map);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		
		Location location = extras.getParcelable("PreviewLocation");
		String address = extras.getString("Address");
		String city = extras.getString("City");
		String zip = extras.getString("Zip");
		Boolean showGeocoded = extras.getBoolean("ShowGeocoded");
		
		mapController = mapView.getController();
		mapView.setBuiltInZoomControls(true);
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
		LDItemizedOverlay itemizedoverlay = new LDItemizedOverlay(drawable,this);
		
		try {
			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			
			GeoPoint p = new GeoPoint(lat, lng);
			
			OverlayItem overlayitem = new OverlayItem(p, "Best known location : " + address + ", " + city, zip);
			itemizedoverlay.addOverlay(overlayitem);
			
			mapController.animateTo(p);
			mapController.setZoom(16);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		if (!showGeocoded) {
			mapOverlays.add(itemizedoverlay);
			return;
		}
		
		try {
			Geocoder geoCoder = new Geocoder(
		               getBaseContext(), Locale.getDefault());
			
			List<Address> addresses = geoCoder.getFromLocationName(
					 address + ", " + city + ", " + zip, 3);
			
			if (addresses.size() > 0 )
			{
				double geocodedLat = addresses.get(0).getLatitude();
				double geocodedLon = addresses.get(0).getLongitude();
				
				int geo_lat = (int) (geocodedLat * 1E6);
				int geo_lng = (int) (geocodedLon * 1E6);
				
				GeoPoint geocodedPoint = new GeoPoint(geo_lat, geo_lng);
				OverlayItem geocodedOverlayitem = new OverlayItem(geocodedPoint, "Geocoded address : " + address + ", " + city, zip);
				
				itemizedoverlay.addOverlay(geocodedOverlayitem);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		mapOverlays.add(itemizedoverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return(false);
	}
}*/