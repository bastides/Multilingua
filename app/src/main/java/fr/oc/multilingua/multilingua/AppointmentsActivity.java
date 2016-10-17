package fr.oc.multilingua.multilingua;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.oc.multilingua.multilingua.sqlite.Appointment;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;

public class AppointmentsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_appointments);

        setTitle("Mes dates de présentielles");

        DBHelper db = new DBHelper(this);
        List<Appointment> appointmentsList = db.selectAllAppointment();
        List<Appointment> newAppointmentsList = new ArrayList<Appointment>();

        for (Appointment appointment : appointmentsList) {
            long currentTimestamp = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = sdf.parse(appointment.get_date());
                long timestampToDelete = date.getTime();
                if (currentTimestamp > timestampToDelete) {
                    db.deleteAppointment(appointment.get_id());
                } else {
                    newAppointmentsList.add(appointment);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // MISE EN PLACE DE L'ALARM MANAGER PROGRAMMANT LES NOTIFICATIONS
        final Intent intent = new Intent(this, AlarmReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (newAppointmentsList.size() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date date = sdf.parse(newAppointmentsList.get(0).get_date());
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime() - 3600000, pendingIntent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date date = sdf.parse(newAppointmentsList.get(0).get_date());
                    alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime() - 3600000, pendingIntent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_appointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppointmentAdapter adapter = new AppointmentAdapter(newAppointmentsList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton addAppointment = (FloatingActionButton) findViewById(R.id.add_appointment);
        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentsActivity.this, AddAppointmentActivity.class);
                startActivity(intent);
            }
        });

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("close"));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
