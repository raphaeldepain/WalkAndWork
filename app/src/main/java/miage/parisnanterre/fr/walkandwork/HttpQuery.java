package miage.parisnanterre.fr.walkandwork;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpQuery {

    private OkHttpClient client = new OkHttpClient();

    private String ENDPOINT = "http://10.0.2.2/phpwaw/WaW-connexion.php";

    public HttpQuery(){

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String Create(String url, String nom, String email, String phone) throws IOException {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("action", url)
                .addFormDataPart("nom", nom)
                .addFormDataPart("email", email)
                .addFormDataPart("phone", phone)
                .build();

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}