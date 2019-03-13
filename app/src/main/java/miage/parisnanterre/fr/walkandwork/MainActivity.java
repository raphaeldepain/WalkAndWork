package miage.parisnanterre.fr.walkandwork;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import maes.tech.intentanim.CustomIntent;

/**
 * Activité principale
 * Il s'agit de l'activité qui affiche la liste d'employeur en faisant appelle à une BDD.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<String> imageNames = new ArrayList<>(); // La liste des titres des images
    private List<String> imageURL = new ArrayList<>(); // La liste des URL des images
    private List<String> idUsersMaps = new ArrayList<String>(); // La liste des utilisateurs présents sur maps
    private List<User> user = new ArrayList<>();
    private List<User> userFinal = new ArrayList<>();
    private Map<User,Integer> userToDecrease = new HashMap<>();

    List<User>sortedUser;
    UserBDD userBdd = new UserBDD(this); // La création de la BDD interne au smartphone (SQLite)
    private List<String> phones = new ArrayList<>();
    private List<String> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button goToMap = (Button) findViewById(R.id.bmaps);


        Intent intent = getIntent();
        //id représente l'identifiant de l'utilisateur actuel
        final String id = intent.getStringExtra("id");
        String isEmployeur = intent.getStringExtra("isEmployeur");
        String nosCompetence = intent.getStringExtra("data");

        //On veut récupérer les informations des gens personnes qui n'ont pas notre identifiant
        //On appelle la base de données avec une requête de connection, et un email
        AsyncTask threadConnectio = new ReadUsers().execute("read",id,isEmployeur);
        try {
            //On récupère l'identifiant de ce qui a été retourné par la base de données sous forme de String
            String response = (String) threadConnectio.get();

            //On récupère tous les utilisateurs sur un Tableau de Json
            JSONArray jsonarray = new JSONArray(response);
           // Toast.makeText(getApplicationContext(),jsonarray.toString(),Toast.LENGTH_LONG).show();

            //On récupère les données qu'on place dans une liste
            for(int i = 0; i< jsonarray.length()-1;i++){
                JSONObject json = new JSONObject(jsonarray.getString(i));

                if(!(json.getString("data").isEmpty())){
                    idUsersMaps.add(json.getString("id"));
                    user.add(new User(json.getString("nom"),json.getString("email"),Integer.parseInt(json.getString("id")),json.getString("phone"),json.getString("data")));
                    Log.d("TETETE", user.get(i).getPhone());
                    }
            }
            //On les classes
            userFinal = classerUserPreference(nosCompetence);



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


        //acces à la carte

        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMap = new Intent(MainActivity.this,MapsActivity.class);
                toMap.putExtra("id",id);
                toMap.putExtra("users", (Serializable) idUsersMaps);
                startActivity(toMap);
                CustomIntent.customType(MainActivity.this, "bottom-to-up");
            }
        });


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

    public static HashMap<User, Integer> triAvecValeur( HashMap<User, Integer> map ){
        List<Map.Entry<User, Integer>> list =
                new LinkedList<Map.Entry<User, Integer>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<User, Integer>>(){
            public int compare( Map.Entry<User, Integer> o1, Map.Entry<User, Integer> o2 ){
                return (o1.getValue()).compareTo( o2.getValue());
            }
        });

        HashMap<User, Integer> map_apres = new LinkedHashMap<User, Integer>();
        for(Map.Entry<User, Integer> entry : list)
            map_apres.put( entry.getKey(), entry.getValue() );
        return map_apres;
    }

    /**
     * Méthode pour préparer les ressources nécessaires à la création de la liste d'employeurs
     *
     */
    public void initImageBitmap(){
        Log.d(TAG, "initImageBitmap: preparing bitmaps.");
        userBdd.open();

        for(int i = userFinal.size()-1; i>=0 ;i--){
            imageURL.add("data:image/jpeg;base64,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");
            imageNames.add(userFinal.get(i).getName());
            Log.d(TAG, userFinal.get(i).getPhone());
            phones.add(userFinal.get(i).getPhone());
            data.add(userFinal.get(i).getData());
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
        RecyclerViewEmployeurAdapter adapter = new RecyclerViewEmployeurAdapter(imageNames,imageURL,phones,data,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<User> classerUserPreference(String nosCompetence){
        int[] userActualise = new int[user.size()];
        Log.d("MainActivity", Integer.toString(userActualise.length));
        String[] nosCompetences = nosCompetence.split(",");
        for(int i =0;i<user.size();i++){
            for(int j=0; j < nosCompetences.length; j++){
                if(Arrays.asList(user.get(i).getData().split(",")).contains( nosCompetences[j])){
                    userActualise[i] += 1;
                    Log.d("MainActivityUOA",Integer.toString(userActualise[i]));
                }
            }
        }
        Log.d("TABTAILLE",Integer.toString(userActualise.length));
        Log.d("LISTTAILLE",Integer.toString(user.size()));
        for(int g = 0;g<user.size();g++){
            userToDecrease.put(user.get(g),userActualise[g]);

        }

        userToDecrease = triAvecValeur((HashMap<User, Integer>) userToDecrease);
        Log.d("HASHTAILLE",Integer.toString(userToDecrease.size()));

        for (HashMap.Entry<User, Integer> entry : userToDecrease.entrySet())
        {
            if(entry.getValue()>0) {
                userFinal.add(entry.getKey());
            }
            //  entry.getValue();
        }

        return userFinal;

    }

}
