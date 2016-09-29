package fr.oc.multilingua.multilingua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        setTitle("Mes dates de présentielles");

        Button addAppointment = (Button) findViewById(R.id.add_appointment);
        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentsActivity.this, AddAppointmentActivity.class);
                startActivity(intent);
            }
        });
    }
}
