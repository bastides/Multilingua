package fr.oc.multilingua.multilingua;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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

import fr.oc.multilingua.multilingua.Notifications.AlertReceiver;
import fr.oc.multilingua.multilingua.sqlite.Appointment;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;

public class AppointmentsActivity extends AppCompatActivity {

    boolean isNotificationActive = false;
    int notificationId = 33;

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_appointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppointmentAdapter adapter = new AppointmentAdapter(newAppointmentsList);
        recyclerView.setAdapter(adapter);

        Button addAppointment = (Button) findViewById(R.id.add_appointment);
        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentsActivity.this, AddAppointmentActivity.class);
                startActivity(intent);
                AlarmNotification(createNotification("5 second delay", "Mon titre"), 1475668012140L, 3600000);
            }
        });

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("close"));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    private void AlarmNotification(Notification notification, long event, int delay) {

        Intent notificationIntent = new Intent(this, AlertReceiver.class);
        notificationIntent.putExtra(AlertReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(AlertReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = event - delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification createNotification(String content, String title) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.androcool);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return builder.build();
        } else {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
