package fr.oc.multilingua.multilingua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.oc.multilingua.multilingua.sqlite.Course;
import fr.oc.multilingua.multilingua.sqlite.DBHelper;

public class CourseActivity extends AppCompatActivity {

    public static final String COURSE_TITLE = "title";
    public static final String COURSE_DESCRIPTION = "description";
    public static final String COURSE_COURSE = "course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        final String title = getIntent().getStringExtra(COURSE_TITLE);
        String description = getIntent().getStringExtra(COURSE_DESCRIPTION);
        String course = getIntent().getStringExtra(COURSE_COURSE);

        setTitle(title);

        TextView courseTitle = (TextView) findViewById(R.id.course_title);
        TextView courseDescription = (TextView) findViewById(R.id.course_description);
        TextView courseCourse = (TextView) findViewById(R.id.course_course);

        courseTitle.setText(title);
        courseDescription.setText(description);
        courseCourse.setText(course);

        Button btnFinish = (Button) findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(CourseActivity.this);
                db.updateCourseComplete(title, 1);

                Intent intent = new Intent(CourseActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });
    }
}
