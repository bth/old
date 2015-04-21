package fr.entrecode.older;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 19/04/15.
 */
public class JeuActivity extends Activity {

    private List<Personne> listePersonnes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        init();
    }

    private void init() {

        // Initialisation de la liste des personnes
        listePersonnes = new ArrayList<>();

        Personne p1 = new Personne("a", "Daniel Radcliffe", "23/07/1989");
        Log.d("debug", String.valueOf(p1.getAge()));

        Personne p2 = new Personne("a", "Daniel Radcliffe", "08/12/1988");
        Log.d("debug", String.valueOf(p2.getAge()));

        Personne p3 = new Personne("c", "Daniel Radcliffe", "19/04/1988");
        Log.d("debug", String.valueOf(p3.getAge()));

        afficherQuestion(p1, p2);
    }

    private void afficherQuestion(Personne personneA, Personne personneB) {
        Resources r = getResources();

        // Affichage de la Personne A
        ImageView photoA = (ImageView)findViewById(R.id.photoA);
        photoA.setImageDrawable(r.getDrawable(r.getIdentifier(personneA.getId(), "drawable", "fr.entrecode.older")));

        TextView nomA = (TextView)findViewById(R.id.nomA);
        nomA.setText(personneA.getNom());

        TextView ageA = (TextView)findViewById(R.id.ageA);
        ageA.setText(String.valueOf(personneA.getAge()));


        // Affichage de la Personne B
        ImageView photoB = (ImageView)findViewById(R.id.photoB);
        photoB.setImageDrawable(r.getDrawable(r.getIdentifier(personneB.getId(), "drawable", "fr.entrecode.older")));

        TextView nomB = (TextView)findViewById(R.id.nomB);
        nomB.setText(personneB.getNom());

        TextView ageB = (TextView)findViewById(R.id.ageB);
        ageB.setText(String.valueOf(personneB.getAge()));
    }
}
