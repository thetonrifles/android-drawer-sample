package com.thetonrifles.drawersample;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class LocationActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener {

	private Location mCurrentLocation;
	private LocationClient mLocationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationClient = new LocationClient(this, this, this);

		if (savedInstanceState != null) {
			mCurrentLocation = savedInstanceState.getParcelable(Globals.ParcelableKeys.LOCATION);
		}
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		Log.i(Globals.Tags.LOCATION, "Location services connected");

		// Getting current location
		mCurrentLocation = mLocationClient.getLastLocation();

		// Requesting periodic location updates
		int interval = getResources().getInteger(R.integer.location_interval);
		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setInterval(interval);
		mLocationClient.requestLocationUpdates(request, this);
	}

	protected Location getCurrentLocation() {
		return mCurrentLocation;
	}

	@Override
	public void onDisconnected() {
		Log.i(Globals.Tags.LOCATION, "Location services disconnected");
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.i(Globals.Tags.LOCATION, "Location services failed");
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.i(Globals.Tags.MAPS, "Location changed");
		mCurrentLocation = location;
	}

	@Override
	protected void onStart() {
		super.onStart();
		mLocationClient.connect();
	}

	@Override
	protected void onStop() {
		if (mLocationClient.isConnected()) {
			mLocationClient.removeLocationUpdates(this);
		}
		mLocationClient.disconnect();
		super.onStop();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putParcelable(Globals.ParcelableKeys.LOCATION, mCurrentLocation);
	}

}
