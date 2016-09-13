package fr.oc.multilingua.multilingua.sqlite;

import android.provider.BaseColumns;

public class Quiz {

    private int _id;
    private String _question;
    private int _answer;
    private int _courseId;

    public Quiz() {}

    public Quiz(int id, String question, int answer, int courseId) {
        this._id = id;
        this._question = question;
        this._answer = answer;
        this._courseId = courseId;
    }

    public static abstract class QuizEntries implements BaseColumns {
        public static final String TABLE_NAME = "quiz";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_ANSWER = "answer";
        public static final String COLUMN_NAME_COURSE_ID = "course";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_QUIZ =
            "CREATE TABLE " + QuizEntries.TABLE_NAME + " ("
                    + QuizEntries._ID + " INTEGER PRIMARY KEY,"
                    + QuizEntries.COLUMN_NAME_QUESTION + TEXT_TYPE + COMMA_SEP
                    + QuizEntries.COLUMN_NAME_ANSWER + INT_TYPE + COMMA_SEP
                    + QuizEntries.COLUMN_NAME_COURSE_ID + INT_TYPE + ")";

    public static final String SQL_DELETE_QUIZ =
            "DROP TABLE IF EXISTS " + QuizEntries.TABLE_NAME;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_question() {
        return _question;
    }

    public void set_question(String _question) {
        this._question = _question;
    }

    public int get_answer() {
        return _answer;
    }

    public void set_answer(int _answer) {
        this._answer = _answer;
    }

    public int get_courseId() {
        return _courseId;
    }

    public void set_courseId(int _courseId) {
        this._courseId = _courseId;
    }
}
