package miage.parisnanterre.fr.walkandwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion extends AppCompatActivity {

    private static final String user = "id8654764_user";
    private static final String pass = "nanterre";
    private static final String url = "";
    private static Connexion connexionDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_connexion);

        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Driver non chargé",Toast.LENGTH_SHORT).show();
        }

        final Button connexion = (Button) findViewById(R.id.boffre);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    connexionDB = (Connexion) DriverManager.getConnection(url,user,pass);

                    // REQUETE SQL

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Intent intent1 = new Intent(Connexion.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
