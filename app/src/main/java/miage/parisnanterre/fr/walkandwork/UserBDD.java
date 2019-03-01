package miage.parisnanterre.fr.walkandwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserBDD {
    //TEST
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "WaW2.db";
    private static final String TABLE_USER = "table_user";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NAME = "NAME";
    private static final int NUM_COL_NAME = 1;
    private static final String COL_EMAIL = "EMAIL";
    private static final int NUM_COL_EMAIL = 2;
    private static final String COL_LATITUDE = "LATITUDE";
    private static final int NUM_COL_LATITUDE = 3;
    private static final String COL_LONGITUDE = "LONGITUDE";
    private static final int NUM_COL_LONGITUDE = 4;

    private SQLiteDatabase bdd;

    private SQLiteUser maBaseSQLite;

    public UserBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new SQLiteUser(context, NOM_BDD, null, VERSION_BDD);
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

    public long insertUser(User user){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_LATITUDE, user.getLatitude());
        values.put(COL_LONGITUDE, user.getLongitude());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_USER, null, values);
    }

    public int updateUser(int id, User user){
        //La mise à jour d'un user dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel user on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_LATITUDE, user.getLatitude());
        values.put(COL_LONGITUDE, user.getLongitude());
        return bdd.update(TABLE_USER, values, COL_ID + " = " +id, null);
    }

    public int removeUserWithID(int id){
        //Suppression d'un user de la BDD grâce à l'ID
        return bdd.delete(TABLE_USER, COL_ID + " = " +id, null);
    }


    public User getUserWithId(String id){
        //Récupère dans un Cursor les valeurs correspondant à un user contenu dans la BDD (ici on sélectionne le user grâce à son nom)
        Cursor c = bdd.query(TABLE_USER, new String[] {COL_ID, COL_NAME, COL_EMAIL, COL_LATITUDE, COL_LONGITUDE}, COL_ID + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToUser(c);
    }

    //Cette méthode permet de convertir un cursor en un user
    private User cursorToUser(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un user
        User user = new User();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        user.setId(c.getInt(NUM_COL_ID));
        user.setName(c.getString(NUM_COL_NAME));
        user.setEmail(c.getString(NUM_COL_EMAIL));
        user.setLatitude(Double.parseDouble(c.getString(NUM_COL_LATITUDE)));
        user.setLongitude(Double.parseDouble(c.getString(NUM_COL_LONGITUDE)));
        //On ferme le cursor
        c.close();

        //On retourne le user
        return user;
    }
}