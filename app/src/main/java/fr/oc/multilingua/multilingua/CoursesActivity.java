package fr.oc.multilingua.multilingua;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fr.oc.multilingua.multilingua.preferences.UserPreferencesManager;

public class CoursesActivity extends AppCompatActivity {

    public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("close"));

        setTitle("Cours");


        /*btn.findViewById(R.id.disconnection);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferencesManager.getInstance(getBaseContext()).saveId(0);
            }
        });
        Log.v("Préférence utilisateur", String.valueOf(UserPreferencesManager.getInstance(getBaseContext()).loadId()));*/
    }
}
