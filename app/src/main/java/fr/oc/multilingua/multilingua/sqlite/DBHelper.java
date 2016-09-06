package fr.oc.multilingua.multilingua.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "oc.multilingua.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.SQL_CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(User.SQL_DELETE_USER);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertUser(String lastname, String firstname, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.UserEntries.COLUMN_NAME_LASTNAME, lastname);
        values.put(User.UserEntries.COLUMN_NAME_FIRSTNAME, firstname);
        values.put(User.UserEntries.COLUMN_NAME_PASSWORD, password);
        values.put(User.UserEntries.COLUMN_NAME_EMAIL, email);

        int newRowId = (int) db.insert(User.UserEntries.TABLE_NAME, null, values);
    }

    public User selectUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                User.UserEntries._ID,
                User.UserEntries.COLUMN_NAME_LASTNAME,
                User.UserEntries.COLUMN_NAME_FIRSTNAME,
                User.UserEntries.COLUMN_NAME_PASSWORD,
                User.UserEntries.COLUMN_NAME_EMAIL
        };

        String selection = User.UserEntries.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                User.UserEntries.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            User user = new User(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            return user;
        } else {
            return null;
        }
    }

    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                User.UserEntries._ID,
                User.UserEntries.COLUMN_NAME_LASTNAME,
                User.UserEntries.COLUMN_NAME_FIRSTNAME,
                User.UserEntries.COLUMN_NAME_PASSWORD,
                User.UserEntries.COLUMN_NAME_EMAIL
        };

        Cursor cursor = db.query(
                User.UserEntries.TABLE_NAME,
                projection,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = new User(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    public void insertCourse(String title, String description, String category, String course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.CourseEntries.COLUMN_NAME_TITLE, title);
        values.put(Course.CourseEntries.COLUMN_NAME_DESCRIPTION, description);
        values.put(Course.CourseEntries.COLUMN_NAME_CATEGORY, category);
        values.put(Course.CourseEntries.COLUMN_NAME_COURSE, course);

        int newRowId = (int) db.insert(Course.CourseEntries.TABLE_NAME, null, values);
    }
}
