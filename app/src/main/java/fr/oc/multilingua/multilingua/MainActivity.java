package fr.oc.multilingua.multilingua;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import fr.oc.multilingua.multilingua.preferences.UserPreferencesManager;
import fr.oc.multilingua.multilingua.sqlite.Appointment;
import fr.oc.multilingua.multilingua.sqlite.Course;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;
import fr.oc.multilingua.multilingua.sqlite.Quiz;
import fr.oc.multilingua.multilingua.sqlite.User;

public class MainActivity extends AppCompatActivity {

    protected boolean registrationComplete;
    private final IntentFilter intentFilter = new IntentFilter("close");

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("close".equals(intent.getAction())) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);

        Button registrationButton = (Button) findViewById(R.id.btn_registration);
        Button connexionButton = (Button) findViewById(R.id.btn_connexion);

        registrationComplete = getIntent().getBooleanExtra("registrationComplete", false);
        if (registrationComplete) {
            AlertDialog.Builder error = new AlertDialog.Builder(this);
            error.setTitle("Bravo !");
            error.setMessage("Inscription réussie.");
            error.setPositiveButton(android.R.string.ok, null);
            error.show();
        }

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        connexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConnexionActivity.class);
                startActivity(intent);
            }
        });

        //Log.v("Préférence utilisateur", String.valueOf(UserPreferencesManager.getInstance(this).loadId()));

        if (UserPreferencesManager.getInstance(this).loadId() != 0) {
            Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
            startActivity(intent);
        }

        // DBHelper db = new DBHelper(this);
        /*db.insertUser("Bastide", "Sébastien", "0000", "bastide.sebastien@gmail.com");

        db.insertCourse(
                "Hello !",
                "Vous allez apprendre à dire bonjour et au revoir à quelqu'un, à déterminer quelle expression utiliser.",
                "Cours débutant",
                "Hello appartient à l'anglais standard et est utilisé, comme le mot bonjour, également dans le contexte du travail et lors de conversations formelles. Good morning (bonjour) signifie littéralement bon matin et est employé jusqu'à environ midi. Good evening (bonsoir) est utilisé environ à partir de 18 heures. Goodnight s'utilise lorsqu'on va dormir.",
                0
        );
        db.insertCourse(
                "Les pronoms",
                "Vous allez apprendre les pronoms personnels en anglais, à vous présenter.",
                "Cours débutant",
                "I, you, he, she, it, we, you, they, signifient litréralement je, tu, il, elle, ce, nous, vous, ils/elles. Cas particulier it : on désigne par it (il/elle) un objet ou un animal. It est également un pronom impersonnel qui se traduit par il ou ce.",
                0
        );
        db.insertCourse(
                "La lettre \"e\"",
                "Vous allez apprendre à prononcer la lettre \"e\" en fonction de sa position dans le mot.",
                "Cours débutant",
                "Comme les autres voyelles, la lettre e se prononce différemment selon les mots. Quand un mot se termine par la lettre e, on ne la prononce pas : name. La lettre e en fin de mot change la prononciation des voyelles dans le mot : fin - fine.",
                0
        );
        db.insertCourse(
                "Les pronoms d'objet",
                "Vous allez apprendre à utiliser les pronoms d'objet afin d'éviter les répétitions.",
                "Cours intermédiaire",
                "Lorsque l'on veut remplacer un complément d'objet dans une phrase, parce que l'on veut éviter la répétition par exemple, on emploie un pronom objet. me, him, her, it, us, you, them signifient littéralement me/moi, le/lui, la/lui, le/lui, nous, te/vous, les/leur. En anglais, on ne fait pas de différence entre le et lui et cela vaut pour toutes les personnes.",
                0
        );
        db.insertCourse(
                "Les comparatifs",
                "Vous allez apprendre à faire une comparaison entre deux objets, personnes etc..",
                "Cours intermédiaire",
                "Pour faire une comparaison (former le comparatif) on ajoute -er à l'adjectif. Cette règle est valable pour tous les adjectifs monosyllabiques : quick - quicker. Pour les adjectifs qui se terminent par une voyelle + une consonne, la consonne est doublée. Puis on ajoute -er : fit - fitter. Quand un adjectif finit par la lettre -e, on ajoute simplement -r à la fin : nice - nicer. Lorsqu'un adjectif finit par -y, la terminaison se transforme en -ier au comparatif : happy - happier. Dans tous les autres cas, on forme la comparaison avec more (more + adjectif) : difficult - more difficult.",
                0
        );
        db.insertCourse(
                "Les adverbes",
                "Vous allez apprendre à contruire un adverbe par rapport à son verbe.",
                "Cours intermédiaire",
                "Les adjectifs décrivent des personnes, des choses et des lieux. Les adverbes, eux, décrivent des verbes et des actions. La plupart des adverbes se forment en ajoutant -ly à l'adjectif : loud - loudly. Lorsqu'un adjectif se finit par un -l, on ajoute simplement -ly pour former l'adverbe : beautiful - beautifully. Lorsqu'un adjectif se finit par -y, on remplace ce y par -ly que l'on précède d'un i : crazy - crazily. Lorsqu'un adjectif se finit par -le, le dernier -e devient -y et on n'ajoute pas de terminaison en plus : terrible - terribly. Lorsqu'un adjectif finit en -ic, on forme l'adverbe en ajoutant -ally : electronic - electronically.",
                0
        );

        db.insertQuiz(
                "Pour dire bonjour à quelqun après 18h on utilise \"Good morning\".",
                0,
                1
        );
        db.insertQuiz(
                "Dans le context du travail, pour dire bonjour on utilise \"Hello\".",
                1,
                1
        );
        db.insertQuiz(
                "On peut désigner un animal par le mot \"it\".",
                1,
                2
        );
        db.insertQuiz(
                "En anglais \"you\" signifie \"nous\".",
                1,
                2
        );
        db.insertQuiz(
                "La lettre \"e\" se prononce de la même manière quelque soit les mots en anglais.",
                0,
                3
        );
        db.insertQuiz(
                "Quand un mot se termine par la lettre e, on ne la prononce pas.",
                1,
                3
        );
        db.insertQuiz(
                "Les pronoms d'objet n'existe pas en anglais.",
                0,
                4
        );
        db.insertQuiz(
                "En anglais \"her\" signifie \"la/lui\".",
                1,
                4
        );
        db.insertQuiz(
                "Pour faire une comparaison, il suffit de rajouter -er pour un adjectif monosyllabiques.",
                1,
                5
        );
        db.insertQuiz(
                "Pour faire une comparaison, certains adjectifs se voit précéder du mot \"more\".",
                1,
                5
        );
        db.insertQuiz(
                "Pour former un adverbe il siffit de rajouter -ller.",
                0,
                6
        );
        db.insertQuiz(
                "Pour former un adverbe à partir d'un adjectif se terminant par -ic, il suffit de rajouter un \"y\".",
                0,
                6
        );*/

        /*for (User u : db.selectAllUsers()) {
            Log.v("List users", String.valueOf(u.get_id()));
            Log.v("List users", String.valueOf(u.get_lastName()));
            Log.v("List users", String.valueOf(u.get_firstName()));
            Log.v("List users", String.valueOf(u.get_password()));
            Log.v("List users", String.valueOf(u.get_email()));
        }*/

        /*for (Course c : db.selectAllCourses()) {
            Log.v("Liste des cours", String.valueOf(c.get_id()));
            Log.v("Liste des cours", String.valueOf(c.get_title()));
            Log.v("Liste des cours", String.valueOf(c.get_description()));
            Log.v("Liste des cours", String.valueOf(c.get_category()));
            Log.v("Liste des cours", String.valueOf(c.get_course()));
            Log.v("Liste des cours", String.valueOf(c.get_complete()));
        }*/

        /*for (Quiz q : db.selectAllQuiz()) {
            Log.v("Liste des quiz", String.valueOf(q.get_id()));
            Log.v("Liste des quiz", String.valueOf(q.get_question()));
            Log.v("Liste des quiz", String.valueOf(q.get_answer()));
            Log.v("Liste des quiz", String.valueOf(q.get_courseId()));
        }*/

        /*for (Quiz q : db.selectQuizByCourseId(1)) {
            Log.v("Liste des quiz", String.valueOf(q.get_question()));
        }*/

        /*for (Appointment a : db.selectAllAppointment()) {
            Log.v("Liste des RDV", String.valueOf(a.get_title()));
            Log.v("Liste des RDV", String.valueOf(a.get_date()));
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
