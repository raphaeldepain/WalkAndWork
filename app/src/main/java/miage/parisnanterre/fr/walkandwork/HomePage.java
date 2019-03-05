package miage.parisnanterre.fr.walkandwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);

        Intent intentFromLoginLinkedin = getIntent();
        String lastname = intentFromLoginLinkedin.getStringExtra("lastname");
        String firstname = intentFromLoginLinkedin.getStringExtra("firstname");

        TextView lastnameTV = (TextView)findViewById(R.id.lastname);
        lastnameTV.setText(lastname);
        TextView firstnameTV = (TextView)findViewById(R.id.firstname);
        firstnameTV.setText(firstname);




    }
}
