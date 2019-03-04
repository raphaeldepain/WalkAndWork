package miage.parisnanterre.fr.walkandwork;


import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

     class AddUser extends AsyncTask<String, Void, String> {
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... args) {

        HttpQuery httpQuery = new HttpQuery();

        try {
            return httpQuery.Create(args[0],args[1],args[2],args[3],args[4],args[5]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

        if(result != null) Log.e("ERROR",result);

     //   Toast.makeText(getApplicationContext(),"operation reussie "+result,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {

        // Things to be done before execution of long running operation. For
        // example showing ProgessDialog

    }
}
