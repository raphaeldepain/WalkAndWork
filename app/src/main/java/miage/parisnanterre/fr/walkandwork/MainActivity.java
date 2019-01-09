package miage.parisnanterre.fr.walkandwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Création d'une instance de ma classe employeursBDD
        EmployeBDD employeurBdd = new EmployeBDD(this);

        //Création d'un employeur
        Employeur employeur = new Employeur("DEPAIN","Raphael","AVIVA","Cherche jeune diplômé Miage");

        //On ouvre la base de données pour écrire dedans
        employeurBdd.open();
        //On insère le employeur que l'on vient de créer
        employeurBdd.insertEmployeur(employeur);

        //Pour vérifier que l'on a bien créé notre employeur dans la BDD
        //on extrait le employeur de la BDD grâce au titre du employeur que l'on a créé précédemment
        Employeur employeurFromBdd = employeurBdd.getEmployeurWithTitre(employeur.getName());
        //Si un employeur est retourné (donc si le employeur à bien été ajouté à la BDD)
        if(employeurFromBdd != null){
            //On affiche les infos du employeur dans un Toast
            Toast.makeText(this, employeurFromBdd.toString(), Toast.LENGTH_LONG).show();
            //On modifie le titre du employeur
            employeurFromBdd.setName("J'ai modifié le nom de l'employeur");
            //Puis on met à jour la BDD
            employeurBdd.updateEmployeur(employeurFromBdd.getId(), employeurFromBdd);
        }

        //On extrait le employeur de la BDD grâce au nouveau nom
        employeurFromBdd = employeurBdd.getEmployeurWithTitre("J'ai modifié le nom de l'employeur");
        //S'il existe un employeur possédant ce titre dans la BDD
        if(employeurFromBdd != null){
            //On affiche les nouvelles informations du employeur pour vérifier que le titre du employeur a bien été mis à jour
            Toast.makeText(this, employeurFromBdd.toString(), Toast.LENGTH_LONG).show();
            //on supprime le employeur de la BDD grâce à son ID
            employeurBdd.removeEmployeurWithID(employeurFromBdd.getId());
        }

        //On essaye d'extraire de nouveau le employeur de la BDD toujours grâce à son nouveau titre
        employeurFromBdd = employeurBdd.getEmployeurWithTitre("J'ai modifié le nom de l'employeur");
        //Si aucun employeur n'est retourné
        if(employeurFromBdd == null){
            //On affiche un message indiquant que le employeur n'existe pas dans la BDD
            Toast.makeText(this, "Cet employeur n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
        }
        //Si le employeur existe (mais normalement il ne devrait pas)
        else{
            //on affiche un message indiquant que le employeur existe dans la BDD
            Toast.makeText(this, "Cet employeur existe dans la BDD", Toast.LENGTH_LONG).show();
        }

        employeurBdd.close();
        
        
    }
}
