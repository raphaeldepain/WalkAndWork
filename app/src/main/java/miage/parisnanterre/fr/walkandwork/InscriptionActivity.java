package miage.parisnanterre.fr.walkandwork;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class InscriptionActivity extends AppCompatActivity {

    private EditText nom, email, phone;
    private Button bouton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // recuperation des elements venant de la vue

        nom = (EditText)findViewById(R.id.nom); email = (EditText)findViewById(R.id.email);

        phone = (EditText)findViewById(R.id.phone); bouton = (Button)findViewById(R.id.bouton);

        // creation du listener sur l'objet bouton

        bouton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                // l'operation de communication doit se faire en arriere plan d'ou l'utilisation de l'asynctask

                new AddUser().execute("create",nom.getText().toString(),email.getText().toString(),phone.getText().toString());

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
                return httpQuery.Create(args[0],args[1],args[2],args[3]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            if(result != null) Log.e("ERROR",result);

            Toast.makeText(getApplicationContext(),"operation reussie "+result,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {

            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog

        }
    }

}
