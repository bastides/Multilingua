package fr.oc.multilingua.multilingua.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "oc.multilingua.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.SQL_CREATE_USER);
        db.execSQL(Course.SQL_CREATE_COURSE);
        db.execSQL(Quiz.SQL_CREATE_QUIZ);
        db.execSQL(Appointment.SQL_CREATE_APPOINTMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(User.SQL_DELETE_USER);
        db.execSQL(Course.SQL_DELETE_COURSE);
        db.execSQL(Quiz.SQL_DELETE_QUIZ);
        db.execSQL(Appointment.SQL_DELETE_APPOINTMENT);
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
            if (cursor.isAfterLast()){
                return null;
            } else {
                User user = new User(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                return user;
            }
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

    public void insertCourse(String title, String description, String category, String course, int complete) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.CourseEntries.COLUMN_NAME_TITLE, title);
        values.put(Course.CourseEntries.COLUMN_NAME_DESCRIPTION, description);
        values.put(Course.CourseEntries.COLUMN_NAME_CATEGORY, category);
        values.put(Course.CourseEntries.COLUMN_NAME_COURSE, course);
        values.put(Course.CourseEntries.COLUMN_NAME_COMPLETE, complete);

        int newRowId = (int) db.insert(Course.CourseEntries.TABLE_NAME, null, values);
    }

    public List<Course> selectAllCourses() {
        List<Course> courses = new ArrayList<Course>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Course.CourseEntries._ID,
                Course.CourseEntries.COLUMN_NAME_TITLE,
                Course.CourseEntries.COLUMN_NAME_DESCRIPTION,
                Course.CourseEntries.COLUMN_NAME_CATEGORY,
                Course.CourseEntries.COLUMN_NAME_COURSE,
                Course.CourseEntries.COLUMN_NAME_COMPLETE
        };

        Cursor cursor = db.query(
                Course.CourseEntries.TABLE_NAME,
                projection,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Course course = new Course(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
            courses.add(course);
            cursor.moveToNext();
        }
        cursor.close();
        return courses;
    }

    public Course selectCourse(String title) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Course.CourseEntries._ID,
                Course.CourseEntries.COLUMN_NAME_TITLE,
                Course.CourseEntries.COLUMN_NAME_DESCRIPTION,
                Course.CourseEntries.COLUMN_NAME_CATEGORY,
                Course.CourseEntries.COLUMN_NAME_COURSE,
                Course.CourseEntries.COLUMN_NAME_COMPLETE
        };

        String selection = Course.CourseEntries.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { title };

        Cursor cursor = db.query(
                Course.CourseEntries.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.isAfterLast()){
                return null;
            } else {
                Course course = new Course(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                );
                return course;
            }
        } else {
            return null;
        }
    }

    public List<Course> selectCompleteCourses() {
        List<Course> courses = new ArrayList<Course>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Course.CourseEntries._ID,
                Course.CourseEntries.COLUMN_NAME_TITLE,
                Course.CourseEntries.COLUMN_NAME_DESCRIPTION,
                Course.CourseEntries.COLUMN_NAME_CATEGORY,
                Course.CourseEntries.COLUMN_NAME_COURSE,
                Course.CourseEntries.COLUMN_NAME_COMPLETE
        };

        Cursor cursor = db.query(
                Course.CourseEntries.TABLE_NAME,
                projection,
                "complete=?",
                new String[]{Integer.toString(1)},
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.isAfterLast()){
                return null;
            } else {
                while (!cursor.isAfterLast()) {
                    Course course = new Course(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5)
                    );
                    courses.add(course);
                    cursor.moveToNext();
                }
                cursor.close();
                return courses;
            }
        } else {
            return null;
        }
    }

    public void updateCourseComplete(String title, int isComplete) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("complete", isComplete);
        String[] selectionArgs = { title };

        db.update(Course.CourseEntries.TABLE_NAME, values, "title = ? ", selectionArgs);
    }

    public void insertQuiz(String question, int answer, int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Quiz.QuizEntries.COLUMN_NAME_QUESTION, question);
        values.put(Quiz.QuizEntries.COLUMN_NAME_ANSWER, answer);
        values.put(Quiz.QuizEntries.COLUMN_NAME_COURSE_ID, courseId);

        int newRowId = (int) db.insert(Quiz.QuizEntries.TABLE_NAME, null, values);
    }

    public List<Quiz> selectAllQuiz() {
        List<Quiz> quizs = new ArrayList<Quiz>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Quiz.QuizEntries._ID,
                Quiz.QuizEntries.COLUMN_NAME_QUESTION,
                Quiz.QuizEntries.COLUMN_NAME_ANSWER,
                Quiz.QuizEntries.COLUMN_NAME_COURSE_ID
        };

        Cursor cursor = db.query(
                Quiz.QuizEntries.TABLE_NAME,
                projection,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Quiz quiz = new Quiz(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3)
            );
            quizs.add(quiz);
            cursor.moveToNext();
        }
        cursor.close();
        return quizs;
    }

    public List<Quiz> selectQuizByCourseId(int courseId) {
        List<Quiz> quizList = new ArrayList<Quiz>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Quiz.QuizEntries._ID,
                Quiz.QuizEntries.COLUMN_NAME_QUESTION,
                Quiz.QuizEntries.COLUMN_NAME_ANSWER,
                Quiz.QuizEntries.COLUMN_NAME_COURSE_ID
        };

        Cursor cursor = db.query(
                Quiz.QuizEntries.TABLE_NAME,
                projection,
                "course=?",
                new String[]{Integer.toString(courseId)},
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.isAfterLast()){
                return null;
            } else {
                while (!cursor.isAfterLast()) {
                    Quiz quiz = new Quiz(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)
                    );
                    quizList.add(quiz);
                    cursor.moveToNext();
                }
                cursor.close();
                return quizList;
            }
        } else {
            return null;
        }
    }

    public void insertAppointment(String title, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Appointment.AppointmentEntries.COLUMN_NAME_TITLE, title);
        values.put(Appointment.AppointmentEntries.COLUMN_NAME_DATE, date);

        int newRowId = (int) db.insert(Appointment.AppointmentEntries.TABLE_NAME, null, values);
    }

    public List<Appointment> selectAllAppointment() {
        List<Appointment> appointments = new ArrayList<Appointment>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Appointment.AppointmentEntries._ID,
                Appointment.AppointmentEntries.COLUMN_NAME_TITLE,
                Appointment.AppointmentEntries.COLUMN_NAME_DATE
        };

        Cursor cursor = db.query(
                Appointment.AppointmentEntries.TABLE_NAME,
                projection,
                null, null, null, null, "date");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Appointment appointment = new Appointment(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            appointments.add(appointment);
            cursor.moveToNext();
        }
        cursor.close();
        return appointments;
    }

    public void deleteAppointment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = Appointment.AppointmentEntries._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(id) };

        db.delete(Appointment.AppointmentEntries.TABLE_NAME, selection, selectionArgs);
    }
}
