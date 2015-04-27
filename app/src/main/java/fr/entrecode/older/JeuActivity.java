package fr.entrecode.older;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ben on 19/04/15.
 */
public class JeuActivity extends Activity {

    private enum Etat {
        AFFICHER_QUESTION,
        AFFICHER_REPONSE
    }

    private final int TEMPS_AFFICHAGE_QUESTION_MAX = 3;
    private final int TEMPS_AFFICHAGE_REPONSE_MAX = 2;

    private List<Personne> listePersonnes;
    private Question.Reponse reponseDonnee = Question.Reponse.REPONSE_INCONNUE;
    private Etat etat = Etat.AFFICHER_QUESTION;
    private int score = 0;
    private int meilleurScore = 0;
    private int tempsAffichageQuestion = TEMPS_AFFICHAGE_QUESTION_MAX;
    private int tempsAffichageReponse = TEMPS_AFFICHAGE_REPONSE_MAX;
    private Question questionCourante;

    private Timer horloge = new Timer();
    final Handler handlerHorloge = new Handler();

    // Nombre des questions potentielles calculées
    private final int NOMBRE_QUESTIONS_CANDIDATES = 3;

    // Profondeur de l'historique des Personnes affichées
    private final int PROFONDEUR_HISTORIQUE = 10;
    private LinkedList<Personne> historiquePersonnesAffichees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        init();
    }

    private void init() {

        // Initialisation de la liste des personnes
        listePersonnes = new ArrayList<>();

        listePersonnes.add(new Personne("a", "Daniel Radcliffe", "23/07/1989"));
        listePersonnes.add(new Personne("b", "Johnny Depp", "09/06/1963"));
        listePersonnes.add(new Personne("c", "Ellen DeGeneres", "26/01/1958"));
        listePersonnes.add(new Personne("d", "Mark Harmon", "02/09/1951"));
        listePersonnes.add(new Personne("e", "Jimmy Fallon", "19/09/1974"));
        listePersonnes.add(new Personne("f", "Steve Harvey", "17/01/1957"));
        listePersonnes.add(new Personne("g", "Jim Parsons", "24/03/1973"));
        listePersonnes.add(new Personne("h", "Jon Stewart", "28/11/1962"));
        listePersonnes.add(new Personne("i", "Stephen Colbert", "13/05/1964"));
        listePersonnes.add(new Personne("j", "Tom Selleck", "29/01/1945"));
        listePersonnes.add(new Personne("k", "Bill O'Reilly", "10/09/1949"));
        listePersonnes.add(new Personne("l", "David Letterman", "12/04/1947"));
        listePersonnes.add(new Personne("m", "Oprah Winfrey", "29/01/1954"));
        listePersonnes.add(new Personne("n", "Tom Hanks", "09/07/1956"));
        listePersonnes.add(new Personne("o", "Dustin Hoffman", "08/08/1937"));
        listePersonnes.add(new Personne("p", "Jack Nicholson", "22/04/1937"));
        listePersonnes.add(new Personne("q", "Robert De Niro", "17/08/1943"));
        listePersonnes.add(new Personne("r", "Anthony Hopkins", "31/12/1937"));
        listePersonnes.add(new Personne("s", "Al Pacino", "25/04/1940"));
        listePersonnes.add(new Personne("t", "Clint Eastwood", "31/05/1930"));
        listePersonnes.add(new Personne("u", "Morgan Freeman", "01/06/1937"));
        listePersonnes.add(new Personne("v", "Denzel Washington", "28/12/1954"));
        listePersonnes.add(new Personne("w", "Russell Crowe", "07/04/1964"));
        listePersonnes.add(new Personne("x", "Sean Penn", "17/08/1960"));
        listePersonnes.add(new Personne("y", "Kate Winslet", "05/10/1975"));
        listePersonnes.add(new Personne("z", "Cate Blanchett", "14/05/1969"));
        listePersonnes.add(new Personne("aa", "Meryl Streep", "22/06/1949"));
        listePersonnes.add(new Personne("ab", "Sophia Loren", "20/09/1934"));
        listePersonnes.add(new Personne("ac", "Jodie Foster", "19/11/1962"));
        listePersonnes.add(new Personne("ad", "Halle Berry", "14/08/1966"));
        listePersonnes.add(new Personne("ae", "Diane Keaton", "05/01/1946"));
        listePersonnes.add(new Personne("af", "Angelina Jolie", "04/06/1975"));
        listePersonnes.add(new Personne("ag", "Sandra Bullock", "26/07/1964"));
        listePersonnes.add(new Personne("ah", "Natalie Portman", "09/06/1981"));
        listePersonnes.add(new Personne("ai", "Julia Roberts", "28/10/1967"));
        listePersonnes.add(new Personne("aj", "Nicole Kidman", "20/06/1967"));
        listePersonnes.add(new Personne("ak", "Mariah Carey", "27/03/1970"));
        listePersonnes.add(new Personne("al", "Céline Dion", "30/03/1968"));
        listePersonnes.add(new Personne("am", "Christina Aguilera", "18/12/1980"));
        listePersonnes.add(new Personne("an", "Beyoncé Knowles", "04/09/1981"));
        listePersonnes.add(new Personne("ao", "Adele", "05/05/1988"));
        listePersonnes.add(new Personne("ap", "Amy Lee", "13/12/1981"));
        listePersonnes.add(new Personne("aq", "Madonna", "16/08/1958"));
        listePersonnes.add(new Personne("ar", "Katy Perry", "25/10/1984"));
        listePersonnes.add(new Personne("as", "Lady Gaga", "28/03/1986"));
        listePersonnes.add(new Personne("at", "Avril Lavigne", "27/09/1984"));
        listePersonnes.add(new Personne("au", "Rihanna", "20/02/1988"));
        listePersonnes.add(new Personne("av", "Pink", "08/09/1979"));
        listePersonnes.add(new Personne("aw", "Britney Spears", "02/12/1981"));
        listePersonnes.add(new Personne("ax", "Shania Twain", "28/08/1965"));
        listePersonnes.add(new Personne("ay", "Shakira", "02/02/1977"));
        listePersonnes.add(new Personne("az", "Tina Turner", "26/10/1939"));
        listePersonnes.add(new Personne("ba", "Alicia Keys", "25/01/1981"));
        listePersonnes.add(new Personne("bb", "Cher", "20/05/1946"));
        listePersonnes.add(new Personne("bc", "Laura Pausini", "16/05/1974"));
        listePersonnes.add(new Personne("bd", "Mylène Farmer", "12/09/1961"));
        listePersonnes.add(new Personne("be", "Paul McCartney", "18/06/1942"));
        listePersonnes.add(new Personne("bf", "Jared Leto", "26/12/1971"));
        listePersonnes.add(new Personne("bg", "Axl Rose", "06/02/1962"));
        listePersonnes.add(new Personne("bh", "Eminem", "17/10/1972"));
        listePersonnes.add(new Personne("bi", "Jon Bon Jovi", "02/03/1962"));
        listePersonnes.add(new Personne("bj", "Enrique Iglesias", "08/05/1975"));
        listePersonnes.add(new Personne("bk", "Bruno Mars", "08/10/1985"));
        listePersonnes.add(new Personne("bl", "Bruce Dickinson", "07/08/1958"));
        listePersonnes.add(new Personne("bm", "Stevie Wonder", "13/05/1950"));
        listePersonnes.add(new Personne("bn", "Chris Cornell", "20/07/1964"));
        listePersonnes.add(new Personne("bo", "Ozzy Osbourne", "03/12/1948"));
        listePersonnes.add(new Personne("bp", "Matthew Bellamy", "09/06/1978"));
        listePersonnes.add(new Personne("bq", "Elton John", "25/03/1947"));
        listePersonnes.add(new Personne("br", "James Hetfield", "03/08/1963"));
        listePersonnes.add(new Personne("bs", "Mick Jagger", "26/07/1943"));
        listePersonnes.add(new Personne("bt", "Bryan Adams", "05/11/1959"));
        listePersonnes.add(new Personne("bu", "David Bowie", "08/01/1947"));
        listePersonnes.add(new Personne("bv", "Usher", "14/10/1978"));
        listePersonnes.add(new Personne("bw", "Justin Timberlake", "31/01/1981"));
        listePersonnes.add(new Personne("bx", "Bono", "10/05/1960"));
        listePersonnes.add(new Personne("by", "Serj Tankian", "21/08/1967"));
        listePersonnes.add(new Personne("bz", "Adam Lambert", "29/01/1982"));
        listePersonnes.add(new Personne("ca", "Matt Damon", "08/10/1970"));
        listePersonnes.add(new Personne("cb", "Mark Wahlberg", "05/06/1971"));
        listePersonnes.add(new Personne("cc", "George Clooney", "06/05/1961"));
        listePersonnes.add(new Personne("cd", "Brad Pitt", "18/12/1963"));
        listePersonnes.add(new Personne("ce", "Christian Bale", "30/01/1974"));
        listePersonnes.add(new Personne("cf", "Leonardo DiCaprio", "11/11/1974"));
        listePersonnes.add(new Personne("cg", "Samuel L. Jackson", "21/12/1948"));
        listePersonnes.add(new Personne("ch", "Arnold Schwarzenegger", "30/07/1947"));
        listePersonnes.add(new Personne("ci", "Sylvester Stallone", "06/07/1946"));
        listePersonnes.add(new Personne("cj", "Jet Li", "26/04/1963"));
        listePersonnes.add(new Personne("ck", "Keanu Reeves", "02/09/1964"));
        listePersonnes.add(new Personne("cl", "Jackie Chan", "07/04/1954"));

        // Initialisation de l'historique
        historiquePersonnesAffichees = new LinkedList<>();

        chargerMeilleurScore();
        afficherQuestion();

        // Préparation du timer de réponse
        TimerReponse callback = new TimerReponse();
        horloge.schedule(callback, 1000, 1000);

    }

    private void setTempsReponse(int valeur) {
        TextView timerView = (TextView) findViewById(R.id.timer);
        timerView.setText(String.valueOf(valeur));
    }

    private void afficherQuestion() {
        reponseDonnee = Question.Reponse.REPONSE_INCONNUE;
        Question question = choisirQuestion();
        questionCourante = question;
        afficherQuestion(question);
    }

    private Question choisirQuestion() {
        int idA = 0;
        int idB = 0;
        Question meilleureCandidate = null;

        // Liste des questions candidates
        List<Question> listeQuestionsCandidates = new ArrayList<>();

        // Tirage au sort des questions candidates
        do {
            idA = (int) Math.round(Math.random() * (listePersonnes.size() - 1));
            idB = (int) Math.round(Math.random() * (listePersonnes.size() - 1));
            if (idA != idB) {
                listeQuestionsCandidates.add(new Question(listePersonnes.get(idA), listePersonnes.get(idB)));
            }
        }
        while (listeQuestionsCandidates.size() < NOMBRE_QUESTIONS_CANDIDATES);

        // Détermination du meilleur candidat
        int meilleureNote = 0;
        for (Question candidate : listeQuestionsCandidates) {
            if (meilleureCandidate == null) {
                meilleureCandidate = candidate;
                meilleureNote = evaluerQuestion(candidate);
            }
            else {
                int noteCandidate = evaluerQuestion(candidate);
                if (noteCandidate < meilleureNote) {
                    meilleureCandidate = candidate;
                    meilleureNote = noteCandidate;
                }
            }
        }

        // Mise à jour de l'historique
        historiquePersonnesAffichees.addFirst(meilleureCandidate.getPersonneA());
        historiquePersonnesAffichees.addFirst(meilleureCandidate.getPersonneB());
        while (historiquePersonnesAffichees.size() > PROFONDEUR_HISTORIQUE) {
            historiquePersonnesAffichees.removeLast();
        }

        return meilleureCandidate;
    }

    private int evaluerQuestion(Question question) {
        int note = 0;

        // Evaluation de la différence d'age
        int noteAge = Math.abs(question.getPersonneA().getAge() - question.getPersonneB().getAge());

        // Evaluation de la présence dans l'historique
        int noteHistorique = notePosition(historiquePersonnesAffichees.indexOf(question.getPersonneA()))
                           + notePosition(historiquePersonnesAffichees.indexOf(question.getPersonneB()));

        note = noteAge + noteHistorique;

        return note;
    }

    private int notePosition(int position) {
        int note = 0;
        if (position == -1) {
            note = 0;
        }
        else {
            note = PROFONDEUR_HISTORIQUE - ((9 * position) / PROFONDEUR_HISTORIQUE);
        }
        return note;
    }

    private void afficherQuestion(Question question) {
        Resources r = getResources();

        // Affichage de la Personne A
        ImageView photoA = (ImageView)findViewById(R.id.photoA);
        photoA.setImageDrawable(r.getDrawable(r.getIdentifier(question.getPersonneA().getId(), "drawable", "fr.entrecode.older")));

        TextView nomA = (TextView)findViewById(R.id.nomA);
        nomA.setText(question.getPersonneA().getNom());

        TextView ageA = (TextView)findViewById(R.id.ageA);
        ageA.setText("??");

        // Affichage de la Personne B
        ImageView photoB = (ImageView)findViewById(R.id.photoB);
        photoB.setImageDrawable(r.getDrawable(r.getIdentifier(question.getPersonneB().getId(), "drawable", "fr.entrecode.older")));

        TextView nomB = (TextView)findViewById(R.id.nomB);
        nomB.setText(question.getPersonneB().getNom());

        TextView ageB = (TextView)findViewById(R.id.ageB);
        ageB.setText("??");

        initialiserCouleurs();

        reponseDonnee = Question.Reponse.REPONSE_INCONNUE;
    }

    private void initialiserCouleurs() {
        coloriserReponseA(getResources().getColor(R.color.claireNormale));
        coloriserReponseB(getResources().getColor(R.color.claireNormale));
        coloriserTimer(getResources().getColor(R.color.claireNormale));
    }

    private void afficherReponse() {
        Question question = questionCourante;

        etat = Etat.AFFICHER_REPONSE;

        TextView ageA = (TextView)findViewById(R.id.ageA);
        ageA.setText(String.valueOf(question.getPersonneA().getAge()));

        TextView ageB = (TextView)findViewById(R.id.ageB);
        ageB.setText(String.valueOf(question.getPersonneB().getAge()));

        // Si le joueur a donné la bonne réponse
        if (question.estLaBonneReponse(reponseDonnee)) {
            score++;
            TextView scoreView = (TextView)findViewById(R.id.score);
            scoreView.setText(String.valueOf(score));
        }
        // Si le joueur a donné la mauvaise réponse
        else {
            resetScore();
        }

        if (question.getBonneReponse() == Question.Reponse.REPONSE_A) {
            coloriserReponseA(getResources().getColor(R.color.bon));
            coloriserReponseB(getResources().getColor(R.color.mauvais));
        }
        else {
            coloriserReponseA(getResources().getColor(R.color.mauvais));
            coloriserReponseB(getResources().getColor(R.color.bon));
        }

        mettreAJourMeilleurScore();
    }

    private void coloriserReponseA(int couleur) {
        TextView nomA = (TextView)findViewById(R.id.nomA);
        nomA.setTextColor(couleur);

        TextView ageA = (TextView)findViewById(R.id.ageA);
        ageA.setTextColor(couleur);
    }

    private void coloriserReponseB(int couleur) {
        TextView nomB = (TextView)findViewById(R.id.nomB);
        nomB.setTextColor(couleur);

        TextView ageB = (TextView)findViewById(R.id.ageB);
        ageB.setTextColor(couleur);
    }

    private void coloriserTimer(int couleur) {
        TextView timer = (TextView)findViewById(R.id.timer);
        timer.setTextColor(couleur);
    }

    private void resetScore() {
        TextView score = (TextView)findViewById(R.id.score);
        this.score = 0;
        score.setText(String.valueOf(this.score));
    }

    private void mettreAJourMeilleurScore() {
        TextView score = (TextView)findViewById(R.id.score);
        TextView meilleurScore = (TextView)findViewById(R.id.meilleur_score);
        Integer valeurScore = Integer.parseInt(score.getText().toString());
        Integer valeurMeilleurScore = Integer.parseInt(meilleurScore.getText().toString());
        if (valeurScore > valeurMeilleurScore) {
                this.meilleurScore = valeurScore;
                meilleurScore.setText(String.valueOf(score.getText()));
                sauvegarderMeilleurScore();
        }
    }

    private void sauvegarderMeilleurScore() {
        SharedPreferences prefs = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("meilleurScore", meilleurScore);
        editor.commit();
    }

    private void chargerMeilleurScore() {
        SharedPreferences prefs = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        meilleurScore = prefs.getInt("meilleurScore", 0);
        TextView meilleurScoreView = (TextView) findViewById(R.id.meilleur_score);
        meilleurScoreView.setText(String.valueOf(meilleurScore));
    }

    public void choisirA(View view) {
        if (reponseDonnee == Question.Reponse.REPONSE_INCONNUE) {
            reponseDonnee = Question.Reponse.REPONSE_A;
            afficherReponse();
        }
    }

    public void choisirB(View view) {
        if (reponseDonnee == Question.Reponse.REPONSE_INCONNUE) {
            reponseDonnee = Question.Reponse.REPONSE_B;
            afficherReponse();
        }
    }

    class TimerReponse extends TimerTask {

        @Override
        public void run() {
            handlerHorloge.post(new Runnable() {

                @Override
                public void run() {
                    switch(etat) {
                        case AFFICHER_QUESTION:
                            tempsAffichageQuestion -=1;
                            setTempsReponse(tempsAffichageQuestion);
                            break;
                        case AFFICHER_REPONSE:
                            tempsAffichageReponse -=1;
                            break;

                        default:
                            break;
                    }

                    if (tempsAffichageQuestion <= 0) {
                        tempsAffichageQuestion = TEMPS_AFFICHAGE_QUESTION_MAX;
                        etat = Etat.AFFICHER_REPONSE;
                        coloriserTimer(getResources().getColor(R.color.mauvais));
                        afficherReponse();
                    }

                    if (tempsAffichageReponse <= 0) {
                            tempsAffichageReponse = TEMPS_AFFICHAGE_REPONSE_MAX;
                            tempsAffichageQuestion = TEMPS_AFFICHAGE_QUESTION_MAX;
                            setTempsReponse(tempsAffichageQuestion);
                            etat = Etat.AFFICHER_QUESTION;
                            afficherQuestion();
                    }
                }
            });
        }
    }
}
