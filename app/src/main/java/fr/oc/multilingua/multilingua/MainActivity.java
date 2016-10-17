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

    // FERME L'ACTIVITE SI ON FAIT PRECEDENT LORSQUE L'ON SE TROUVE SUR LA PAGE DES COURS
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

        // SI UN ID DANS LES PREFERENCES UTILISATEUR A ETE ENREGISTRE, ON VA DIRECTEMENT A LA PAGE DES COURS
        if (UserPreferencesManager.getInstance(this).loadId() != 0) {
            Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
