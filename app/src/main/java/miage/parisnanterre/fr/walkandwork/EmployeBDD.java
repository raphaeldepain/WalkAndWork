package miage.parisnanterre.fr.walkandwork;

import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;

public class EmployeBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "waw.db";

    private static final String TABLE_EMPLOYEUR = "table_employeur";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NAME = "NAME";
    private static final int NUM_COL_NAME = 1;
    private static final String COL_FNAME = "FNAME";
    private static final int NUM_COL_FNAME = 2;
    private static final String COL_COMPAGNY = "COMPAGNY";
    private static final int NUM_COL_COMPAGNY = 3;
    private static final String COL_DESCRIBTION = "DESCRIBTION";
    private static final int NUM_COL_DESCRIBTION = 4;

    private SQLiteDatabase bdd;

    private SQLiteEmploye maBaseSQLite;

    public EmployeBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new SQLiteEmploye(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertEmployeur(Employeur employeur){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NAME, employeur.getName());
        values.put(COL_FNAME, employeur.getFirstname());
        values.put(COL_COMPAGNY, employeur.getCompagny());
        values.put(COL_DESCRIBTION, employeur.getDescribtion());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_EMPLOYEUR, null, values);
    }

    public int updateEmployeur(int id, Employeur employeur){
        //La mise à jour d'un employeur dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel employeur on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NAME, employeur.getName());
        values.put(COL_FNAME, employeur.getFirstname());
        values.put(COL_COMPAGNY, employeur.getCompagny());
        values.put(COL_DESCRIBTION, employeur.getDescribtion());
        return bdd.update(TABLE_EMPLOYEUR, values, COL_ID + " = " +id, null);
    }

    public int removeEmployeurWithID(int id){
        //Suppression d'un employeur de la BDD grâce à l'ID
        return bdd.delete(TABLE_EMPLOYEUR, COL_ID + " = " +id, null);
    }

    public Employeur getEmployeurWithTitre(String name){
        //Récupère dans un Cursor les valeurs correspondant à un employeur contenu dans la BDD (ici on sélectionne le employeur grâce à son nom)
        Cursor c = bdd.query(TABLE_EMPLOYEUR, new String[] {COL_ID, COL_NAME, COL_FNAME, COL_COMPAGNY, COL_DESCRIBTION}, COL_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToEmployeur(c);
    }

    //Cette méthode permet de convertir un cursor en un employeur
    private Employeur cursorToEmployeur(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un employeur
        Employeur employeur = new Employeur();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        employeur.setId(c.getInt(NUM_COL_ID));
        employeur.setName(c.getString(NUM_COL_NAME));
        employeur.setFirstname(c.getString(NUM_COL_FNAME));
        employeur.setCompagny(c.getString(NUM_COL_COMPAGNY));
        employeur.setDescribtion(c.getString(NUM_COL_DESCRIBTION));
        //On ferme le cursor
        c.close();

        //On retourne le employeur
        return employeur;
    }
}