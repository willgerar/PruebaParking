package com.example.urv.pruebaparking;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.database.Cursor;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.urv.pruebaparking.Models.Location;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cat.tomasgis.app.providers.parkingprovider.contracts.ModelContracts;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location mLocation;
    private static final String TAG = com.example.urv.pruebaparking.MapsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

        //verificamos el estado de conecxión
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if(status == ConnectionResult.SUCCESS){

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }else{
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,(Activity)getApplicationContext(),10);
            dialog.show();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);//pone el cuadro de zoom con el + y el -
        uiSettings.setCompassEnabled(true);//muestra el cuadro de acceso a google maps y el cuadro de acceso a la ubicacion
        uiSettings.setMapToolbarEnabled(true);//oculta el cuadro de la barra de mapas ose donde se muestra la linea anterior
        uiSettings.setTiltGesturesEnabled(true);//matiene activo el titulo sobre el globo de ubo de ubicación

        double latitude=0.0;
        double longitude = 0.0;
        String streetAdress;

        //Seleccion los latitud y longitud de la Based e datos
        Cursor cursor = this.getContentResolver().query(ModelContracts.LocationModel.buildContentUri(),
                ModelContracts.LocationModel.DEFAULT_PROJECTIONS, null, null,
                ModelContracts.LocationModel.DEFAULT_SORT);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            latitude = cursor.getDouble(cursor.getColumnIndex(ModelContracts.LocationContract.LATITUDE));
            longitude = cursor.getDouble(cursor.getColumnIndex(ModelContracts.LocationContract.LONGITUDE));
            streetAdress = cursor.getString(cursor.getColumnIndex(ModelContracts.LocationContract.STREET_ADDRESS));

            Log.i(TAG,"Location Parking: " + latitude + longitude);
        }else{
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);
        }
        LatLng locationPark = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(locationPark).title("Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        float zoomLevel = 16;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPark, zoomLevel));//se ha de poner newLatLngZoom

        //mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }


}
