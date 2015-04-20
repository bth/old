package fr.entrecode.older;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ben on 19/04/15.
 */
public class JeuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        init();
    }

    private void init() {

        // Initialisation de la liste des Personnes
        Personne p1 = new Personne("Daniel Radcliffe", "23/07/1989");
        Log.d("debug", String.valueOf(p1.getAge()));

        Personne p2 = new Personne("Daniel Radcliffe", "08/12/1988");
        Log.d("debug", String.valueOf(p2.getAge()));

        Personne p3 = new Personne("Daniel Radcliffe", "19/04/1988");
        Log.d("debug", String.valueOf(p3.getAge()));
    }
}
