package fr.oc.multilingua.multilingua;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.oc.multilingua.multilingua.preferences.UserPreferencesManager;
import fr.oc.multilingua.multilingua.sqlite.Course;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;
import fr.oc.multilingua.multilingua.sqlite.User;

public class MainActivity extends AppCompatActivity {

    protected String successMassage;
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
        TextView message = (TextView) findViewById(R.id.success_registration);

        successMassage = getIntent().getStringExtra("successMessage");
        message.setText(successMassage);

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

        /*if (UserPreferencesManager.getInstance(getBaseContext()).loadId() != 0) {
        }*/

        //Log.v("Préférence utilisateur", String.valueOf(UserPreferencesManager.getInstance(getBaseContext()).loadId()));

        DBHelper db = new DBHelper(this);

        /*db.insertCourse(
                "Les adverbes",
                "Vous allez apprendre à contruire un adverbe par rapport à son verbe.",
                "Cours intermédiaire",
                "Vous allez apprendre à contruire un adverbe par rapport à son verbe. Lorsqu'un adjectif se finit par un -l, on ajoute simplement -ly pour former l'adverbe : beautiful - beautifully. Lorsqu'un adjectif se finit par -y, on remplace ce y par -ly que l'on précède d'un i : crazy - crazily. Lorsqu'un adjectif se finit par -le, le dernier -e devient -y et on n'ajoute pas de terminaison en plus : terrible - terribly. Lorsqu'un adjectif finit en -ic, on forme l'adverbe en ajoutant -ally : electronic - electronically.",
                0
        );*/

        /*for (User u : db.selectAllUsers()) {
            Log.v("List users", String.valueOf(u.get_id()));
            Log.v("List users", String.valueOf(u.get_lastName()));
            Log.v("List users", String.valueOf(u.get_firstName()));
            Log.v("List users", String.valueOf(u.get_password()));
            Log.v("List users", String.valueOf(u.get_email()));
        }*/

        for (Course c : db.selectAllCourses()) {
            Log.v("Liste des cour", String.valueOf(c.get_id()));
            Log.v("Liste des cour", String.valueOf(c.get_title()));
            Log.v("Liste des cour", String.valueOf(c.get_description()));
            Log.v("Liste des cour", String.valueOf(c.get_category()));
            Log.v("Liste des cour", String.valueOf(c.get_course()));
            Log.v("Liste des cour", String.valueOf(c.get_complete()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
