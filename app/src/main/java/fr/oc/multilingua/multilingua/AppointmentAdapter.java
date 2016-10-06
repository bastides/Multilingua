package fr.oc.multilingua.multilingua;

import android.app.AlarmManager;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import fr.oc.multilingua.multilingua.Notifications.AlertReceiver;
import fr.oc.multilingua.multilingua.sqlite.Appointment;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private List<Appointment> _appointmentsList;

    public AppointmentAdapter (List<Appointment> appointmentsList) {
        this._appointmentsList = appointmentsList;
    }

    @Override
    public AppointmentAdapter.AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentAdapter.AppointmentViewHolder holder, int position) {
        holder.setAppointment(_appointmentsList.get(position));
    }

    @Override
    public int getItemCount() {
        return _appointmentsList.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView _title;
        private final TextView _date;
        private final Button _deleteButton;
        private Appointment _currentAppointment;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            _title = ((TextView) itemView.findViewById(R.id.appointment_title));
            _date = ((TextView) itemView.findViewById(R.id.appointment_date));
            _deleteButton = ((Button) itemView.findViewById(R.id.delete_appointment));

            _deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBHelper db = new DBHelper(view.getContext());
                    db.deleteAppointment(_currentAppointment.get_id());
                    Intent intent = new Intent(view.getContext(), AppointmentsActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void setAppointment(Appointment appointment) {
            this._currentAppointment = appointment;
            _title.setText(this._currentAppointment.get_title());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = sdf.parse(this._currentAppointment.get_date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy à HH:mm");
                _date.setText(sdf2.format(date));
                // date.getTime(); Retourne le timestamp en Mills
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}

