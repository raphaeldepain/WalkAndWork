package miage.parisnanterre.fr.walkandwork;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import maes.tech.intentanim.CustomIntent;

public class InscriptionActivity extends AppCompatActivity {

    private EditText nom, email, phone, data;
    private Button bouton, getUsersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // recuperation des elements venant de la vue

        nom = (EditText) findViewById(R.id.nom);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        data = (EditText) findViewById(R.id.data);
        final RadioButton employeur = (RadioButton) findViewById(R.id.employeur);
        final RadioButton chomeur = (RadioButton) findViewById(R.id.chomeur);
        bouton = (Button) findViewById(R.id.bouton);



        // creation du listener sur l'objet bouton

        bouton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                AsyncTask thread = null;

               String competence = data.getText().toString().toUpperCase();

                // l'operation de communication doit se faire en arriere plan d'ou l'utilisation de l'asynctask

                if(employeur.isChecked()){
                   thread = new AddUser().execute("create", nom.getText().toString(), email.getText().toString(), phone.getText().toString(), competence, "1");
                }
                if(chomeur.isChecked()){
                    thread = new AddUser().execute("create", nom.getText().toString(), email.getText().toString(), phone.getText().toString(), competence, "0");
                }
                String response = null;
                try {
                    response = (String) thread.get();
                    Intent toConnexion = new Intent(InscriptionActivity.this,Connexion.class);
                    if(response.equals("1")){
                        startActivity(toConnexion);
                        CustomIntent.customType(InscriptionActivity.this, "left-to-right");
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Une erreur est survenue" , Toast.LENGTH_LONG).show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    // Asynctask destine a l'operation d'arriere plan

    private class AddUser extends AsyncTask<String, Void, String> {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... args) {

            HttpQuery httpQuery = new HttpQuery();

            try {
                String result = httpQuery.Create(args[0], args[1], args[2], args[3], args[4], args[5]);

                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            if (result != null) {
                Log.e("ERROR", result);
            }
            if(result==null){
               // Toast.makeText(getApplicationContext(), "fail" , Toast.LENGTH_LONG).show();
            }else{
               // Toast.makeText(getApplicationContext(), "succes" + result, Toast.LENGTH_LONG).show();
            }



        }

        @Override
        protected void onPreExecute() {

            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog

        }
    }


}
