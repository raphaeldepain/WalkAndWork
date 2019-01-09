package miage.parisnanterre.fr.walkandwork;

import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class SQLiteEmploye extends SQLiteOpenHelper {

    private static final String TABLE_EMPLOYES = "table_employeur";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_FNAME = "FNAME";
    private static final String COL_COMPAGNY = "COMPAGNY";
    private static final String COL_DESCRIBTION = "DESCRIBTION";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_EMPLOYES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT NOT NULL, "
            + COL_FNAME + " TEXT NOT NULL, " + COL_COMPAGNY + " TEXT NOT NULL, "+ COL_DESCRIBTION + " TEXT NOT NULL);";

    public SQLiteEmploye(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
       
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_EMPLOYES + ";");
        onCreate(db);
    }

}