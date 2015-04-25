package fr.entrecode.older;

/**
 * Created by ben on 24/04/15.
 */
public class Question {

    public enum Reponse {
        REPONSE_INCONNUE,
        REPONSE_A,
        REPONSE_B
    }

    private Personne personneA;
    private Personne personneB;
    private Reponse bonneReponse = Reponse.REPONSE_INCONNUE;

    public Question(Personne personneA, Personne personneB) {
        setPersonneA(personneA);
        setPersonneB(personneB);
        if (getPersonneA().estPlusVieuxQue(getPersonneB())) {
            bonneReponse = Reponse.REPONSE_A;
        }
        else {
            bonneReponse= Reponse.REPONSE_B;
        }
    }

    public Personne getPersonneA() {
        return personneA;
    }

    public void setPersonneA(Personne personneA) {
        this.personneA = personneA;
    }

    public Personne getPersonneB() {
        return personneB;
    }

    public void setPersonneB(Personne personneB) {
        this.personneB = personneB;
    }

    public Personne getPlusVieux() {
        return personneA;
    }

    public boolean estLaBonneReponse(Reponse reponse) {
        if (reponse == bonneReponse) {
            return true;
        }
        else {
            return false;
        }
    }
}
