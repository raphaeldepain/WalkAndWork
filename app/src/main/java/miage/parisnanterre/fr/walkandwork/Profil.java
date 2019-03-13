package miage.parisnanterre.fr.walkandwork;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        final ImageButton call = findViewById(R.id.bCall);
        final TextView name = findViewById(R.id.name);
        final TextView description = findViewById(R.id.description);

        Intent fromRecycler = getIntent();
        final String phoneNumber = fromRecycler.getStringExtra("phoneNumber");
        String nom = fromRecycler.getStringExtra("name");
        String data = fromRecycler.getStringExtra("data");

        name.setText(nom);
        description.setText(data);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone(phoneNumber);
            }
        });



    }

    public void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
