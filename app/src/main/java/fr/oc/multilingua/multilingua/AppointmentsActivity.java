package fr.oc.multilingua.multilingua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import fr.oc.multilingua.multilingua.sqlite.Appointment;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;

public class AppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        setTitle("Mes dates de présentielles");

        DBHelper db = new DBHelper(this);
        List<Appointment> appointmentsList = db.selectAllAppointment();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_appointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppointmentAdapter adapter = new AppointmentAdapter(appointmentsList);
        recyclerView.setAdapter(adapter);

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
