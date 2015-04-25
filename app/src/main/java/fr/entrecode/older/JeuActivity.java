package fr.entrecode.older;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
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

    private final int TEMPS_AFFICHAGE_QUESTION_MAX = 10;
    private final int TEMPS_AFFICHAGE_REPONSE_MAX = 1;

    private List<Personne> listePersonnes;
    private Boolean reponseDonnee = false;
    private Etat etat = Etat.AFFICHER_QUESTION;
    private int score = 0;
    private int meilleurScore = 0;
    private int tempsAffichageQuestion = TEMPS_AFFICHAGE_QUESTION_MAX;
    private int tempsAffichageReponse = TEMPS_AFFICHAGE_REPONSE_MAX;

    private Timer horloge = new Timer();
    final Handler handlerHorloge = new Handler();

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
        reponseDonnee = false;
        //coloriserTimer(Color.BLACK);
        Question question = choisirQuestion();
        afficherQuestion(question);
    }

    private Question choisirQuestion() {
        int idA = 0;
        int idB = 0;
        do {
            idA = (int) Math.round(Math.random() * (listePersonnes.size() - 1));
            idB = (int) Math.round(Math.random() * (listePersonnes.size() - 1));
        }
        while (idA == idB);
        return new Question(listePersonnes.get(idA), listePersonnes.get(idB));
    }

    private void afficherQuestion(Question question) {
        Resources r = getResources();

        // Affichage de la Personne A
        ImageView photoA = (ImageView)findViewById(R.id.photoA);
        photoA.setImageDrawable(r.getDrawable(r.getIdentifier(question.getPersonneA().getId(), "drawable", "fr.entrecode.older")));

        TextView nomA = (TextView)findViewById(R.id.nomA);
        nomA.setText(question.getPersonneA().getNom());

        TextView ageA = (TextView)findViewById(R.id.ageA);
        ageA.setText(String.valueOf(question.getPersonneA().getAge()));


        // Affichage de la Personne B
        ImageView photoB = (ImageView)findViewById(R.id.photoB);
        photoB.setImageDrawable(r.getDrawable(r.getIdentifier(question.getPersonneB().getId(), "drawable", "fr.entrecode.older")));

        TextView nomB = (TextView)findViewById(R.id.nomB);
        nomB.setText(question.getPersonneB().getNom());

        TextView ageB = (TextView)findViewById(R.id.ageB);
        ageB.setText(String.valueOf(question.getPersonneB().getAge()));
    }

    private void afficherReponse() {
        mettreAJourMeilleurScore();
    }

    private void coloriserReponseA(int couleur) {
        TextView reponseA = (TextView)findViewById(R.id.ageA);
        reponseA.setTextColor(couleur);
    }

    private void coloriserReponseB(int couleur) {
        TextView reponseB = (TextView)findViewById(R.id.ageB);
        reponseB.setTextColor(couleur);
    }

    private void coloriserTimer(int couleur) {
        TextView timer = (TextView)findViewById(R.id.timer);
        timer.setTextColor(couleur);
    }

    private void resetScore() {
        TextView score = (TextView)findViewById(R.id.score);
        score.setText("0");
        this.score = 0;
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
        Log.d("debug", "Réponse A");
    }

    public void choisirB(View view) {
        Log.d("debug", "Réponse B");
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
                        tempsAffichageQuestion = TEMPS_AFFICHAGE_QUESTION_MAX + 1;
                        etat = Etat.AFFICHER_REPONSE;
                        /*coloriserTimer(Color.RED);*/
                        resetScore();
                        afficherReponse();
                    }

                    if (tempsAffichageReponse <= 0) {
                            tempsAffichageReponse = TEMPS_AFFICHAGE_REPONSE_MAX + 1;
                            tempsAffichageQuestion = TEMPS_AFFICHAGE_QUESTION_MAX + 1;
                            setTempsReponse(tempsAffichageQuestion);
                            etat = Etat.AFFICHER_QUESTION;
                            afficherQuestion();
                    }
                }
            });
        }
    }
}
