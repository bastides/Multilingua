package fr.oc.multilingua.multilingua.sqlite;

import android.provider.BaseColumns;

public class User {

    private int _id;
    private String _lastName;
    private String _firstName;
    private String _password;
    private String _email;

    public User() {}

    public User(int id, String lastName, String firstName, String password, String email) {
        this._id = id;
        this._lastName = lastName;
        this._firstName = firstName;
        this._password = password;
        this._email = email;
    }

    public static abstract class UserEntries implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_LASTNAME = "lastname";
        public static final String COLUMN_NAME_FIRSTNAME = "firstname";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_EMAIL = "email";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_USER =
            "CREATE TABLE " + UserEntries.TABLE_NAME + " ("
                    + UserEntries._ID + " INTEGER PRIMARY KEY,"
                    + UserEntries.COLUMN_NAME_LASTNAME + TEXT_TYPE + COMMA_SEP
                    + UserEntries.COLUMN_NAME_FIRSTNAME + TEXT_TYPE + COMMA_SEP
                    + UserEntries.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP
                    + UserEntries.COLUMN_NAME_EMAIL + TEXT_TYPE + ")";

    public static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + UserEntries.TABLE_NAME;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_lastName() {
        return _lastName;
    }

    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }
}
