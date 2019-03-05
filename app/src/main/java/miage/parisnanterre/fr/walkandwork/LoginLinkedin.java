package miage.parisnanterre.fr.walkandwork;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;

public class LoginLinkedin extends AppCompatActivity implements View.OnClickListener {

    Intent intentToHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_linkedin);
       intentToHomePage =  new Intent(LoginLinkedin.this, HomePage.class);



        computePakageHash();
        initializeControl();

    }

    private void computePakageHash(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                "miage.parisnanterre.fr.walkandwork",
                        PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash",Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }

        }catch(Exception e){
            Log.e("TAG",e.getMessage());
        }
    }

    public void initializeControl(){

        ImageView buttonLinkedin = (ImageView)findViewById(R.id.button_linkedin);
        buttonLinkedin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_linkedin:
                handleLogin();
                break;
        }
    }

    private void handleLogin(){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Authentication was successful.  You can now do
                // other calls with the SDK.
                fetchPersonalInfo();


            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                Log.e("NIKIL",error.toString());
            }
        }, true);
    }

    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }

    private void fetchPersonalInfo(){


        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name)";

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success !
                JSONObject jsonObject = apiResponse.getResponseDataAsJson();
                try {
                    String firstName = jsonObject.getString("firstName");
                    Log.d("NAMEUSER",firstName);
                    String lastName = jsonObject.getString("lastName");
                    intentToHomePage.putExtra("firstname", "RAPHA");
                    intentToHomePage.putExtra("lastname",lastName);
                    startActivity(intentToHomePage);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onApiError(LIApiError LIApiError) {
                Log.d("NAMEUSER",LIApiError.getMessage());

            }
        });
    }

}
