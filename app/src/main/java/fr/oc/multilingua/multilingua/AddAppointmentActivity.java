package fr.oc.multilingua.multilingua;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import fr.oc.multilingua.multilingua.sqlite.Appointment;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;

public class AddAppointmentActivity extends AppCompatActivity {

    Button btnDatePicker, btnTimePicker, btnValidate;
    TextView txtDate, txtTime, inputTitle;
    private TextInputLayout inputLayoutTitle;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int dbYear, dbMonth, dbDay, dbHour, dbMinute;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addappointment);

        setTitle("Nouvelle date");

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        btnValidate = (Button) findViewById(R.id.btn_validate);
        txtDate = (TextView) findViewById(R.id.in_date);
        txtTime = (TextView) findViewById(R.id.in_time);
        inputTitle = (EditText) findViewById(R.id.input_appointment_title);
        inputLayoutTitle = (TextInputLayout) findViewById(R.id.input_layout_appointment_title);
        inputTitle.addTextChangedListener(new MyTextWatcher(inputTitle));

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddAppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            dbYear = year;
                            dbMonth = monthOfYear;
                            dbDay = dayOfMonth;
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddAppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                            dbHour = hourOfDay;
                            dbMinute = minute;
                        }
                    }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (submitForm()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Calendar calendar = new GregorianCalendar(dbYear, dbMonth, dbDay, dbHour, dbMinute);
                        sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                        String date = sdf.format(calendar.getTime());

                        DBHelper db = new DBHelper(AddAppointmentActivity.this);
                        db.insertAppointment(
                                inputTitle.getText().toString(),
                                date
                        );
                    }
                    Intent intent = new Intent(AddAppointmentActivity.this, AppointmentsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Validating form
     */
    private boolean submitForm() {
        return validateTitle();
    }

    /**
     * Validating input lastname
     */
    private boolean validateTitle() {
        if (inputTitle.getText().toString().trim().isEmpty()) {
            inputLayoutTitle.setError(getString(R.string.err_msg_appointment_title));
            requestFocus(inputTitle);
            return false;
        } else {
            inputLayoutTitle.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_appointment_title:
                    validateTitle();
                    break;
            }
        }
    }
}
