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
    private List<Employeur> employeur = new ArrayList<>(); // La liste d'employeur créés
    private List<User> user = new ArrayList<>();
    EmployeBDD employeurBdd = new EmployeBDD(this); // La création de la BDD interne au smartphone (SQLite)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        //id représente l'identifiant de l'utilisateur actuel
        String id = intent.getStringExtra("id");
       // Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();

        //On veut récupérer les informations des gens personnes qui n'ont pas notre identifiant
        //On appelle la base de données avec une requête de connection, et un email
        AsyncTask threadConnectio = new ReadUsers().execute("read",id);
        try {
            //On récupère l'identifiant de ce qui a été retourné par la base de données sous forme de String
            String response = (String) threadConnectio.get();

            //On récupère tous les utilisateurs sur un Tableau de Json
            JSONArray jsonarray = new JSONArray(response);



            for(int i = 0; i< jsonarray.length()-1;i++){
                JSONObject json = new JSONObject(jsonarray.getString(i));
                user.add(new User(json.getString("nom"),json.getString("email"),Integer.parseInt(json.getString("id"))));

            }
            Toast.makeText(getApplicationContext(),user.get(2).toString(),Toast.LENGTH_LONG).show();

        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        employeur.add( new Employeur("DEPAIN","Raphael","SOCIETE GENERALE","Cherche jeune diplômé Miage"));
        employeur.add( new Employeur("AMRANI","Said","AXA","Cherche jeune diplômé Info"));
        employeur.add( new Employeur("ASLIMI","Soukaina","AIRFRANCE","Cherche jeune diplômé Miage"));

        //On ouvre la base de données pour écrire dedans
        employeurBdd.open();
        //On insère dans la BDD les employeurs que l'on vient de créer
        employeurBdd.insertEmployeur(employeur.get(0));
        employeurBdd.insertEmployeur(employeur.get(1));
        employeurBdd.insertEmployeur(employeur.get(2));
        //On ferme la connexion à la BDD
        employeurBdd.close();
        initImageBitmap();
        
    }

    /**
     * Méthode pour préparer les ressources nécessaires à la création de la liste d'employeurs
     *
     */
    public void initImageBitmap(){
        Log.d(TAG, "initImageBitmap: preparing bitmaps.");
        employeurBdd.open();
        imageURL.add("https://pbs.twimg.com/profile_images/707182241403822080/IKaXM_ps_400x400.jpg");
        imageNames.add(employeurBdd.getEmployeurWithTitre("DEPAIN").getCompagny());

        imageURL.add("https://is4-ssl.mzstatic.com/image/thumb/Purple128/v4/cc/99/0b/cc990b90-65ee-8924-b3ef-3552af684d76/AppIcon-0-1x_U007emarketing-0-0-GLES2_U002c0-512MB-sRGB-0-0-0-85-220-0-0-0-7.png/246x0w.jpg");
        imageNames.add(employeurBdd.getEmployeurWithTitre("AMRANI").getCompagny());

        imageURL.add("https://pbs.twimg.com/profile_images/1013705098663428096/HG670mrU.jpg");
        imageNames.add(employeurBdd.getEmployeurWithTitre("ASLIMI").getCompagny());

        employeurBdd.close();
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
