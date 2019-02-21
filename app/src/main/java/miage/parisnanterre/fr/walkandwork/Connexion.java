package miage.parisnanterre.fr.walkandwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Connexion extends AppCompatActivity {

    private static final String user = "root";
    private static final String pass = "";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/jee";
    private static String name = "DEPAIN";
    private static Connection connexionDB;


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
                  //  Toast.makeText(getApplicationContext(),"AVANT CONNEXION",Toast.LENGTH_SHORT).show();
                    connexionDB = DriverManager.getConnection(url,user,pass);
                    Toast.makeText(getApplicationContext(),connexionDB.getCatalog(),Toast.LENGTH_SHORT).show();
                    // REQUETE SQL
                    String sqlid = "INSERT INTO contact (last_name) VALUES ("+name+");";
                    Statement st = connexionDB.createStatement();
                   // st.executeUpdate(sqlid);
                    Toast.makeText(getApplicationContext(),st.executeUpdate(sqlid),Toast.LENGTH_SHORT).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
             //   Intent intent1 = new Intent(Connexion.this, MainActivity.class);
            //  startActivity(intent1);
            }
        });
    }
}
