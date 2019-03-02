package miage.parisnanterre.fr.walkandwork;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Connexion extends AppCompatActivity {

    //private FusedLocationProviderClient client;
    private FusedLocationProviderClient GPSLocationClient;
    private String MY_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private boolean isGPSUpdating=false;
    private LocationCallback GPSLocationCallback;
    private EditText email;
    private JSONObject json;


   // final static int REQUEST_CODE_GPS_LAST_LOCATION=4;
    //final static int REQUEST_CODE_GPS_GET_LOCATION=5;
    final static int REQUEST_CODE_GPS_UPDATE_LOCATION=6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_connexion);


        final Button inscription = (Button) findViewById(R.id.binscription);
        final Button connexion = (Button) findViewById(R.id.boffre);
        final Button mapsConnexion = (Button) findViewById(R.id.maps);
         TextView tv =(TextView) findViewById(R.id.textSexe);
         Button buttonUpdateGps = findViewById(R.id.geolocation);
         email = (EditText) findViewById(R.id.email);

         mapsConnexion.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent1 = new Intent(Connexion.this, MainActivity.class);
                 startActivity(intent1);
             }
         });


        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //email de l'utilisateur (on ajoute les quotes pour la requête SQL)
                String emailUser = "\""+email.getText().toString()+"\"";
                //On appelle la base de données avec une requête de connection, et un email
               AsyncTask threadConnection = new ConnexionApp().execute("connection",emailUser);
                try {
                    //On récupère l'identifiant de ce qui a été retourné par la base de données sous forme de String
                    String response = (String) threadConnection.get();
                    //On convertit le String en JSON
                    json = new JSONObject(response);
                    //On récupère la donnée d'identifiant id
                    String id = json.getString("id");

                  //  Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                    Intent intentToMain = new Intent(Connexion.this, MainActivity.class);
                    intentToMain.putExtra("id",id);
                    startActivity(intentToMain);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //   Intent intent1 = new Intent(Connexion.this, MainActivity.class);
             //   startActivity(intent1);
            }
        });


        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInscription = new Intent(Connexion.this, InscriptionActivity.class);
                startActivity(toInscription);
            }
        });



        GPSLocationClient = LocationServices.getFusedLocationProviderClient(this);
        buttonUpdateGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSUpdateLocation();
            }
        });
    }

//=================================== FIN OnCreate============================


    public void GPSUpdateLocation(){
        if(isGPSUpdating) {
            GPSUpdateLocationStop();
        } else {

            if (ActivityCompat.checkSelfPermission(Connexion.this, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
/*                if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,MY_PERMISSION)) {
                    Toast.makeText(MainActivity.this, "Permission nécessaire avait déjà été refusée.", Toast.LENGTH_LONG).show();
                } else {
*/
                Toast.makeText(Connexion.this, "Demande de permission lancée.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(
                        Connexion.this,
                        new String[]{MY_PERMISSION},
                        REQUEST_CODE_GPS_UPDATE_LOCATION);
//                }
            } else {
                GPSLocationCallback= new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        publishLocation(locationResult.getLastLocation(), "Nb locs : "+locationResult.getLocations().size());
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
                Toast.makeText(Connexion.this, msg, Toast.LENGTH_LONG).show();
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
            Toast.makeText(Connexion.this, "Permission refusée.", Toast.LENGTH_LONG).show();
        }
    }

    //=======================================================================

}
