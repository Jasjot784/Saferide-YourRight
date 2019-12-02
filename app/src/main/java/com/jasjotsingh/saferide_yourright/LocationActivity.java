package com.jasjotsingh.saferide_yourright;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.jasjotsingh.saferide_yourright.CombuyUtils.obtenerCercanos;
import static com.jasjotsingh.saferide_yourright.CombuyUtils.test;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback{

    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private GoogleMap mMap;
    private Location CurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private static final int permiso = 0;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationrequest;
    private boolean PermisoConcedido ;

    private List<CombuyLocal> locales;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ObtenerPermisodeUbicacion();

        updateLocationUI();

        ObtenerUbicacion(); // Obtiene Ubicacion del dispositivo y coloca la posicion en el mapa

        //obtenerLocales();

    }
    private void agregarMarcador(double Lat,double Lng,String nombre,String descripcion){
        Log.v("RETRO","AGREGANDO MARCADORES");
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Lat, Lng))
                .title(nombre)
                .snippet(descripcion));

    }

    private void obtenerLocales() {
        Log.v("MAPS","OBTENIENDO LOCALES");
        if(locales!=null){
            for(CombuyLocal i : locales){
                agregarMarcador(i.getLatitud(),i.getLongitud(),i.getNombrenegocio(),i.getDescripcion());
            }
        }else{
            Toast.makeText(this, "No se encontro ningun local u.u", Toast.LENGTH_LONG).show();
        }



    }

    private void ObtenerPermisodeUbicacion() {
        /*
         * Consulta el permison de FINE_LOCATION, el resultado es manejado por el callback
         * onRequestPermissionsResult
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            PermisoConcedido = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        PermisoConcedido = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermisoConcedido = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (PermisoConcedido) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                CurrentLocation = null;
                ObtenerPermisodeUbicacion();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void ObtenerUbicacion() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (PermisoConcedido) {
                Task<Location> locationResult = mFusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            CurrentLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(CurrentLocation.getLatitude(),
                                            CurrentLocation.getLongitude()), DEFAULT_ZOOM));
                            Log.v("TASK","ESTA ES LA UBICACION");
                            Log.v("TASK",test(CurrentLocation));

                            locales = obtenerCercanos(CurrentLocation,5);
                            obtenerLocales();
                            DatabaseReference myref = database.getReference("Location Latitude");
                            myref.setValue(CurrentLocation.getLatitude());
                            DatabaseReference myref1 = database.getReference("Location Longitude");
                            myref1.setValue(CurrentLocation.getLongitude());

                            //locales=CombuyUtils.obtenerCercanos(CurrentLocation,2);
                        } else {
                            Log.d("MAPS", "Current location is null. Using defaults.");
                            Log.e("MAPS", "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}
