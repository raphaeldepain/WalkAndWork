package miage.parisnanterre.fr.walkandwork;

import android.support.annotation.NonNull;

public class User {
    public User() {
    }

    private String name;
    private String email;
    private int id;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String name, String email, int id, String phone, String data) {

        this.name = name;
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.data = data;
    }

    private double longitude;
    private double latitude;
    private String data;

    public String getData() {
        return data;
    }

    public User(String name, String email, int id, String data) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.data = data;
    }

    @NonNull

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", data='" + data + '\'' +
                '}';
    }

    public void setData(String data) {
        this.data = data;
    }

    public User(String name, String email, int id, double longitude, double latitude, String data) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.data = data;
    }

    public User(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
