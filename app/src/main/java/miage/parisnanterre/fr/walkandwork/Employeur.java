package miage.parisnanterre.fr.walkandwork;

public class Employeur {

    private int id;


    private String name;
    private String firstname;
    private String compagny;

    public Employeur(String name, String firstname, String compagny, String describtion) {
        this.name = name;
        this.firstname = firstname;
        this.compagny = compagny;
        this.describtion = describtion;
    }

    private String describtion;

    public Employeur() {
    }

    @Override
    public String toString() {
        return "Employeur{" +
                "name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", compagny='" + compagny + '\'' +
                ", describtion='" + describtion + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCompagny() {
        return compagny;
    }

    public void setCompagny(String compagny) {
        this.compagny = compagny;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employeur(String name, String firstname, String compagny, String describtion, int id) {

        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.compagny = compagny;
        this.describtion = describtion;
    }
}
