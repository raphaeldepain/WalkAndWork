package miage.parisnanterre.fr.walkandwork;

/**
 * Created by saida on 23/02/2019.
 */

public class Coordonnees {


    public Double latitude;
    public Double longitude;



    public Coordonnees(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Coordonnees(){}

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }




}
