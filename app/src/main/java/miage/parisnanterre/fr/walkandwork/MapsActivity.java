package miage.parisnanterre.fr.walkandwork;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    // ici l'ajout
    private FusedLocationProviderClient GPSLocationClient;
    private String MY_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private boolean isGPSUpdating=false;
    private LocationCallback GPSLocationCallback;
    final static int REQUEST_CODE_GPS_UPDATE_LOCATION=6;
    public Coordonnees Mapostion;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Mapostion = new Coordonnees(30,30);

        GPSLocationClient = LocationServices.getFusedLocationProviderClient(this);
        GPSUpdateLocation();



        Toast.makeText(MapsActivity.this,String.valueOf(Mapostion.getLatitude()), Toast.LENGTH_LONG).show();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

       mapFragment.getMapAsync(this);
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

      //  mMap = googleMap;


        mGoogleMap=googleMap;

       // mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
       // mGoogleMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ));
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
              //  buildGoogleApiClient();
               // mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
               // checkLocationPermission();
            }
        }
        else {
          //  buildGoogleApiClient();
           /// mGoogleMap.setMyLocationEnabled(true);
        }


       // LatLng paris = new LatLng(48.866667,2.333333 );
       // mMap.addMarker(new MarkerOptions().position(paris).title("la position de paris"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(paris));
        mGoogleMap.animateCamera( CameraUpdateFactory.zoomTo( 4.0f ) );

    }


    public void GPSUpdateLocation(){
        if(isGPSUpdating) {
            GPSUpdateLocationStop();
        } else {

            if (ActivityCompat.checkSelfPermission(MapsActivity.this, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
/*                if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,MY_PERMISSION)) {
                    Toast.makeText(MainActivity.this, "Permission nécessaire avait déjà été refusée.", Toast.LENGTH_LONG).show();
                } else {
*/
                Toast.makeText(MapsActivity.this, "Demande de permission lancée.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(
                        MapsActivity.this,
                        new String[]{MY_PERMISSION},
                        REQUEST_CODE_GPS_UPDATE_LOCATION);
//                }
            } else {
                GPSLocationCallback= new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        publishLocation(locationResult.getLastLocation(), "Nb locs : "+locationResult.getLocations().size());
                        LatLng latLng = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("Current Position");


                       // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        Marker mCurrLocation = mGoogleMap.addMarker(markerOptions);
                       // GoogleMap googleMap;
                        mGoogleMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );

                    }
                };

                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(10000);
                locationRequest.setFastestInterval(5000);    // en millisecondes
                locationRequest.setSmallestDisplacement(50); // en mètres
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                GPSLocationClient.requestLocationUpdates(
                        locationRequest,
                        GPSLocationCallback,
                        null /* Looper */);

                isGPSUpdating=true;
                // buttonUpdateGps.setText("Stop");
            }
        }
    }
//=========================Fin de GPSUpdateLocation================================


    public void GPSUpdateLocationStop(){
        isGPSUpdating=false;
        // btnGPSUpdateLocation.setText("Suis moi.");
        if(GPSLocationClient!=null ) {
            if(GPSLocationCallback!=null) {
                GPSLocationClient.removeLocationUpdates(GPSLocationCallback);
            }
            GPSLocationCallback=null;
        }
    }

    //===================================================================================
    protected void onPause() {
        super.onPause();
        GPSUpdateLocationStop();

    }

    //==============================================================================


    private Location publishLocation(Location loc, String msg) {
        if(loc != null ){

            Context context = getApplicationContext();
            String espace ="  ";
            String text1 = String.valueOf(loc.getLatitude()) ;
            String text2 = String.valueOf(loc.getLongitude());
            String text = text1+" "+text2;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


            //  tv.setText("Lat : "+loc.getLatitude()+" / Long : "+loc.getLongitude()+" (Provider: "+loc.getProvider()+")");
            if(msg!=null) {
                Toast.makeText(MapsActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        }
        return loc;
    }

    //=============================================================================================

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {

        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            switch (requestCode) {

                case REQUEST_CODE_GPS_UPDATE_LOCATION: GPSUpdateLocation();  return;

            }
        } else {
            Toast.makeText(MapsActivity.this, "Permission refusée.", Toast.LENGTH_LONG).show();
        }
    }


}


