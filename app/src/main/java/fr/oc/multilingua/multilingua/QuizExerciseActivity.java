package fr.oc.multilingua.multilingua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import fr.oc.multilingua.multilingua.sqlite.Course;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;
import fr.oc.multilingua.multilingua.sqlite.Quiz;

public class QuizExerciseActivity extends AppCompatActivity {

    public static final String COURSE_TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_exercise);

        String title = getIntent().getStringExtra(COURSE_TITLE);

        setTitle("Quiz du cours " + title);

        DBHelper db = new DBHelper(this);
        Course currentCourse = db.selectCourse(title);
        List<Quiz> quizList = db.selectQuizByCourseId(currentCourse.get_id());


    }
}
