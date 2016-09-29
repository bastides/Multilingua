package fr.oc.multilingua.multilingua.sqlite;

import android.provider.BaseColumns;

public class Appointment {

    private int _id;
    private String _title;
    private String _date;

    public Appointment() {}

    public Appointment(int id, String title, String date) {
        this._id = id;
        this._title = title;
        this._date = date;
    }

    public static abstract class AppointmentEntries implements BaseColumns {
        public static final String TABLE_NAME = "appointment";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_APPOINTMENT =
            "CREATE TABLE " + AppointmentEntries.TABLE_NAME + " ("
                    + AppointmentEntries._ID + " INTEGER PRIMARY KEY,"
                    + AppointmentEntries.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
                    + AppointmentEntries.COLUMN_NAME_DATE + TEXT_TYPE + ")";

    public static final String SQL_DELETE_APPOINTMENT =
            "DROP TABLE IF EXISTS " + AppointmentEntries.TABLE_NAME;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}
