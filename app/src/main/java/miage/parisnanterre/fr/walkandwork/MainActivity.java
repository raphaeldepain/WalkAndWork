package miage.parisnanterre.fr.walkandwork;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Activité principale
 * Il s'agit de l'activité qui affiche la liste d'employeur en faisant appelle à une BDD.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<String> imageNames = new ArrayList<>(); // La liste des titres des images
    private List<String> imageURL = new ArrayList<>(); // La liste des URL des images
    //private List<Employeur> employeur = new ArrayList<>(); // La liste d'employeur créés
    private List<User> user = new ArrayList<>();
    UserBDD userBdd = new UserBDD(this); // La création de la BDD interne au smartphone (SQLite)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        //id représente l'identifiant de l'utilisateur actuel
        String id = intent.getStringExtra("id");
        String isEmployeur = intent.getStringExtra("isEmployeur");

        //On veut récupérer les informations des gens personnes qui n'ont pas notre identifiant
        //On appelle la base de données avec une requête de connection, et un email
        AsyncTask threadConnectio = new ReadUsers().execute("read",id,isEmployeur);
        try {
            //On récupère l'identifiant de ce qui a été retourné par la base de données sous forme de String
            String response = (String) threadConnectio.get();

            //On récupère tous les utilisateurs sur un Tableau de Json
            JSONArray jsonarray = new JSONArray(response);

            //On récupère les données qu'on place dans une liste
            for(int i = 0; i< jsonarray.length()-1;i++){
                JSONObject json = new JSONObject(jsonarray.getString(i));
                user.add(new User(json.getString("nom"),json.getString("email"),Integer.parseInt(json.getString("id"))));

            }
         //   Toast.makeText(getApplicationContext(),user.get(2).toString(),Toast.LENGTH_LONG).show();

        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }


       // employeur.add( new Employeur("DEPAIN","Raphael","SOCIETE GENERALE","Cherche jeune diplômé Miage"));
       // employeur.add( new Employeur("AMRANI","Said","AXA","Cherche jeune diplômé Info"));
       // employeur.add( new Employeur("ASLIMI","Soukaina","AIRFRANCE","Cherche jeune diplômé Miage"));

        //On ouvre la base de données pour écrire dedans
        userBdd.open();
        //On insère dans la BDD les employeurs que l'on vient de créer
        for(int i = 0;i<user.size();i++){
            userBdd.insertUser(user.get(i));
        }


        //On ferme la connexion à la BDD
        userBdd.close();
        initImageBitmap();
        
    }

    /**
     * Méthode pour préparer les ressources nécessaires à la création de la liste d'employeurs
     *
     */
    public void initImageBitmap(){
        Log.d(TAG, "initImageBitmap: preparing bitmaps.");
        userBdd.open();

        for(int i = 0; i<user.size();i++){
            imageURL.add("https://pbs.twimg.com/profile_images/707182241403822080/IKaXM_ps_400x400.jpg");
         //   imageNames.add(userBdd.getUserWithId(Integer.toString(user.get(i).getId())).getEmail());
            imageNames.add(user.get(i).getEmail());
        }

        userBdd.close();
        initRecyclerView();
    }

    /**
     * Méthode pour afficher la liste d'employeur sous la forme d'un recycler View et à l'aide d'un Adapter.
     */
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEmployeur);
        RecyclerViewEmployeurAdapter adapter = new RecyclerViewEmployeurAdapter(imageNames,imageURL,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
