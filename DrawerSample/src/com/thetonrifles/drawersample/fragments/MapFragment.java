package com.thetonrifles.drawersample.fragments;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thetonrifles.drawersample.Globals;
import com.thetonrifles.drawersample.R;

public class MapFragment extends BaseFragment {

	private static final String LOG_TAG = "Maps";

	private MapView mMapView;
	private GoogleMap mMap;
	private int mCurrentZoomLevel;
	private Location mCurrentLocation;

	public static MapFragment newInstance(Location location) {
		MapFragment fragment = new MapFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(Globals.ParcelableKeys.LOCATION, location);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCurrentZoomLevel = -1;
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map, null);

		mCurrentLocation = (Location) getArguments().getParcelable(Globals.ParcelableKeys.LOCATION);

		mMapView = (MapView) view.findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);
		mMap = mMapView.getMap();

		buildMap();

		return view;
	}

	private void buildMap() {
		try {
			// Checking google services
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}

		// Configuring map
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
		mMap.setMyLocationEnabled(true);

		if (mCurrentLocation != null) {
			// Setting map depending on current location
			if (mCurrentZoomLevel == -1) {
				mCurrentZoomLevel = (int) mMap.getCameraPosition().zoom;
				mCurrentZoomLevel = getIntermediateZoomLevel(mMap);
			}
			LatLng coords = new LatLng(mCurrentLocation.getLatitude(),
					mCurrentLocation.getLongitude());

			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, mCurrentZoomLevel));
		}

		putMarkers();
	}

	private int getIntermediateZoomLevel(GoogleMap map) {
		float max = map.getMaxZoomLevel();
		// 3/4 between default zoom and max zoom supported by device
		return (int) (mCurrentZoomLevel + (3 * (max - mCurrentZoomLevel) / 4));
	}

	private void putMarkers() {
		addMarker(41.9f, 12.5f); // Rome
		addMarker(40.7127f, -74.0059f); // New York
	}

	public Marker addMarker(float lat, float lng) {
		MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng));
		Marker marker = mMap.addMarker(options);
		marker.hideInfoWindow();
		return marker;
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

}
