package fr.oc.multilingua.multilingua.sqlite;

import android.provider.BaseColumns;

public class Course {

    private int _id;
    private String _title;
    private String _description;
    private String _category;
    private String _course;
    private int _complete;

    public Course() {}

    public Course(int id, String title, String description, String category, String course, int complete) {
        this._id = id;
        this._title = title;
        this._description = description;
        this._category = category;
        this._course = course;
        this._complete = complete;
    }

    public static abstract class CourseEntries implements BaseColumns {
        public static final String TABLE_NAME = "course";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_COURSE = "course";
        public static final String COLUMN_NAME_COMPLETE = "complete";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_COURSE =
            "CREATE TABLE " + CourseEntries.TABLE_NAME + " ("
                    + CourseEntries._ID + " INTEGER PRIMARY KEY,"
                    + CourseEntries.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
                    + CourseEntries.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP
                    + CourseEntries.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP
                    + CourseEntries.COLUMN_NAME_COURSE + TEXT_TYPE + COMMA_SEP
                    + CourseEntries.COLUMN_NAME_COMPLETE + INT_TYPE + ")";

    public static final String SQL_DELETE_COURSE =
            "DROP TABLE IF EXISTS " + CourseEntries.TABLE_NAME;


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

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public String get_course() {
        return _course;
    }

    public void set_course(String _course) {
        this._course = _course;
    }

    public int get_complete() {
        return _complete;
    }

    public void set_complete(int _complete) {
        this._complete = _complete;
    }
}
