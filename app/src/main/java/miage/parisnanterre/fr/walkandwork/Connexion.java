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

import maes.tech.intentanim.CustomIntent;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Connexion extends AppCompatActivity {

    private EditText email;
    private JSONObject json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_connexion);


        final Button inscription = (Button) findViewById(R.id.binscription);
        final Button connexion = (Button) findViewById(R.id.boffre);


         email = (EditText) findViewById(R.id.email);




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
                    String type = json.getString("isEmployeur");
                    String data = json.getString("data");

                  //  Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                    Intent intentToMain = new Intent(Connexion.this, MainActivity.class);
                    intentToMain.putExtra("id",id);
                    intentToMain.putExtra("isEmployeur",type);
                    intentToMain.putExtra("data",data);
                    startActivity(intentToMain);
                    CustomIntent.customType(Connexion.this, "left-to-right");

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
                CustomIntent.customType(Connexion.this, "right-to-left");

            }
        });

    }


}
