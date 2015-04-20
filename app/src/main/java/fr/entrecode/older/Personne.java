package fr.entrecode.older;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ben on 20/04/15.
 */
public class Personne {

    private String dateNaissance;
    private String Nom;

    public Personne(String nom, String dateNaissance) {
        setNom(nom);
        setDateNaissance(dateNaissance);
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public int getAge() {
        String[] parties = getDateNaissance().split("/");
        int age = 0;
        if (parties.length == 3) {
            Calendar naissance = new GregorianCalendar();
            Calendar aujourdhui = new GregorianCalendar();
            naissance.set(Integer.parseInt(parties[2]), Integer.parseInt(parties[1])-1, Integer.parseInt(parties[0]));

            int anneeDifference = aujourdhui.get(Calendar.YEAR) - naissance.get(Calendar.YEAR);
            naissance.set(Calendar.YEAR, aujourdhui.get(Calendar.YEAR));
            if (!naissance.after(aujourdhui)) {
                age = anneeDifference;
            }
            else {
                age = anneeDifference - 1;
            }
        }
        return age;
    }
}
