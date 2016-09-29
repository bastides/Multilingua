package fr.oc.multilingua.multilingua;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.oc.multilingua.multilingua.sqlite.Appointment;

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
        private Appointment _currentAppointment;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            _title = ((TextView) itemView.findViewById(R.id.appointment_title));
            _date = ((TextView) itemView.findViewById(R.id.appointment_date));
        }

        public void setAppointment(Appointment appointment) {
            this._currentAppointment = appointment;
            _title.setText(this._currentAppointment.get_title());
            _date.setText(this._currentAppointment.get_date());
        }
    }
}

