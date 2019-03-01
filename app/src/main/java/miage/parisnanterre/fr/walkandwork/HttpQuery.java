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

    private String ENDPOINT = "https://walkandwork.000webhostapp.com/WaW-connexion.php";

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String UpdatePosition(String url, String id, String longitude, String latitude) throws IOException {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("action", url)
                .addFormDataPart("id", id)
                .addFormDataPart("longitude", longitude)
                .addFormDataPart("latitude", latitude)
                .build();

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String Connexion(String url, String email) throws IOException {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("action", url)
                .addFormDataPart("email", email)
                .build();

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String Read(String url, String id) throws IOException {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("action", url)
                .addFormDataPart("id", id)
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