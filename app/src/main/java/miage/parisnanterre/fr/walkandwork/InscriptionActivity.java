package miage.parisnanterre.fr.walkandwork;

import android.annotation.TargetApi;
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

               String competence = data.getText().toString().toUpperCase();

                // l'operation de communication doit se faire en arriere plan d'ou l'utilisation de l'asynctask

                if(employeur.isChecked()){
                    new AddUser().execute("create", nom.getText().toString(), email.getText().toString(), phone.getText().toString(), competence, "1");
                }
                if(chomeur.isChecked()){
                    new AddUser().execute("create", nom.getText().toString(), email.getText().toString(), phone.getText().toString(), competence, "0");
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
                Toast.makeText(getApplicationContext(), "fail" , Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "succes" + result, Toast.LENGTH_LONG).show();
            }



        }

        @Override
        protected void onPreExecute() {

            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog

        }
    }

    private class GetUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpQuery httpQuery = new HttpQuery();
            String r=null;

            try {
                r=httpQuery.gettheUsers(params[0], params[1], params[2]);

                return r;
            } catch (IOException e) {
                e.printStackTrace();

                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {


            if (s==null) {
                //Toast.makeText(getApplicationContext(), "try again " , Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(getApplicationContext(), "succees "+s , Toast.LENGTH_LONG).show();
                JSONObject obj = null;
                try {
                    obj = new JSONObject(s);
                    JSONArray arr = obj.getJSONArray("result");
                    // Log.d("this is my array", "arr: " + arr);
                    for (int i = 0; i < arr.length(); i++)
                    {
                        String id = arr.getJSONObject(i).getString("id");
                        double distance= arr.getJSONObject(i).getDouble("distance");

                        Log.d("les identifiant",id);
                        Log.d("distance", "value: " + distance);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }




        }
    }

}
