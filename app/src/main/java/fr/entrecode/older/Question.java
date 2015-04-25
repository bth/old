package fr.entrecode.older;

/**
 * Created by ben on 24/04/15.
 */
public class Question {

    private Personne personneA;
    private Personne personneB;

    public Question(Personne personneA, Personne personneB) {
        setPersonneA(personneA);
        setPersonneB(personneB);
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
}
